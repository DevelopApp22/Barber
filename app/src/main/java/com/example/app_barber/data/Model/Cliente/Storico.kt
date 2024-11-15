package com.example.app_barber.data.Model.Cliente

data class PrenotazioniStorico(
    var numeroPrenotazioni: Int = 0
)

data class ServiziStorico(
    var servizioId: String = "",
    var numeroVolte: Int = 0
)

data class StoricoServiziCliente(
    var servizioId: String = "",
    var nomeServizio: String = "",
    var NumeroVolte: Any = 0
)