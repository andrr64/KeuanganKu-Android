package com.andreasoftware.keuanganku.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.andreasoftware.keuanganku.data.database.entities.Userdata

@Dao
interface UserdataDao {
    @Insert
    fun insert(userdata: Userdata)
}