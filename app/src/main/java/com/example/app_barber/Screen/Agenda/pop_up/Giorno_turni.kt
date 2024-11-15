package com.example.app_barber.Screen.Agenda.pop_up

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.app_barbaber.Screen.ClienteItem
import com.example.app_barbaber.Screen.ServizioItem
import com.example.app_barbaber.ui.theme.Black_Agenda
import com.example.app_barbaber.ui.theme.Black_Dialog
import com.example.app_barbaber.ui.theme.Black_casellaN
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barbaber.ui.theme.Gray_Text
import com.example.app_barbaber.ui.theme.Gray_griglia
import com.example.app_barbaber.ui.theme.Gray_grigliaN
import com.example.app_barbaber.ui.theme.white30
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.data.Model.Day
import com.example.app_barber.data.Model.Prenotazione.Prenotazione
import com.example.app_barber.data.Model.Prenotazione.Prenotazione_completa
import com.example.app_barber.data.Model.Servizio
import com.example.app_barber.data.Model.Turno
import com.example.app_barber.ui.theme.CustomFontFamily
import com.example.app_barber.ui_app.Component.CustomTextField
import com.example.app_barber.ui_app.ViewModel.CalendarViewModel
import com.example.app_barber.ui_app.ViewModel.ClienteViewModel
import com.example.app_barber.ui_app.ViewModel.PrenotazioniViewModel
import com.example.app_barber.ui_app.ViewModel.ServiziViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun showPrenotazioniDialog(
    showDialog: MutableState<Boolean>,
    date: LocalDate,
    mese: String,
    turni: List<Turno?>,
    prenotazione: List<Prenotazione_completa>,
    prenotazioniViewModel: PrenotazioniViewModel,
    paddingValues: PaddingValues,
    calenderViewModel: CalendarViewModel,
    day: MutableState<Day?>
) {
    val isDataLoaded = remember { mutableStateOf(false) }

    // Aggiungi un delay per simulare il caricamento dei dati
    LaunchedEffect(turni, prenotazione) {
        delay(500)
        if (turni.isNotEmpty() || prenotazione.isNotEmpty()) {
            isDataLoaded.value = true
        }
    }

    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()

    AlertDialog(
        containerColor = Black_Dialog,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 200.dp),
        onDismissRequest = { showDialog.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        confirmButton = {},
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { showDialog.value = false }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${date.dayOfMonth} $mese",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        text = {
            Card(
                modifier = Modifier.fillMaxSize(1f),
                colors = CardDefaults.cardColors(containerColor = Black_casellaN)
            ) {
                AnimatedVisibility(
                    visible = isDataLoaded.value,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300)) + slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        // Ordina i turni per l'ora di inizio
                        val sortedTurni = turni.sortedBy { it?.start }

                        items(sortedTurni.size) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val turno = sortedTurni[item]
                                val turnoTime = turno?.start?.let { LocalTime.parse(it) }

                                // Controlla se la data selezionata è oggi e se l'ora del turno è passata
                                val turnoScaduto = date == currentDate && turnoTime?.isBefore(currentTime) == true

                                // Mostra il turno allineato a sinistra
                                Text(
                                    text = turno?.start ?: "Turno sconosciuto",
                                    color = Gray_grigliaN,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                        .width(70.dp),
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                // Cerca la prenotazione corrispondente al turno corrente
                                val prenotazioneCorrispondente = prenotazione.find { it.turno.id == turno?.id }

                                if (prenotazioneCorrispondente != null) {
                                    // Mostra la prenotazione corrispondente se esiste
                                    prenotazione_turno(prenotazioneCorrispondente, prenotazioniViewModel, date, day)
                                } else {
                                    // Mostra una prenotazione vuota o placeholder se non esiste prenotazione
                                    prenotazione_turnoV(
                                        turno!!,
                                        clienteViewModel = ClienteViewModel(firestoreRepository = FirestoreRepository()),
                                        paddingValues = paddingValues,
                                        serviziViewModel = ServiziViewModel(firestoreRepository = FirestoreRepository()),
                                        date = date,
                                        prenotazioniViewModel = prenotazioniViewModel,
                                        calenderViewModel = calenderViewModel,
                                        day = day,
                                        turnoscaduto = turnoScaduto
                                    )
                                }
                            }
                        }
                    }

                }
                // Mostra un indicatore di caricamento mentre i dati non sono pronti
                if (!isDataLoaded.value) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun prenotazione_turno(pretazione:Prenotazione_completa,prenotazioniViewModel: PrenotazioniViewModel,date: LocalDate,day: MutableState<Day?>){
    var delete_prenotazione by remember { mutableStateOf(false) }
    var firestoreRepository = FirestoreRepository()
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth(1f)
           ,
        color = Black_Dialog

    ) {




            Row(
                verticalAlignment = Alignment.CenterVertically, // Allinea tutti gli elementi verticalmente al centro
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "${pretazione.cliente.nome} ${pretazione.cliente.cognome}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = pretazione.prenotazione.servizioId,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        delete_prenotazione = true
                    }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
    }
    if(delete_prenotazione){
        AlertDialog(onDismissRequest = { delete_prenotazione=false }, confirmButton ={
            Button(onClick = {
                delete_prenotazione=false
                prenotazioniViewModel.deletePrenotazione(FirebaseAuth.getInstance().currentUser!!.uid,pretazione,{
                    prenotazioniViewModel.getPrenotazioniCalendar(
                        uid = FirebaseAuth.getInstance().currentUser!!.uid,
                        date = date
                    )
                },{
                    prenotazioniViewModel.getDayCalendar(date) { fetchedDay ->
                        day.value = fetchedDay
                    }
                })
            }) {
                Text(text = "Conferma", style = MaterialTheme.typography.labelLarge)
            }


        }, title = { Text(text = "Eliminazione Prenotazione",color = Color.White) }, text = { Text(text = "Sei sicuro di voler eliminare questa prenotazione?",color = Color.White, style = MaterialTheme.typography.labelLarge) })
    }


}

@ExperimentalFoundationApi
@SuppressLint("Range")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun prenotazione_turnoV(turno: Turno,clienteViewModel: ClienteViewModel,paddingValues: PaddingValues,serviziViewModel: ServiziViewModel,date:LocalDate,prenotazioniViewModel: PrenotazioniViewModel,calenderViewModel: CalendarViewModel,day: MutableState<Day?>,turnoscaduto:Boolean) {

    var insert_Prentotazione by remember { mutableStateOf(false) }
    var errore by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth(1f)
            .height(50.dp)
            .clickable {
                if (!turnoscaduto)
                    insert_Prentotazione = true
                else {
                    errore = true

                }
            },
        color = if (turnoscaduto) Gray_grigliaN else Blue

    ) {
        // AlertDialog che avvisa l'utente che il turno è passato
        if (errore) {
            AlertDialog(
                onDismissRequest = { errore = false },
                title = {
                    Text(
                        text = "Turno Passato",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                text = {
                    Text(
                        text = "L'ora del turno ${turno.start} è già passata. Non è possibile prenotare per questo turno.",
                        color = Color.White, style = MaterialTheme.typography.labelLarge
                    )
                },
                confirmButton = {
                    Button(onClick = { errore = false }) {
                        Text("OK", style = MaterialTheme.typography.labelLarge)
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(40.dp))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Localized description",
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp) // Dimensione fissa per l'icona
                )
            }
        }

    }


    if (insert_Prentotazione) {
        var clienteSelezionato by remember { mutableStateOf<Cliente?>(null) }
        var serzioSelezionato by remember { mutableStateOf<Servizio?>(null) }
        AlertDialog(
            containerColor = Black_Dialog,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 200.dp),

            onDismissRequest = { insert_Prentotazione = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            confirmButton = {
                if (clienteSelezionato != null && serzioSelezionato != null) {

                    Button(onClick = {
                        prenotazioniViewModel.createPrenotazione(uid = FirebaseAuth.getInstance().currentUser!!.uid,
                            Prenotazione(
                                UUID.randomUUID().toString(),
                                clienteSelezionato!!.id,
                                serzioSelezionato!!.id,
                                date.toString(),
                                turno.id
                            ),
                            onSuccess = {
                                prenotazioniViewModel.getPrenotazioniCalendar(
                                    uid = FirebaseAuth.getInstance().currentUser!!.uid,
                                    date = date
                                )
                                insert_Prentotazione = false
                            },
                            onUpdate = {

                                prenotazioniViewModel.getDayCalendar(date) { fetchedDay ->
                                    day.value = fetchedDay
                                }
                            })


                    }) {
                        Text(text = "Conferma", style = MaterialTheme.typography.labelLarge)
                    }
                }
            },
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (clienteSelezionato != null && serzioSelezionato == null) {
                        IconButton(onClick = {
                            clienteSelezionato = null
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }

                    }
                    if (serzioSelezionato != null) {
                        IconButton(onClick = {
                            serzioSelezionato = null
                        }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }

                    }
                    if (clienteSelezionato == null && serzioSelezionato == null) {
                        IconButton(onClick = {
                            insert_Prentotazione = false
                        }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }

                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (clienteSelezionato != null && serzioSelezionato != null) {
                        Text(
                            text = "Conferma appuntamento",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    } else {

                        Text(
                            text = "Registra appuntamento",
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }



                    Spacer(modifier = Modifier.weight(1f))


                }

            },
            text = {
                Card(
                    modifier = Modifier
                        .fillMaxSize(2f),
                    // Riempie lo spazio disponibile
                    colors = CardDefaults.cardColors(
                        containerColor = Black_casellaN
                    )
                ) {
                    val servizi by serviziViewModel.servizi.observeAsState(emptyList())

                    var query by remember { mutableStateOf("") }

                    clienteViewModel.getClienti(uid = FirebaseAuth.getInstance().currentUser!!.uid,{})
                    serviziViewModel.getServizi(uid = FirebaseAuth.getInstance().currentUser!!.uid)

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        if (clienteSelezionato != null && serzioSelezionato == null) {

                            Column {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp)
                                ) {


                                    items(servizi.size) { item ->
                                        ServizioItem(
                                            servizi[item],
                                            onClick = { serzioSelezionato = servizi[item] })

                                    }
                                }
                            }


                        }
                        if (serzioSelezionato != null && clienteSelezionato != null) {
                            infoprenotazione(
                                clienteSelezionato!!,
                                serzioSelezionato!!,
                                turno.start,
                                date.toString()
                            )
                        }
                        if (clienteSelezionato == null) {

                            val query by clienteViewModel.searchQuery.observeAsState(" ")
                            val filteredClients by clienteViewModel.filteredClients.observeAsState(
                                emptyList()
                            )



                            Column {
                                Row(modifier = Modifier.padding(bottom = 10.dp)) {

                                    CustomTextField(
                                        value = query,

                                        onValueChange = {
                                            clienteViewModel.updateSearchQuery(it)
                                            if (it.isNotEmpty()) {

                                            }
                                        },
                                        label = { Text("Cerca cliente per telefono") },
                                        modifier = Modifier.fillMaxWidth(),


                                        )


                                }


                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp)
                                ) {


                                    items(filteredClients.size) { item ->
                                        ClienteItem(cliente = filteredClients[item], onClick = {
                                            clienteSelezionato = filteredClients[item]

                                        })

                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}



@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun infoprenotazione(cliente: Cliente, servizio: Servizio, turno: String, date: String) {
    // Converti la stringa `date` in `LocalDate`, poi formattala
    val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val formattedDate = localDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("it")))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Filled.DateRange,
                contentDescription = "Localized description",
                tint = Color.White,
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(), // Riempie la larghezza per consentire il centramento del testo
                horizontalAlignment = Alignment.CenterHorizontally // Allinea al centro orizzontalmente
            ) {
                Text(
                    text = formattedDate,
                    color = Color.White,
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center // Centro il testo della data
                )
                Spacer(modifier = Modifier.height(4.dp)) // Usare height per creare spazio verticale
                Text(
                    text = "Turno $turno",
                    color = Gray_grigliaN,
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center // Centro il testo del turno
                )
            }
        }


        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .border(1.dp, color = Gray_Text, shape = RoundedCornerShape(8.dp))
                    .background(color = white30, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Info Cliente",
                        color = Gray_Text,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 22.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nome: ${cliente.nome}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 18.sp,
                    )
                    Text(
                        text = "Cognome: ${cliente.cognome}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Telefono: ${cliente.telefono}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 18.sp,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .border(1.dp, color = Gray_Text, shape = RoundedCornerShape(8.dp))
                    .background(color = white30, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Servizio",
                        color = Gray_Text,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 22.sp,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Nome: ${servizio.nome}",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Prezzo: ${servizio.prezzo}€",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 18.sp,
                    )
                }
            }
        }



    }
}

