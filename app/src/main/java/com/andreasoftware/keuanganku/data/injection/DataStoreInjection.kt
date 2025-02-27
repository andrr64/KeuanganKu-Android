package com.andreasoftware.keuanganku.data.injection

import android.app.Application
import com.andreasoftware.keuanganku.data.datastore.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreInjection {
    @Provides
    @Singleton
    fun provideUserDataStore(application: Application): UserDataStore {
        return UserDataStore(application)
    }
}