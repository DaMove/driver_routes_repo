package com.aryeetey.driverroutesapp.domain

import androidx.lifecycle.LiveData
import com.aryeetey.driverroutesapp.data.localdb.DriverEntity

interface DriverRepository {
    suspend fun fetchAndSaveDrivers()
    fun getAllDrivers(): LiveData<List<DriverEntity>>
}