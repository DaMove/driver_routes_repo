package com.aryeetey.driverroutesapp.data.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drivers")
data class DriverEntity(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String
)