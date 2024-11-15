package com.example.app_barber.data.Model.Cliente

data class Cliente_completo(
    val cliente : Cliente,
    val prenotazioni : PrenotazioniStorico,
    val lista_servizi: List<StoricoServiziCliente>
)