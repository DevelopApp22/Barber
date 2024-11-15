package com.example.app_barbaber. Screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_barbaber.ui.theme.Black_Agenda
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Black_casellaN
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barbaber.ui.theme.Blue_Ag
import com.example.app_barbaber.ui.theme.Gray_Text
import com.example.app_barbaber.ui.theme.Gray_griglia
import com.example.app_barbaber.ui.theme.Gray_grigliaN
import com.example.app_barbaber.ui.theme.Green
import com.example.app_barbaber.ui.theme.Red
import com.example.app_barbaber.ui.theme.white30
import com.example.app_barber.Funzioni.Schermo.TextType
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.Screen.Agenda.pop_up.showPrenotazioniDialog
import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.data.Model.Day
import com.example.app_barber.data.Model.Servizio
import com.example.app_barber.ui_app.Component.IconClienti
import com.example.app_barber.ui_app.ViewModel.CalendarViewModel
import com.example.app_barber.ui_app.ViewModel.PrenotazioniViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import java.time.LocalDate


@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Agenda(
    navController: NavHostController,
    selected: MutableState<ImageVector>,
    prenotazioniViewModel: PrenotazioniViewModel,
    calendarViewModel: CalendarViewModel,
) {
    val currentdate = remember { LocalDate.now() }
    var currentYear by remember { mutableStateOf(currentdate.year) }

    var momentYear by remember { mutableStateOf(currentYear) }
    var momentMonth by remember { mutableStateOf(currentdate.monthValue) }

    var isLoaded by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    val calendar by calendarViewModel.calendar.observeAsState(emptyArray())

    LaunchedEffect(Unit) {
        delay(1000)
        showContent = true
    }

    val daysOfWeek = listOf("Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica")

    var contentHeightDp by remember { mutableStateOf(0.dp) }
    var weekHeightDp by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val caselladp = remember(contentHeightDp, weekHeightDp) {
        (contentHeightDp - weekHeightDp) / 6
    }

    val mese = remember(momentMonth) {
        when (momentMonth) {
            1 -> "Gennaio"
            2 -> "Febbraio"
            3 -> "Marzo"
            4 -> "Aprile"
            5 -> "Maggio"
            6 -> "Giugno"
            7 -> "Luglio"
            8 -> "Agosto"
            9 -> "Settembre"
            10 -> "Ottobre"
            11 -> "Novembre"
            12 -> "Dicembre"
            else -> "null"
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Black_Back,
                        titleContentColor = Color.White,
                    ),
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                isLoaded = true
                                if (momentMonth == 1) {
                                    momentMonth = 12
                                    momentYear--
                                } else {
                                    momentMonth--
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Go to previous month"
                                )
                            }

                            Text(
                                text = "$mese $momentYear",
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = 30.sp
                            )
                            IconButton(onClick = {
                                isLoaded = true
                                if (momentMonth == 12) {
                                    momentMonth = 1
                                    momentYear++
                                } else {
                                    momentMonth++
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "Go to next month"
                                )
                            }
                        }
                    },
                )
            },
            containerColor = Black_Back,
            bottomBar = {
                Buttom_Bar(selected = selected, navController = navController)
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .onGloballyPositioned { layoutCoordinates ->
                        contentHeightDp = with(density) { layoutCoordinates.size.height.toDp() }
                    }
            ) {
                val selectedMonth = calendar.flatten()
                if (isLoaded || !showContent) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = Blue,
                            trackColor = white30
                        )
                    }

                    calendarViewModel.updateCalendar(momentYear, momentMonth)
                    isLoaded = false
                } else {
                    Column {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                                weekHeightDp = with(density) { layoutCoordinates.size.height.toDp() }
                            },
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            items(daysOfWeek) { day ->
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = day,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            items(42) { index ->
                                val date = selectedMonth.getOrNull(index)
                                if (date != null && (date.isBefore(currentdate) || date.monthValue != momentMonth)) {
                                    casella_N(date, caselladp)
                                } else {
                                    val day = remember { mutableStateOf<Day?>(null) }
                                    casella(
                                        date = date,
                                        check = currentdate == date,
                                        caselladp = caselladp,
                                        dayState = day,
                                        prenotazioniViewModel = prenotazioniViewModel,
                                        paddingValues = innerPadding,
                                        calendarViewModel = calendarViewModel
                                    )

                                    if (date != null) {
                                        prenotazioniViewModel.getDayCalendar(date) { fetchedDay ->
                                            day.value = fetchedDay
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@SuppressLint("Range")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun casella(
    date: LocalDate?,
    check: Boolean,
    caselladp: Dp,
    dayState: MutableState<Day?>,
    prenotazioniViewModel: PrenotazioniViewModel,
    paddingValues: PaddingValues,
    calendarViewModel: CalendarViewModel
) {
    val day = date?.dayOfMonth
    val showDialog = remember { mutableStateOf(false) }
    val month = date?.monthValue ?: 0

    val turni by prenotazioniViewModel.turni.observeAsState(emptyList())
    val prenotazione by prenotazioniViewModel.boking_day.observeAsState(emptyList())

    val mese = when (month) {
        1 -> "Gennaio"
        2 -> "Febbraio"
        3 -> "Marzo"
        4 -> "Aprile"
        5 -> "Maggio"
        6 -> "Giugno"
        7 -> "Luglio"
        8 -> "Agosto"
        9 -> "Settembre"
        10 -> "Ottobre"
        11 -> "Novembre"
        12 -> "Dicembre"
        else -> "null"
    }

    Box(
        modifier = Modifier
            .height(caselladp)
            .border(0.5.dp, Gray_griglia)
            .clickable {
                showDialog.value = true
                val uid = FirebaseAuth.getInstance().currentUser!!.uid
                prenotazioniViewModel.getTurni(uid = uid, onSuccess = {}, onFailure = {})
                prenotazioniViewModel.getPrenotazioniCalendar(date = date!!, uid = uid)
            }
            .background(if (check) Blue_Ag else Black_Agenda),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = day?.toString() ?: "",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(
                            when (dayState.value?.isFull) {
                                true -> Red
                                false -> Green
                                null -> Color.Gray
                            }
                        )
                )
            }
        }
    }
    if (showDialog.value) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        prenotazioniViewModel.getTurni(uid = uid, onSuccess = {}, onFailure = {})
        prenotazioniViewModel.getPrenotazioniCalendar(date = date!!, uid = uid)

        showPrenotazioniDialog(
            showDialog = showDialog,
            date = date!!,
            mese = mese,
            turni = turni,
            prenotazione = prenotazione,
            paddingValues = paddingValues,
            prenotazioniViewModel = prenotazioniViewModel,
            calenderViewModel = calendarViewModel,
            day = dayState
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun casella_N(date: LocalDate?, caselladp: Dp) {
    val day = date?.dayOfMonth
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(1f)
            .border(0.5.dp, Gray_grigliaN)
            .clickable {},
        color = Black_casellaN
    ) {
        Box(
            modifier = Modifier.height(caselladp),
            contentAlignment = Alignment.TopEnd
        ) {
            Column {
                Text(
                    text = day?.toString() ?: "",
                    color = Gray_Text,
                    modifier = Modifier.padding(end = 8.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



@Composable
fun ClienteItem(cliente: Cliente, onClick: () -> Unit, clienteselezionato: Boolean = false) {
    // Genera un colore fisso basato sul nome o ID del cliente

    Card(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(16.dp)),
        colors = if (clienteselezionato==false) {CardDefaults.cardColors(containerColor = Black_White)} else {CardDefaults.cardColors(containerColor = Blue)}
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconClienti(name = cliente.nome, surname = cliente.cognome)



            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "${cliente.nome} ${cliente.cognome}",
                    color = Color.White,
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = cliente.telefono,
                    color = Gray_grigliaN,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



@Composable
fun ServizioItem(servizio: Servizio, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Black_White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column {
                Text(
                    text = servizio.nome,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${servizio.prezzo}€ ",
                    color = Gray_grigliaN,
                    fontSize = 16.sp
                )
            }

        }
    }
}




