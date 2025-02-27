package com.andreasoftware.keuanganku.data.sqlite

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreasoftware.keuanganku.data.sqlite.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.sqlite.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.sqlite.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.sqlite.dao.IncomeDao
import com.andreasoftware.keuanganku.data.sqlite.dao.UserdataDao
import com.andreasoftware.keuanganku.data.sqlite.dao.WalletDao
import com.andreasoftware.keuanganku.data.sqlite.entities.Expense
import com.andreasoftware.keuanganku.data.sqlite.entities.ExpenseCategory
import com.andreasoftware.keuanganku.data.sqlite.entities.Income
import com.andreasoftware.keuanganku.data.sqlite.entities.IncomeCategory
import com.andreasoftware.keuanganku.data.sqlite.entities.Userdata
import com.andreasoftware.keuanganku.data.sqlite.entities.Wallet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Wallet::class,
        Userdata::class,

        ExpenseCategory::class,
        IncomeCategory::class,

        Income::class,
        Expense::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseCategoryDao(): ExpenseCategoryDao
    abstract fun incomeCategoryDao(): IncomeCategoryDao
    abstract fun walletDao(): WalletDao
    abstract fun userdataDao(): UserdataDao
    abstract fun incomeDao(): IncomeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private fun initDatabase(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val expenseCategoryDao = getDatabase(context).expenseCategoryDao()
                    val incomeCategoryDao = getDatabase(context).incomeCategoryDao()

                    if (expenseCategoryDao.getCount() == 0) {
                        val expenseCategories = listOf(
                            ExpenseCategory(
                                title = "Food",
                            ),
                            ExpenseCategory(
                                title = "Transport",
                            ),
                            ExpenseCategory(
                                title = "Utilities",
                            ),
                            ExpenseCategory(
                                title = "Entertainment",
                            ),
                            ExpenseCategory(
                                title = "Shopping",
                            )
                        )
                        expenseCategories.forEach { expenseCategoryDao.insert(it) }
                    }

                    if (incomeCategoryDao.getCount() == 0) {
                        val incomeCategories = listOf(
                            IncomeCategory(
                                title = "Init Wallet",
                            ),
                            IncomeCategory(
                                title = "Salary",
                            ),
                            IncomeCategory(
                                title = "Freelance",
                            ),
                            IncomeCategory(
                                title = "Investment",
                            ),
                            IncomeCategory(
                                title = "Gift",
                            ),
                            IncomeCategory(
                                title = "Rental Income",
                            )
                        )
                        incomeCategories.forEach { incomeCategoryDao.insert(it) }
                    }

                } catch (e: Exception) {
                    Log.e("AppDatabase", "Error initializing database", e)
                }
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        initDatabase(context)
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}