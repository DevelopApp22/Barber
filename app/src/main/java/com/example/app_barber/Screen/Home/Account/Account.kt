package com.example.app_barber.ui_app.Screen.Home.Account

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barbaber.ui.theme.Black_Agenda
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_Dialog
import com.example.app_barbaber.ui.theme.Black_Dialog_1
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Black_casellaN
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barbaber.ui.theme.Blue_Ag
import com.example.app_barbaber.ui.theme.Gray_Text
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.data.Model.User
import com.example.app_barber.ui_app.Component.IconClienti
import com.example.app_barber.ui_app.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.roundToInt

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(selected: MutableState<ImageVector>,navController: NavHostController, userViewModel: UserViewModel) {
    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    val user by userViewModel.user.observeAsState()
    val serviziData by userViewModel.serviziStats.observeAsState()
    val prenotazioniData by userViewModel.prenotazioniMensili.observeAsState()
    val bookingFrequency by userViewModel.bookingFrequency.observeAsState(emptyMap())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black_Back
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Black_Back,
                        titleContentColor = Color.White
                    ),
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { navController.navigate(Screen.Home.root) }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowLeft,
                                    modifier = Modifier.size(100.dp),
                                    contentDescription = "Localized description"
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                )
            },
            containerColor = Black_Back, bottomBar = { Buttom_Bar(selected = selected, navController = navController) },
        ) { innerPadding ->
            if (auth.currentUser != null) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Sezione Profilo utente
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            // Prima Card che occupa la larghezza massima disponibile
                            Card(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .weight(1f) // Occupa tutto lo spazio disponibile a sinistra
                                    .height(408.dp), // Altezza fissa
                                colors = CardDefaults.cardColors(containerColor = Black_Dialog),
                                elevation = CardDefaults.cardElevation(20.dp)
                            ) {

                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Row contenente il titolo e l'icona di logout
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Account",
                                            color = Color.White,
                                            fontSize = 40.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.weight(1f) // Occupa lo spazio rimanente
                                        )

                                        IconButton(onClick = {
                                            // Gestisci l'azione di logout qui
                                            auth.signOut()
                                            navController.navigate("login") {
                                                popUpTo(0) // Clear backstack for safe navigation
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Logout,
                                                contentDescription = "Logout",
                                                tint = Color.Red,
                                                modifier = Modifier.size(40.dp)
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                           user?.name?.let { IconClienti(name = it, surname = user?.surname ?: "" ,size = 200.dp, fontSize = 60.sp) }
                                            user?.let {
                                                Text(
                                                    text = it.name_activity,
                                                    color = Color.LightGray,
                                                    fontSize = 30.sp
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(16.dp)) // Spazio tra l'icona e il divider

                                        // Divider verticale tra icona e informazioni
                                        Divider(
                                            modifier = Modifier
                                                .height(100.dp)  // Altezza del divider in base alla grandezza dell'icona
                                                .width(1.dp),  // Divider sottile
                                            color = Color.LightGray
                                        )

                                        Spacer(modifier = Modifier.width(16.dp)) // Spazio tra il divider e le informazioni
                                        // Informazioni dell'utente
                                        Column(
                                            verticalArrangement = Arrangement.Center // Allinea verticalmente al centro rispetto all'icona
                                        ) {
                                            userViewModel.getUser(
                                                auth.currentUser?.uid ?: "",
                                                {},
                                                {})
                                            user?.let {
                                                Text(
                                                    text = it.name,
                                                    color = Color.LightGray,
                                                    fontSize = 25.sp
                                                )
                                            }
                                            user?.let {
                                                Text(
                                                    text = it.surname,
                                                    color = Color.LightGray,
                                                    fontSize = 25.sp
                                                )
                                            }
                                            user?.let {
                                                Text(
                                                    text = it.telephone_activity,
                                                    color = Color.LightGray,
                                                    fontSize = 25.sp
                                                )
                                            }
                                            user?.let {
                                                Text(
                                                    text = it.via,
                                                    color = Color.LightGray,
                                                    fontSize = 25.sp
                                                )
                                            }
                                        }
                                    }
                                }


                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            // Colonna con due Card quadrate
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                            ) {
                                Card(
                                    onClick = { navController.navigate(Screen.Servizi.root)},
                                    modifier = Modifier
                                        .width(200.dp) // Larghezza fissa
                                        .aspectRatio(1f), // Card quadrata
                                    colors = CardDefaults.cardColors(containerColor = Blue),
                                    elevation = CardDefaults.cardElevation(40.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.Center),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ContentCut,
                                                contentDescription = "Servizi Icon",
                                                Modifier.size(50.dp),
                                                tint = Color.White
                                            )
                                            Spacer(modifier = Modifier.height(15.dp))
                                            Text(
                                                text = "Servizi",
                                                color = Color.White,
                                                style = MaterialTheme.typography.bodyLarge,
                                                textAlign = TextAlign.Center,
                                                fontSize = 30.sp
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "Next",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(8.dp)
                                                .size(35.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Card(
                                    onClick = { navController.navigate(Screen.Turni.root)},
                                    modifier = Modifier
                                        .width(200.dp) // Larghezza fissa
                                        .aspectRatio(1f), // Card quadrata
                                    colors = CardDefaults.cardColors(containerColor = Blue),
                                    elevation = CardDefaults.cardElevation(40.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.align(Alignment.Center),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AccessTime,
                                                contentDescription = "Turni Icon",
                                                Modifier.size(50.dp),
                                                tint = Color.White
                                            )
                                            Spacer(modifier = Modifier.height(15.dp))
                                            Text(
                                                text = "Turni",
                                                color = Color.White,
                                                style = MaterialTheme.typography.bodyLarge,
                                                textAlign = TextAlign.Center,
                                                fontSize = 30.sp
                                            )
                                        }
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "Next",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(8.dp)
                                                .size(35.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }


// Sezione Statistiche racchiusa in una Card
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Black_Dialog),
                            elevation = CardDefaults.cardElevation(20.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Titolo grande a sinistra
                                Text(
                                    text = "Statistiche",
                                    color = Color.White,
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(bottom = 16.dp)
                                )

                                // Grafici Servizi e Frequenza Prenotazioni affiancati
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    userViewModel.fetchServiziStats(auth.currentUser?.uid ?: "")
                                    serviziData?.let {
                                        ServiziPieChart(
                                            serviziData = it,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }

                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Grafico Prenotazioni Mensili
                                userViewModel.fetchPrenotazioniMensili(uid = auth.currentUser!!.uid)
                                prenotazioniData?.let { PrenotazioniMensiliChart(it) }
                            }
                        }
                    }

                }
            } else {

            }
        }
    }
}

@Composable
fun ServiziPieChart(serviziData: Map<String, Int>, modifier: Modifier = Modifier) {
    val baseColors = listOf(
        Color(0xFF4CAF50), Color(0xFFFFC107), Color(0xFF03A9F4),
        Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF673AB7)
    )
    val sliceColors = List(serviziData.size) { baseColors[it % baseColors.size] }
    val totalServizi = serviziData.values.sum().toFloat()

    Card(
        modifier = modifier
            .height(500.dp) // Altezza fissa per mantenere le due card allineate
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Black_Dialog_1),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Servizi Statistiche",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Canvas(modifier = Modifier.size(200.dp)) {
                var startAngle = 0f
                serviziData.entries.forEachIndexed { index, entry ->
                    val sliceAngle = 360 * (entry.value / totalServizi)
                    drawArc(
                        color = sliceColors[index],
                        startAngle = startAngle,
                        sweepAngle = sliceAngle,
                        useCenter = true
                    )
                    startAngle += sliceAngle
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(serviziData.entries.toList()) { (name, count) ->
                    val percentage = (count / totalServizi * 100).roundToInt()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(
                                        color = sliceColors[serviziData.keys.indexOf(name)],
                                        shape = CircleShape
                                    )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = name,
                                color = Color.White,
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        Text(
                            text = "$percentage%",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatisticheSection(userViewModel: UserViewModel, auth: FirebaseAuth) {
    val serviziData by userViewModel.serviziStats.observeAsState()
    val bookingFrequency by userViewModel.bookingFrequency.observeAsState(emptyMap())

    var isLoading by remember { mutableStateOf(true) }

    // Avvio del caricamento dati e impostazione dello stato di caricamento
    LaunchedEffect(Unit) {
        auth.currentUser?.uid?.let { uid ->
            userViewModel.fetchServiziStats(uid)
            userViewModel.fetchBookingFrequency(uid)
            isLoading = false // Ferma il caricamento quando i dati sono pronti
        }
    }

    if (isLoading) {
        // Mostra il caricamento circolare blu al centro della sezione
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator(
                color = Color.Blue,
                modifier = Modifier.size(80.dp)
            )
        }
    } else {
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            serviziData?.let {
                ServiziPieChart(
                    serviziData = it,
                    modifier = Modifier.weight(1f)
                )
            }

            CustomerBookingFrequencyChart(
                bookingFrequency,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CustomerBookingFrequencyChart(frequencyData: Map<String, Float>, modifier: Modifier = Modifier) {
    val colors = listOf(Color.Cyan, Color.Magenta, Color.Yellow, Color.Green, Color.Blue)

    Card(
        modifier = modifier
            .height(500.dp)
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Black_Dialog_1),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Frequenza di Prenotazioni",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Canvas(
                modifier = Modifier.size(200.dp)
            ) {
                var startAngle = 0f
                val total = frequencyData.values.sum()

                frequencyData.entries.forEachIndexed { index, entry ->
                    val sweepAngle = (entry.value / total) * 360f
                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true
                    )
                    startAngle += sweepAngle
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(frequencyData.entries.toList()) { (key, value) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(colors[frequencyData.keys.indexOf(key) % colors.size])
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$key: ${"%.1f".format(value)}%",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrenotazioniMensiliChart(prenotazioniData: Map<String, Map<String, Int>>) {
    val maxPrenotazioni = prenotazioniData.values.flatMap { it.values }.maxOrNull() ?: 0
    val sortedYears = prenotazioniData.keys.sorted()
    var selectedYearIndex by remember { mutableStateOf(0) }
    val selectedYear = sortedYears.getOrNull(selectedYearIndex) ?: return // Mostra solo se ci sono dati

    val mesi = listOf("Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic")

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .height(400.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Black_Dialog_1),
        elevation = CardDefaults.cardElevation(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),  // Imposta l'altezza della colonna in base al contenuto
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Statistiche Prenotazioni Mensili per Anno",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Navigazione tra gli anni
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { if (selectedYearIndex > 0) selectedYearIndex-- },
                    enabled = selectedYearIndex > 0
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Anno precedente", tint = Color.White)
                }

                Text(
                    text = "Anno $selectedYear",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 22.sp

                )

                IconButton(
                    onClick = { if (selectedYearIndex < sortedYears.size - 1) selectedYearIndex++ },
                    enabled = selectedYearIndex < sortedYears.size - 1
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Anno successivo", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                ) {
                    val barWidth = size.width / (mesi.size * 2)
                    val maxBarHeight = size.height - 20.dp.toPx()

                    // Scala dei numeri sul lato sinistro
                    for (i in 0..4) {
                        val y = size.height - (i * maxBarHeight / 4)
                        drawContext.canvas.nativeCanvas.drawText(
                            "${(maxPrenotazioni * i / 4)}",
                            0f,
                            y,
                            android.graphics.Paint().apply {
                                textAlign = android.graphics.Paint.Align.RIGHT
                                color = android.graphics.Color.WHITE
                                textSize = 24f
                            }
                        )
                    }

                    mesi.forEachIndexed { index, mese ->
                        val meseKey = (index + 1).toString().padStart(2, '0')
                        val prenotazioniCount = prenotazioniData[selectedYear]?.get(meseKey) ?: 0
                        val barHeight = (prenotazioniCount / maxPrenotazioni.toFloat()) * maxBarHeight

                        val xOffset = index * (barWidth * 2) + barWidth / 2

                        drawRect(
                            color = Blue,
                            topLeft = Offset(x = xOffset, y = size.height - barHeight),
                            size = Size(width = barWidth, height = barHeight)
                        )

                        // Aggiungi l'etichetta del mese con padding sotto la barra
                        drawContext.canvas.nativeCanvas.drawText(
                            mese,
                            xOffset + barWidth / 2,  // Allinea il testo al centro della barra
                            size.height + 25.dp.toPx(),  // Posiziona con padding sotto la barra
                            android.graphics.Paint().apply {
                                textAlign = android.graphics.Paint.Align.CENTER
                                color = android.graphics.Color.WHITE
                                textSize = 24f
                            }
                        )
                    }
                }
            }
        }
    }
}