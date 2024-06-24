package com.aryeetey.driverroutesapp.data.repositories

import androidx.lifecycle.LiveData
import com.aryeetey.driverroutesapp.domain.DriverRepository
import com.aryeetey.driverroutesapp.data.localdb.DriverDao
import com.aryeetey.driverroutesapp.data.localdb.DriverEntity
import com.aryeetey.driverroutesapp.data.remote.DriverRoutesApiService

class DriverRepositoryImpl(
    private val apiService: DriverRoutesApiService,
    private val driverDao: DriverDao
) : DriverRepository {
    override suspend fun fetchAndSaveDrivers() {
        val drivers = apiService.getData().body()?.drivers
        if (drivers != null) {
            driverDao.insertAll(drivers.map {
                val (firstName, lastName) = splitNames(it.name)
                DriverEntity(it.id, firstName, lastName) })
        }
    }

    private fun splitNames(fullName:String): Pair<String, String> {
        val names = fullName.split(" ")
        return Pair(names[0], names[1])
    }

    override fun getAllDrivers(): LiveData<List<DriverEntity>> = driverDao.getAllDrivers()
}