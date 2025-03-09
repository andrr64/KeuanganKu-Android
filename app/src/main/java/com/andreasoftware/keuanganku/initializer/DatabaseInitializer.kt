package com.andreasoftware.keuanganku.initializer

import android.content.Context
import androidx.startup.Initializer
import com.andreasoftware.keuanganku.data.db.AppDatabase

class DatabaseInitializer : Initializer<AppDatabase> {
    override fun create(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}