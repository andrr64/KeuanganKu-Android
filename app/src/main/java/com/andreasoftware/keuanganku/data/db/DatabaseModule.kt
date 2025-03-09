package com.andreasoftware.keuanganku.data.db

import android.content.Context
import com.andreasoftware.keuanganku.data.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideExpenseDao(appDatabase: AppDatabase): ExpenseDao {
        return appDatabase.expenseDao()
    }

    @Provides
    fun provideExpenseCategoryDao(appDatabase: AppDatabase): ExpenseCategoryDao {
        return appDatabase.expenseCategoryDao()
    }

    @Provides
    fun provideIncomeCategoryDao(appDatabase: AppDatabase): IncomeCategoryDao {
        return appDatabase.incomeCategoryDao()
    }

    @Provides
    fun provideWalletDao(appDatabase: AppDatabase): WalletDao {
        return appDatabase.walletDao()
    }

    @Provides
    fun provideIncomeDao(appDatabase: AppDatabase): IncomeDao {
        return appDatabase.incomeDao()
    }
}