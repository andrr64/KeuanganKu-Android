package com.andreasoftware.keuanganku.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreasoftware.keuanganku.data.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.model.ExpenseCategoryModel
import com.andreasoftware.keuanganku.data.model.ExpenseModel
import com.andreasoftware.keuanganku.data.model.IncomeCategoryModel
import com.andreasoftware.keuanganku.data.model.IncomeModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(
    entities = [
        ExpenseModel::class,
        ExpenseCategoryModel::class,
        IncomeModel::class,
        IncomeCategoryModel::class,
        WalletModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao
    abstract fun incomeCategoryDao(): IncomeCategoryDao
    abstract fun walletDao(): WalletDao
    abstract fun incomeDao(): IncomeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        const val DATABASE_NAME = "keuanganku.db"

        private fun initDatabase(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val db = getDatabase(context)
                    val expenseCategoryDao = db.expenseCategoryDao()
                    val incomeCategoryDao = db.incomeCategoryDao()

                    val expenseCount = withContext(Dispatchers.IO) { expenseCategoryDao.getCount() }
                    val incomeCount = withContext(Dispatchers.IO) { incomeCategoryDao.getCount() }

                    Log.d("DatabaseInit", "Jumlah kategori pengeluaran: $expenseCount")
                    Log.d("DatabaseInit", "Jumlah kategori pemasukan: $incomeCount")

                    if (expenseCount == 0) {
                        val expenseCategories = listOf(
                            ExpenseCategoryModel(name = "Food"),
                            ExpenseCategoryModel(name = "Transport"),
                            ExpenseCategoryModel(name = "Utilities"),
                            ExpenseCategoryModel(name = "Entertainment"),
                            ExpenseCategoryModel(name = "Shopping")
                        )
                        expenseCategories.forEach { expenseCategoryDao.insert(it) }
                        Log.d("DatabaseInit", "Kategori pengeluaran ditambahkan")
                    }

                    if (incomeCount == 0) {
                        val incomeCategories = listOf(
                            IncomeCategoryModel(name = "Wallet Initialization"),
                            IncomeCategoryModel(name = "Salary"),
                            IncomeCategoryModel(name = "Freelance"),
                            IncomeCategoryModel(name = "Investment"),
                            IncomeCategoryModel(name = "Gift"),
                            IncomeCategoryModel(name = "Rental Income")
                        )
                        incomeCategories.forEach { incomeCategoryDao.insert(it) }
                        Log.d("DatabaseInit", "Kategori pemasukan ditambahkan")
                    }

                } catch (e: Exception) {
                    Log.e("DatabaseInit", "Error initializing database", e)
                }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("Database", "Database created")
                            initDatabase(context)
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}