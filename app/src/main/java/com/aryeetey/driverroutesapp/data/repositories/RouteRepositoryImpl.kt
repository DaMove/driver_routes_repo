package com.aryeetey.driverroutesapp.data.repositories

import androidx.lifecycle.LiveData
import com.aryeetey.driverroutesapp.data.localdb.RouteDao
import com.aryeetey.driverroutesapp.data.localdb.RouteEntity
import com.aryeetey.driverroutesapp.data.remote.DriverRoutesApiService
import com.aryeetey.driverroutesapp.domain.RouteRepository

class RouteRepositoryImpl(
    private val apiService: DriverRoutesApiService,
    private val routeDao: RouteDao
) : RouteRepository {

    override suspend fun fetchAndSaveRoutes() {
        val routes = apiService.getData().body()?.routes
        if (routes != null) {
            routeDao.insertAll(routes.map {
                RouteEntity(it.id, it.type, it.name) })
        }
    }

    override fun getAllRoutes(): LiveData<List<RouteEntity>> = routeDao.getAllRoutes()

}
