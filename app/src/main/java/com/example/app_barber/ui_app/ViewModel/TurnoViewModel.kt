package com.example.app_barber.ui_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.data.Model.Turno
import com.example.app_barber.data.Model.User
import kotlinx.coroutines.launch

class TurnoViewModel(val firestoreRepository: FirestoreRepository): ViewModel()  {

    private val _turno = MutableLiveData<List<Turno>>()
    val turno: LiveData<List<Turno>> get() = _turno

    fun getTurni (uid: String, onSuccess: () -> Unit) {
        firestoreRepository.getTurni(uid, { turni ->
            _turno.value = turni
            onSuccess()
        }, { })
    }

    fun createTurno(uid: String, start: String, end: String, onSuccess: () -> Unit) {
        firestoreRepository.create_turno(uid, start, end, { onSuccess() })
    }

    fun deleteTurno(uid: String, id_turno: String, onSuccess: () -> Unit) {
        firestoreRepository.delete_turno(uid, id_turno, { onSuccess() })
    }

    fun modifyTurno(uid: String, id_turno: String, start: String, end: String, onSuccess: () -> Unit) {
        firestoreRepository.modidy_turno(uid, id_turno, start, end, { onSuccess() })
    }

}