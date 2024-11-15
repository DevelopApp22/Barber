package com.example.app_barbaber.Screen



import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.app_barbaber.ui.theme.Black_Agenda
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_Background
import com.example.app_barbaber.ui.theme.Black_Dialog
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barbaber.ui.theme.Blue_Ag
import com.example.app_barbaber.ui.theme.Gray_griglia
import com.example.app_barbaber.ui.theme.Gray_grigliaN
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.Screen.Agenda.pop_up.prenotazione_turno
import com.example.app_barber.Screen.Agenda.pop_up.prenotazione_turnoV
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.Prenotazione.Prenotazione_completa
import com.example.app_barber.ui.theme.App_BarberTheme
import com.example.app_barber.ui_app.ViewModel.ClienteViewModel
import com.example.app_barber.ui_app.ViewModel.PrenotazioniViewModel
import com.example.app_barber.ui_app.ViewModel.ServiziViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale


@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, selected: MutableState<ImageVector>, onAccount: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Black_Back,
                        titleContentColor = Color.White
                    ),
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = onAccount) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    modifier = Modifier.size(100.dp),
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = {
                Buttom_Bar(selected = selected, navController = navController)
            }
        ) { innerPadding ->
            // Modifica per allineare tutto a sinistra
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),  // Assicurati che la riga riempia tutto lo spazio disponibile
                horizontalArrangement = Arrangement.Start // Allinea tutto a sinistra
            ) {
                Card_Agenda(prenotazioniViewModel = PrenotazioniViewModel(FirestoreRepository()))
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Card_Agenda(prenotazioniViewModel: PrenotazioniViewModel) {
    val turni by prenotazioniViewModel.turni.observeAsState(emptyList())
    val prenotazione by prenotazioniViewModel.boking_day.observeAsState(emptyList())

    prenotazioniViewModel.getTurni(uid = Firebase.auth.currentUser!!.uid, onSuccess = {}, onFailure = {})

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        prenotazioniViewModel.getPrenotazioniCalendar(date = LocalDate.now(), uid = Firebase.auth.currentUser!!.uid)
    }
    val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        null
    }

    val giornoSettimana = when (currentDate?.dayOfWeek?.value) {
        1 -> "Lunedì"
        2 -> "Martedì"
        3 -> "Mercoledì"
        4 -> "Giovedì"
        5 -> "Venerdì"
        6 -> "Sabato"
        7 -> "Domenica"
        else -> "Giorno sconosciuto"
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        prenotazioniViewModel.getPrenotazioniCalendar(date = LocalDate.now(), uid = Firebase.auth.currentUser!!.uid)
    }

    val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalTime.now()
    } else {
        null
    }


    val turniFiltrati = turni.filter { turno ->
        currentTime?.let {
            val turnoStartTime = LocalTime.parse(turno!!.start)
            turnoStartTime.isAfter(it) || turnoStartTime.equals(it)
        } ?: true
    }

    // Modifica il layout per ottenere una card che occupa metà dello schermo
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start // Aggiunge centratura orizzontale
    ) {
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.5f),  // Imposta la larghezza a metà schermo
            colors = CardDefaults.cardColors(
                containerColor = Black_White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Text(text = "Agenda", color = Color.White, fontSize = 30.sp)

                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = Black_Back
                    )
                ) {
                    Text(text = "Oggi", modifier = Modifier.padding( horizontal = 60.dp).padding(top = 10.dp).align(Alignment.CenterHorizontally), color = Color.White, fontSize = 20.sp)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        items(turniFiltrati.size) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 0.dp, horizontal = 0.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = turniFiltrati[item]?.start ?: "Turno sconosciuto",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.width(60.dp)

                                )

                                val prenotazioneCorrispondente = prenotazione.find { it.turno.id == turniFiltrati[item]?.id }

                                if (prenotazioneCorrispondente != null) {
                                    Item(prenotazioneCorrispondente, isFirstItem = item == 0)
                                } else {
                                    Item_1()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Item(prenotazione: Prenotazione_completa, isFirstItem: Boolean) {
    val backgroundColor = if (isFirstItem) Blue_Ag else Black_Dialog // Colore diverso per il primo turno
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(color = backgroundColor) // Colore di background condizionale
            .border(
                width = 1.dp,
                color = Gray_griglia,
            )
            // Aggiungi padding orizzontale
    ) {
        Text(
            text = prenotazione.cliente.nome + " " + prenotazione.cliente.cognome,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun Item_1() {
    val backgroundColor = Black_Dialog // Colore diverso per il primo turno
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(color = backgroundColor) // Colore di background condizionale
            .border(
                width = 1.dp,
                color = Gray_griglia,
            )
            
    ) {
        // Lascia lo spazio vuoto ma con lo stesso padding
    }
}