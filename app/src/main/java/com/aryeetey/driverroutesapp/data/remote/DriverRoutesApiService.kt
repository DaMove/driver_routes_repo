package com.aryeetey.driverroutesapp.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface DriverRoutesApiService {
    @GET("data")
    suspend fun getData(): Response<DriverRoutesResponse>
}