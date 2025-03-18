package com.andreasoftware.keuanganku.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.dao.CategoryDao
import com.andreasoftware.keuanganku.data.dao.ExpenseLimiterDao
import com.andreasoftware.keuanganku.data.dao.TransactionDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Database(
    entities = [
        WalletModel::class,
        TransactionModel::class,
        CategoryModel::class,
        ExpenseLimiterModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun walletDao(): WalletDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseLimiterDao(): ExpenseLimiterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        const val DATABASE_NAME = "keuanganku.db"

        private fun initDb(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val db = getDatabase(context)

                    val categoryDao = db.categoryDao()
                    val transactionExpenseTypeId = TransactionType.EXPENSE.value
                    val transactionIncomeTypeId = TransactionType.INCOME.value

                    val expenseCount = withContext(Dispatchers.IO) { categoryDao.getCategoryCountByType(transactionExpenseTypeId) }
                    val incomeCount = withContext(Dispatchers.IO) { categoryDao.getCategoryCountByType(TransactionType.INCOME.value) }

                    if (incomeCount == 0) {
                        val incomeCategories = listOf(
                            CategoryModel(name = "Wallet Initialization", id = 1,  transactionTypeId = transactionIncomeTypeId),
                            CategoryModel(name = "Salary", transactionTypeId = transactionIncomeTypeId),
                            CategoryModel(name = "Freelance", transactionTypeId = transactionIncomeTypeId),
                            CategoryModel(name = "Investment", transactionTypeId = transactionIncomeTypeId),
                            CategoryModel(name = "Gift", transactionTypeId = transactionIncomeTypeId),
                            CategoryModel(name = "Rental Income", transactionTypeId = transactionIncomeTypeId)
                        )
                        incomeCategories.forEach { categoryDao.insert(it) }
                        Log.d("DatabaseInit", "Kategori pemasukan ditambahkan")
                    }

                    if (expenseCount == 0) {
                        val expenseCategories = listOf(
                            CategoryModel(name = "Food", transactionTypeId = transactionExpenseTypeId),
                            CategoryModel(name = "Transport", transactionTypeId = transactionExpenseTypeId),
                            CategoryModel(name = "Utilities", transactionTypeId = transactionExpenseTypeId),
                            CategoryModel(name = "Entertainment", transactionTypeId = transactionExpenseTypeId),
                            CategoryModel(name = "Shopping", transactionTypeId = transactionExpenseTypeId)
                        )
                        expenseCategories.forEach { categoryDao.insert(it) }
                    }
                }  catch (e: Exception){
                    ///TODO: handle exception
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
                            initDb(context)
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}