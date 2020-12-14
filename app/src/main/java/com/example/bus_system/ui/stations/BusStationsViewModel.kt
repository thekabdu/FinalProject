package com.example.bus_system.ui.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BusStationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "In processing, wait next update)"
    }
    val text: LiveData<String> = _text
}