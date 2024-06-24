package com.aryeetey.driverroutesapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryeetey.driverroutesapp.data.localdb.DriverRoutesDatabase
import com.aryeetey.driverroutesapp.data.remote.RetrofitClient
import com.aryeetey.driverroutesapp.data.repositories.DriverRepositoryImpl
import com.aryeetey.driverroutesapp.domain.DriverRepository
import com.aryeetey.driverroutesapp.presentation.viewmodels.DriverViewModel
import com.aryeetey.driverroutesapp.ui.theme.DriverRoutesAppTheme

class MainActivity : ComponentActivity() {
    private val driverRepository: DriverRepository by lazy {
        DriverRepositoryImpl(
            RetrofitClient.apiService,
            DriverRoutesDatabase.getDatabase(this).driverDao()
        )
    }

    private val viewModelFactory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DriverViewModel::class.java)) {
                return DriverViewModel(driverRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    private val driverViewModel: DriverViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DriverRoutesAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MaterialTheme {
                        DriverListScreen(driverViewModel) { position ->
                            val driverId = driverViewModel.driversLiveData.value?.get(position)?.id
                            Log.d("MainActivity", "Driver selected with ID: $driverId")
                            startActivity(
                                Intent(this@MainActivity, RouteActivity::class.java).apply {
                                    putExtra("DRIVER_ID", driverId ?: -1)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


