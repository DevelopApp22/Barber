package com.example.app_barber.data.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Calendar


data class User(
    var name: String = "",
    var surname: String = "",
    var sex: String = "",
    var date_of_birth: String = "",
    var name_activity: String = "",
    var telephone_activity: String = "",
    var via: String = "",
    var orario: String = "",
    var storicoAggiornato: Boolean = false,
    var lastUpdated: String = getCurrentDate() // Usa un metodo per ottenere la data
) {
    companion object {
        // Metodo per ottenere la data corrente come stringa
        private fun getCurrentDate(): String {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // I mesi partono da 0
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return String.format("%04d-%02d-%02d", year, month, day) // Formato YYYY-MM-DD
        }
    }
}