package com.aryeetey.driverroutesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryeetey.driverroutesapp.data.localdb.RouteEntity
import com.aryeetey.driverroutesapp.domain.RouteRepository
import kotlinx.coroutines.launch

class RouteViewModel(
    private val routeRepository: RouteRepository
) : ViewModel() {
    private val _routesLiveData = MutableLiveData<List<RouteEntity>>()
    val routesLiveData: LiveData<List<RouteEntity>> = _routesLiveData

    init {
        viewModelScope.launch {
            routeRepository.fetchAndSaveRoutes()
            // Ensure LiveData updates from database stream
           routeRepository.getAllRoutes().observeForever {
                _routesLiveData.postValue(it)
            }
        }
    }

    val selectedRoute = MutableLiveData<RouteEntity?>()

     fun selectRoute(routes: List<RouteEntity>, driverId: Int) {
        val route = when {
            routes.any { it.id == driverId } -> routes.first { it.id == driverId }
            driverId % 2 == 0 -> routes.firstOrNull { it.type == "R" }
            driverId % 5 == 0 -> routes.filter { it.type == "C" }.getOrNull(1)
            else -> routes.lastOrNull { it.type == "I" }
        }
        selectedRoute.postValue(route)
    }
}
