package com.andreasoftware.keuanganku.data.di

import android.app.Application
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.database.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.database.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.database.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.database.dao.IncomeDao
import com.andreasoftware.keuanganku.data.database.dao.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExpenseCategoryDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IncomeCategoryDaoQualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ExpenseDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IncomeDaoQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WalletDaoQualifier

@Module
@InstallIn(SingletonComponent::class)
object DatabaseInjection {
    @Provides
    @Singleton
    @ExpenseCategoryDaoQualifier
    fun provideExpenseCategoryDao(application: Application): ExpenseCategoryDao {
        return AppDatabase.getDatabase(application).expenseCategoryDao()
    }

    @Provides
    @Singleton
    @ExpenseDaoQualifier
    fun provideExpenseDao(application: Application): ExpenseDao {
        return AppDatabase.getDatabase(application).expenseDao()
    }

    @Provides
    @Singleton
    @IncomeDaoQualifier
    fun provideIncomeDao(application: Application): IncomeDao {
        return AppDatabase.getDatabase(application).incomeDao()
    }

    @Provides
    @Singleton
    @IncomeCategoryDaoQualifier
    fun provideIncomeCategoryDao(application: Application): IncomeCategoryDao {
        return AppDatabase.getDatabase(application).incomeCategoryDao()
    }

    @Provides
    @Singleton
    @WalletDaoQualifier
    fun provideWalletDao(application: Application): WalletDao {
        return AppDatabase.getDatabase(application).walletDao()
    }
}