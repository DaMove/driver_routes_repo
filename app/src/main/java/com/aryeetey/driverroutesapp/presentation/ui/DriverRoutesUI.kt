package com.aryeetey.driverroutesapp.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aryeetey.driverroutesapp.data.localdb.DriverEntity
import com.aryeetey.driverroutesapp.data.localdb.RouteEntity
import com.aryeetey.driverroutesapp.presentation.viewmodels.DriverViewModel
import com.aryeetey.driverroutesapp.presentation.viewmodels.RouteViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DriverListScreen(
    driverViewModel: DriverViewModel,
    onItemSelected: (Int) -> Unit
) {
    val drivers by driverViewModel.driversLiveData.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Drivers") },
                actions = {
                    IconButton(
                        onClick = { driverViewModel.sortDriversByLastName() },
                        modifier = Modifier.background(Color.LightGray)
                    ) {
                        Icon(Icons.Default.Sort, contentDescription = "Sort")
                    }
                }
            )
        }
    ) {
        LazyColumn {
            itemsIndexed(drivers) { index, driver ->
                DriverListItem(driver = driver) {
                    onItemSelected(index)  // Pass index to the handler
                }
            }
        }
    }
}


@Composable
fun DriverListItem(driver: DriverEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = "${driver.firstName} ${driver.lastName}",
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp
        )
    }
}


@Composable
fun RouteScreen(routesViewModel: RouteViewModel, driverId: Int) {
    val routes: List<RouteEntity> by routesViewModel.routesLiveData.observeAsState(emptyList())
    routesViewModel.selectRoute(routes, driverId)
    val route by routesViewModel.selectedRoute.observeAsState()

    route?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Route Type: ${it.type}", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Route Name: ${it.name}", style = MaterialTheme.typography.body1)
        }
    } ?: run {
        Text(text = "No route found", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
    }
}


