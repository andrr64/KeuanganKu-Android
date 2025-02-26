package com.andreasoftware.keuanganku.data.di

import android.app.Application
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.database.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.database.dao.IncomeCategoryDao
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
    @IncomeCategoryDaoQualifier
    fun provideIncomeCategoryDao(application: Application): IncomeCategoryDao {
        return AppDatabase.getDatabase(application).incomeCategoryDao()
    }
}