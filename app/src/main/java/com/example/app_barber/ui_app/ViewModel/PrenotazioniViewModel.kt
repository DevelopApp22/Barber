package com.example.app_barber.ui_app.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.Day
import com.example.app_barber.data.Model.Prenotazione.Prenotazione
import com.example.app_barber.data.Model.Prenotazione.Prenotazione_completa
import com.example.app_barber.data.Model.Turno
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

class PrenotazioniViewModel (private val firestoreRepository: FirestoreRepository) : ViewModel() {

    private val _bookings_day = MutableLiveData<List<Prenotazione_completa>>()
    val boking_day: LiveData<List<Prenotazione_completa>> get() = _bookings_day


    private val _turni = MutableLiveData<List<Turno?>>()
    val turni: LiveData<List<Turno?>> get() = _turni

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkStoricoAggiornato(uid: String) {
        firestoreRepository.controllaAggiornamentoStorico(uid =uid )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayCalendar(date: LocalDate, onSuccess: (Day) -> Unit) {
        firestoreRepository.getDayCalendar(
            date = date,
            uid = FirebaseAuth.getInstance().currentUser!!.uid,
            onSuccess = { day ->
                onSuccess(day)
                // Esegui l'aggiornamento dello storico solo la prima volta
                checkStoricoAggiornato(uid = FirebaseAuth.getInstance().currentUser!!.uid)
            }
        )

    }

    fun getTurni(uid:String,onSuccess: (List<Turno?>) -> Unit, onFailure: (Exception) -> Unit) {
        firestoreRepository.getTurni(uid=uid,onSuccess = { turni ->
            _turni.value = turni
            onSuccess(turni)
        }, onFailure = { exception ->
            onFailure(exception)

     }


        )}

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPrenotazioniCalendar(uid:String,date: LocalDate,)  {
        firestoreRepository.getPrenotazioniConDettagli(date=date, uid = uid, onComplete = {day->
            _bookings_day.value = day

        })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createPrenotazione(uid: String, prenotazione: Prenotazione, onSuccess: () -> Unit,onUpdate: () -> Unit) {
        firestoreRepository.createPrenotazione(uid, prenotazione, {
            // Chiamo onSuccess() correttamente
            onSuccess()
        }, onUpdate = { onUpdate() })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun aggiornaStoricoPrenotazioni(date: LocalDate, uid: String) {
        // Implementa qui la logica per aggiornare lo storico delle prenotazioni
        firestoreRepository.aggiornaStoricoPrenotazioni(date =date, uid = uid, onComplete = {})
    }
    fun deletePrenotazione(uid: String, prenotazione: Prenotazione_completa, onSuccess: () -> Unit, onUpdate: () -> Unit) {
        firestoreRepository.deletePrenotazione(uid, prenotazione,
            {
            // Chiamo onSuccess() correttamente
            onSuccess()
        } , onUpdate = { onUpdate() })
    }
}



