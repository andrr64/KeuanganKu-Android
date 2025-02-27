package com.andreasoftware.keuanganku.data.sqlite.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("userdata")
data class Userdata(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "",
    var locale: String? = null
)