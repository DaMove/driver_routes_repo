package com.aryeetey.driverroutesapp.data.remote

import com.aryeetey.driverroutesapp.domain.Driver
import com.aryeetey.driverroutesapp.domain.Route

data class DriverRoutesResponse(
    val drivers: List<Driver>,
    val routes: List<Route>
)