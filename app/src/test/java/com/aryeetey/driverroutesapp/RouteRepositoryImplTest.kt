package com.aryeetey.driverroutesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.aryeetey.driverroutesapp.data.localdb.RouteDao
import com.aryeetey.driverroutesapp.data.localdb.RouteEntity
import com.aryeetey.driverroutesapp.data.remote.DriverRoutesApiService
import com.aryeetey.driverroutesapp.data.remote.DriverRoutesResponse
import com.aryeetey.driverroutesapp.data.repositories.RouteRepositoryImpl
import com.aryeetey.driverroutesapp.domain.Route
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.Arrays


class RouteRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: DriverRoutesApiService

    private val observer: Observer<List<RouteEntity>>? = Mockito.mock(Observer::class.java) as Observer<List<RouteEntity>>

    @Mock
    private lateinit var routeDao: RouteDao

    private lateinit var repository: RouteRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = RouteRepositoryImpl(apiService, routeDao)
    }

    @Test
    fun fetchAndSaveRoutes_savesData() = runBlockingTest {
        val routes = listOf(Route(1, "R", "Route A"))
        `when`(apiService.getData()).thenReturn(Response.success(DriverRoutesResponse(emptyList(), routes)))

        repository.fetchAndSaveRoutes()

        verify(routeDao).insertAll(anyList())
    }

    @Test
    fun getAllRoutes_returnsData() {
        val liveData = MutableLiveData<List<RouteEntity>>()
        `when`(routeDao.getAllRoutes()).thenReturn(liveData)
        val repository = RouteRepositoryImpl(apiService, routeDao)
        if (observer != null) {
            repository.getAllRoutes().observeForever(observer)
        }

        val expectedRoutes = Arrays.asList(
            RouteEntity(1, "R", "West Side Residential Route"),
            RouteEntity(2, "C", "West Side Commercial Route"),
            RouteEntity(3, "I", "West Side Industrial Route"),
            RouteEntity(4, "R", "East Side Residential Route"),
            RouteEntity(5, "C", "East Side Commercial Route"),
            RouteEntity(6, "I", "East Side Industrial Route"),
            RouteEntity(7, "R", "North Side Residential Route"),
            RouteEntity(8, "C", "North Side Commercial Route"),
            RouteEntity(9, "I", "North Side Industrial Route"),
            RouteEntity(10, "R", "South Side Residential Route"),
            RouteEntity(11, "C", "South Side Commercial Route"),
            RouteEntity(12, "I", "South Side Industrial Route")
            )
        liveData.setValue(expectedRoutes)

        verify(observer)?.onChanged(expectedRoutes)
    }
}
