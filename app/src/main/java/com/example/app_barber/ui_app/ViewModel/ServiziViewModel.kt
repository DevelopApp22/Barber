package com.example.app_barber.ui_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.Servizio


class ServiziViewModel( val firestoreRepository: FirestoreRepository): ViewModel() {

    private val _servizi = MutableLiveData<List<Servizio>>()
    val servizi: LiveData<List<Servizio>> = _servizi

    fun getServizi(uid:String){
        firestoreRepository.getServizi(uid=uid,onSuccess = { servizi ->
            _servizi.value = servizi},onFailure = { })
    }

    fun createServizio(uid: String, nome: String, prezzo: String, onSuccess: () -> Unit) {
        firestoreRepository.create_servizio(uid, nome, prezzo, { onSuccess() })
    }

    fun deleteTurno(uid: String, id_servizio: String, onSuccess: () -> Unit) {
        firestoreRepository.delete_servizio(uid, id_servizio, { onSuccess() })
    }

    fun modifyServizio(uid: String, id_servizio: String, nome: String, prezzo: String, onSuccess: () -> Unit) {
        firestoreRepository.modify_servizio(uid, id_servizio, nome, prezzo, { onSuccess() })
    }
}