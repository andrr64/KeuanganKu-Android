package com.andreasoftware.keuanganku.data.sqlite.dao

import androidx.room.Dao
import androidx.room.Insert
import com.andreasoftware.keuanganku.data.sqlite.entities.Userdata

@Dao
interface UserdataDao {
    @Insert
    fun insert(userdata: Userdata)
}