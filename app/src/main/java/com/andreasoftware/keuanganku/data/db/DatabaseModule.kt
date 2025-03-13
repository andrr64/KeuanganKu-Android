package com.andreasoftware.keuanganku.data.db

import android.content.Context
import com.andreasoftware.keuanganku.data.dao.CategoryDao
import com.andreasoftware.keuanganku.data.dao.TransactionDao
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
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao{
        return appDatabase.categoryDao()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao{
        return appDatabase.transactionDao()
    }

    @Provides
    fun provideWalletDao(appDatabase: AppDatabase): WalletDao {
        return appDatabase.walletDao()
    }
}