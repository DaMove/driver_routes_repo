package com.aryeetey.driverroutesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryeetey.driverroutesapp.data.localdb.DriverEntity
import com.aryeetey.driverroutesapp.domain.DriverRepository
import kotlinx.coroutines.launch

class DriverViewModel(private val driverRepository: DriverRepository) : ViewModel() {
    private val _driversLiveData = MutableLiveData<List<DriverEntity>>()
    val driversLiveData: LiveData<List<DriverEntity>> = _driversLiveData

    init {
        viewModelScope.launch {
            driverRepository.fetchAndSaveDrivers()

            // Ensure LiveData updates from database stream
            driverRepository.getAllDrivers().observeForever {
                _driversLiveData.postValue(it)
            }
        }
    }

    fun sortDriversByLastName() {
        _driversLiveData.value?.sortedBy { it.lastName }?.let {
            _driversLiveData.postValue(it)
        }
    }
}
