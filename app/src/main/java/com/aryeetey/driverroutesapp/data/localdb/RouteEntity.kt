package com.aryeetey.driverroutesapp.data.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class RouteEntity(
    @PrimaryKey
    val id: Int,
    val type: String,
    val name: String
)