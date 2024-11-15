package com.example.app_barber.ui_app.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.data.Model.Cliente.PrenotazioniStorico
import com.example.app_barber.data.Model.Cliente.StoricoServiziCliente
import com.example.app_barber.data.Model.Prenotazione.Prenotazione

class ClienteViewModel( val firestoreRepository: FirestoreRepository): ViewModel() {

    private val _clienti = MutableLiveData<List<Cliente>>()
    val clienti: LiveData<List<Cliente>> get() = _clienti

    private val _filteredClients = MutableLiveData<List<Cliente>>(emptyList()) // Clienti filtrati
    val filteredClients: LiveData<List<Cliente>> get() = _filteredClients

    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> get()= _searchQuery

    private val _prenotazioniStorico = MutableLiveData<PrenotazioniStorico>()
    val prenotazioniStorico: LiveData<PrenotazioniStorico> get() = _prenotazioniStorico

    private val _serviziStorico = MutableLiveData<List<StoricoServiziCliente>>(emptyList())
    val serviziStorico: LiveData<List<StoricoServiziCliente>> = _serviziStorico

    private val _prenotazioni = MutableLiveData<List<Prenotazione>>()
    val prenotazioni: LiveData<List<Prenotazione>> get() = _prenotazioni

    private val _isPrenotazioniVuote = MutableLiveData<Boolean>()
    val isPrenotazioniVuote: LiveData<Boolean> get() = _isPrenotazioniVuote

    fun getClienti(uid:String,onSucces: () -> Unit) {
        firestoreRepository.getClienti(uid=uid,onSuccess = { clienti ->
            _clienti.value = clienti
            onSucces()
        },onFailure = { })
    }

    fun addCliente(uid: String, nome: String, cognome: String, telefono: String, onSucces: () -> Unit) {
        firestoreRepository.create_client(uid, nome, cognome, telefono, { onSucces() })
    }

    fun deleteCliente(uid: String, id_cliente: String, onSucces: () -> Unit) {
        firestoreRepository.delete_client(uid, id_cliente, { onSucces() })
    }

    fun modificaCliente(uid: String, id_cliente: String, nome: String, cognome: String, telefono: String, onSucces: () -> Unit){
        firestoreRepository.modify_client(uid, id_cliente, nome, cognome, telefono, {onSucces()})
    }

    // Funzione per filtrare i clienti in base alla query
    fun filterClients(query: String) {
        val clientiList = _clienti.value ?: emptyList() // Assicurati che _clienti contenga la lista completa

        val filteredList = if (query.isEmpty()) {

            clientiList // Mostra tutti i clienti se la query è vuota
        } else {
            clientiList.filter {
                it.nome.contains(query, ignoreCase = true) ||
                        it.cognome.contains(query, ignoreCase = true) ||
                        it.telefono.contains(query)
            }
        }
        _filteredClients.value = filteredList
    }
    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
        filterClients(newQuery)
    }

    fun getStoricoCliente(uid: String, cliente: Cliente, onSucces: () -> Unit) {
        firestoreRepository.getStoricoCliente(uid, cliente, {
                prenotazioni ->
            _prenotazioniStorico.value = prenotazioni
        },{})
    }

    fun getServiziCliente(uid: String, cliente: Cliente, onSucces: () -> Unit) {
        firestoreRepository.getStoricoServizi(uid, cliente, {
                servizi ->
            _serviziStorico.value = servizi
            Log.e("servizi", "sssssss")
        },{})
    }

    fun getPrenotazioneUtente(uid: String, cliente: Cliente, onSuccess: () -> Unit) {
        firestoreRepository.getPrenotazioneUtente(uid, cliente, { prenotazioni ->
            _prenotazioni.value = prenotazioni
            _isPrenotazioniVuote.value = prenotazioni.isEmpty()
            onSuccess()
        }, {
            _isPrenotazioniVuote.value = true
        })
    }

}


