package com.example.app_barber.data.Model.Cliente

import com.example.app_barber.data.Model.Prenotazione.Prenotazione

data class Cliente(
    var id: String = "",
    var nome: String = "",
    var cognome: String = "",
    var telefono: String = "",
    var prenotazioni: List<Prenotazione> = emptyList()

)