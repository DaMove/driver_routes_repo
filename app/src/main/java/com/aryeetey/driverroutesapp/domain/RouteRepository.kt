package com.aryeetey.driverroutesapp.domain

import androidx.lifecycle.LiveData
import com.aryeetey.driverroutesapp.data.localdb.RouteEntity

interface RouteRepository {
    suspend fun fetchAndSaveRoutes()
    fun getAllRoutes(): LiveData<List<RouteEntity>>
}