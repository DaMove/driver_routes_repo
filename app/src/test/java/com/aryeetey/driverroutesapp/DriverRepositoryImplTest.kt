package com.aryeetey.driverroutesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aryeetey.driverroutesapp.data.localdb.DriverDao
import com.aryeetey.driverroutesapp.data.localdb.DriverEntity
import com.aryeetey.driverroutesapp.data.remote.DriverRoutesApiService
import com.aryeetey.driverroutesapp.data.remote.DriverRoutesResponse
import com.aryeetey.driverroutesapp.data.repositories.DriverRepositoryImpl
import com.aryeetey.driverroutesapp.domain.Driver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class DriverRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: DriverRoutesApiService

    @Mock
    private lateinit var driverDao: DriverDao

    private lateinit var repository: DriverRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = DriverRepositoryImpl(apiService, driverDao)
    }

    @Test
    fun fetchAndSaveDrivers_savesData() = runBlockingTest {
        val drivers = listOf(Driver(1, "John Doe"))
        `when`(apiService.getData()).thenReturn(Response.success(DriverRoutesResponse(drivers, emptyList())))

        repository.fetchAndSaveDrivers()

        verify(driverDao).insertAll(anyList())
    }

    @Test
    fun getAllDrivers_returnsData() {
        val driverEntities = listOf(
            DriverEntity(9, "Bruce", "Spruce"),
            DriverEntity(19, "Andy", "Garcia"),
            DriverEntity(14, "Jenny", "Lowe"),
            DriverEntity(13, "Amber", "Shoe"),
            DriverEntity(6, "Adam", "Stand"),
            DriverEntity(15, "Ellis", "Roth"),
            DriverEntity(2, "Chris", "Willis"),
            DriverEntity(16, "Danika", "Johnson"),
            DriverEntity(3, "Archie", "King"),
            DriverEntity(25, "Monica", "Brown")
        )
        val liveData = MutableLiveData<List<DriverEntity>>()
        liveData.value = driverEntities

        `when`(driverDao.getAllDrivers()).thenReturn(liveData)

        val observer: Observer<List<DriverEntity>> = mock(Observer::class.java) as Observer<List<DriverEntity>>
        repository.getAllDrivers().observeForever(observer)

        verify(observer).onChanged(driverEntities)
    }

}
