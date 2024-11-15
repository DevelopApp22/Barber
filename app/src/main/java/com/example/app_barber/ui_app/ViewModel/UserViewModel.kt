package com.example.app_barber.ui_app.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

// ViewModel per gestire i dati dell'utente
class UserViewModel(private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _user = MutableLiveData<User?>() // MutableLiveData per aggiornare i dati dell'utente
    val user: LiveData<User?> get() = _user // LiveData per osservare i dati dell'utente

    private val _serviziStats = MutableLiveData<Map<String, Int>>(emptyMap())
    val serviziStats: LiveData<Map<String, Int>> = _serviziStats

    private val _prenotazioniMensili = MutableLiveData<Map<String, Map<String, Int>>>()
    val prenotazioniMensili: LiveData<Map<String, Map<String, Int>>> get() = _prenotazioniMensili

    private val _bookingFrequency = MutableLiveData<Map<String, Float>>()
    val bookingFrequency: LiveData<Map<String, Float>> get() = _bookingFrequency


    fun createUser(userId: String, user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            firestoreRepository.createUserDocument(userId, user, onSuccess, onFailure)
        }
    }
    fun saveUser(userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch{ // Lancia una coroutine nel viewModelScope
            firestoreRepository.createUserDocument(userId, user.value!!, onSuccess, onFailure)
        }
    }

    // Funzione per aggiornare i campi dell'utente


    // Funzione per verificare se un documento utente esiste su Firestore
    fun doesUserExist(userId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            firestoreRepository.doesUserDocumentExist(userId, onResult)
        }
    }

    // Funzione per recuperare i dati di un utente da Firestore
    fun getUser(uid: String, onSuccess: (User?) -> Unit, onFailure: (Exception) -> Unit) {
            firestoreRepository.getUserDocument(uid, onSuccess={ userModel ->
                _user.value = userModel
            },onFailure = {

            })
    }

    fun fetchServiziStats(uid: String) {
        viewModelScope.launch {
            val stats = firestoreRepository.getServiziStats(uid)
            _serviziStats.value = stats
        }
    }

    fun fetchPrenotazioniMensili(uid: String) {
        firestoreRepository.getPrenotazioniMensiliPerAnno(uid) { data ->
            _prenotazioniMensili.value = data
        }
    }
    fun fetchBookingFrequency(uid: String) {
        firestoreRepository.getCustomerBookingFrequencyFromStorico(uid) { frequencyData ->
            _bookingFrequency.value = frequencyData
        }
    }

    }