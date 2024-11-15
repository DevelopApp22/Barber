package com.example.app_barber.data.Model.Prenotazione

import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.data.Model.Turno

data class Prenotazione_completa(
    val prenotazione: Prenotazione,
    val cliente: Cliente,
    val turno: Turno
)
