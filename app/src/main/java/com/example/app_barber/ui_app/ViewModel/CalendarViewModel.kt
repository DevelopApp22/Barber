package com.example.app_barber.ui_app.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_barber.Funzioni.Calendario.Calendario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel: ViewModel() {

    private val _calendar = MutableLiveData<Array<Array<LocalDate?>>>()
    val calendar: LiveData<Array<Array<LocalDate?>>> = _calendar


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCalendar(year: Int, month: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            val newCalendar = Calendario(year, month)
            _calendar.postValue(newCalendar)
        }
    }

}