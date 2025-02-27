package com.andreasoftware.keuanganku.data.injection

import android.app.Application
import com.andreasoftware.keuanganku.common.qualifier.ExpenseCategoryDaoQualifier
import com.andreasoftware.keuanganku.common.qualifier.ExpenseDaoQualifier
import com.andreasoftware.keuanganku.common.qualifier.IncomeCategoryDaoQualifier
import com.andreasoftware.keuanganku.common.qualifier.IncomeDaoQualifier
import com.andreasoftware.keuanganku.common.qualifier.WalletDaoQualifier
import com.andreasoftware.keuanganku.data.sqlite.AppDatabase
import com.andreasoftware.keuanganku.data.sqlite.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.sqlite.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.sqlite.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.sqlite.dao.IncomeDao
import com.andreasoftware.keuanganku.data.sqlite.dao.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomInjection {
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