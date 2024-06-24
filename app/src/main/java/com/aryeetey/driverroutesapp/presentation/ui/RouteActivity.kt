package com.aryeetey.driverroutesapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryeetey.driverroutesapp.data.localdb.DriverRoutesDatabase
import com.aryeetey.driverroutesapp.data.remote.RetrofitClient
import com.aryeetey.driverroutesapp.data.repositories.RouteRepositoryImpl
import com.aryeetey.driverroutesapp.domain.RouteRepository
import com.aryeetey.driverroutesapp.presentation.viewmodels.RouteViewModel
import com.aryeetey.driverroutesapp.ui.theme.DriverRoutesAppTheme

class RouteActivity : ComponentActivity() {
    private val routeRepository: RouteRepository by lazy {
        RouteRepositoryImpl(
            RetrofitClient.apiService,
            DriverRoutesDatabase.getDatabase(this).routeDao()
        )
    }

    private val viewModelFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RouteViewModel::class.java)) {
                return RouteViewModel(routeRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val routeViewModel: RouteViewModel by viewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val driverId = intent.getIntExtra("DRIVER_ID", -1)

        setContent {
            DriverRoutesAppTheme {
                RouteScreen(routeViewModel, driverId)
            }
        }
    }
}
