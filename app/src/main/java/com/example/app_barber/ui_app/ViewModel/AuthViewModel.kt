package com.example.app_barber.ui_app.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: FirestoreRepository) :ViewModel() {


    private val _authState = MutableLiveData<Boolean>() // MutableLiveData per aggiornare lo stato di autenticazione

    val authState: LiveData<Boolean> get() = _authState // LiveData per osservare lo stato di autenticazione da parte delle viste

    val auth = FirebaseAuth.getInstance() // istanza di FirebaseAuth per gestire l'autenticazione

    fun registerWithEmail(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)//crea un utente con email e password
            .addOnCompleteListener { task ->//aggiunge un listener per verificare se la registrazione è andata a buon fine
                if (task.isSuccessful) {
                    _authState.value = true
                    onSuccess()
                } else {
                    _authState.value = false
                    task.exception?.let { onFailure(it) }//se errore mette _authState a false

                }
            }
    } //funzione per registrare un utente con email e password se è loggato mette _authState a true altrimenti false

    fun loginWithEmail(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)//crea un utente con email e password
            .addOnCompleteListener { task ->//aggiunge un listener per verificare se la registrazione è andata a buon fine
                if (task.isSuccessful) {
                    _authState.value = true
                    onSuccess()
                } else {
                    _authState.value = false
                    task.exception?.let { onFailure(it) }//se errore mette _authState a false
                }
            }
    }

    fun createUser(userId: String, user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        viewModelScope.launch {
            repository.createUserDocument(userId, user, onSuccess, onFailure)
        }
    }
}