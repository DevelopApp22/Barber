package com.example.app_barber.Screen.Servizi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_Dialog
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Black_casellaN
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barber.data.Model.Servizio
import com.example.app_barber.ui_app.Component.CustomTextField
import com.example.app_barber.ui_app.ViewModel.ServiziViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Servizi(navController: NavHostController, serviziViewModel: ServiziViewModel) {

    val servizi by serviziViewModel.servizi.observeAsState(emptyList())
    serviziViewModel.getServizi(FirebaseAuth.getInstance().currentUser!!.uid)

    var showAddServizio by remember { mutableStateOf(false) }

    var showModifyServizio by remember { mutableStateOf(false) }
    var servizioToModify by remember { mutableStateOf<Servizio?>(null) }

    var nome by remember { mutableStateOf("") }
    var prezzo by remember { mutableStateOf("") }

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
                            IconButton(onClick = { navController.navigate(Screen.Account.root) }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowLeft,
                                    modifier = Modifier.size(100.dp),
                                    contentDescription = "Localized description"
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text("Servizi", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(1f))

                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddServizio = true },
                    containerColor = Blue,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(70.dp)
                ) {
                    Icon(
                        Icons.Default.Add, contentDescription = "Add",
                        modifier = Modifier.size(35.dp))
                }
            },
            containerColor = Black_Back
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (showAddServizio) {
                    AlertDialog(
                        containerColor = Black_Dialog,
                        modifier = Modifier
                            .width(900.dp)
                            .padding(vertical = 100.dp, horizontal = 100.dp),
                        onDismissRequest = { showAddServizio = false },
                        properties = DialogProperties(usePlatformDefaultWidth = false),
                        confirmButton = {
                            Button(onClick = {
                                serviziViewModel.createServizio(
                                    FirebaseAuth.getInstance().currentUser!!.uid,
                                    nome,
                                    prezzo,
                                    onSuccess = {
                                        serviziViewModel.getServizi(FirebaseAuth.getInstance().currentUser!!.uid)
                                        showAddServizio = false
                                    }
                                )
                            }) {
                                Text("Conferma")
                            }
                        },
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 1.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = { showAddServizio = false }) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = "Localized description",
                                        tint = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "Aggiungi Turno", color = Color.White)
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        },
                        text = {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                colors = CardDefaults.cardColors(containerColor = Black_casellaN)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Column {
                                        CustomTextField(
                                            value = nome,
                                            onValueChange = { nome = it },
                                            label = { Text("Nome") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 5.dp)
                                        )
                                        CustomTextField(
                                            value = prezzo,
                                            onValueChange = { prezzo = it },
                                            label = { Text("Prezzo") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 5.dp)
                                        )
                                    }

                                }
                            }
                        }
                    )
                }
                if (showModifyServizio && servizioToModify != null) {
                    AlertDialog(
                        containerColor = Black_Dialog,
                        modifier = Modifier
                            .width(900.dp)
                            .padding(vertical = 100.dp, horizontal = 100.dp),
                        onDismissRequest = { showModifyServizio = false },
                        properties = DialogProperties(usePlatformDefaultWidth = false),
                        confirmButton = {
                            Button(onClick = {
                                serviziViewModel.modifyServizio(
                                    FirebaseAuth.getInstance().currentUser!!.uid,
                                    servizioToModify!!.id,
                                    nome,
                                    prezzo,
                                    onSuccess = {
                                        serviziViewModel.getServizi(FirebaseAuth.getInstance().currentUser!!.uid)
                                        showModifyServizio = false
                                    }
                                )
                            }) {
                                Text("Salva")
                            }
                        },
                        title = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 1.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(onClick = { showModifyServizio = false }) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = "Chiudi popup",
                                        tint = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "Modifica Servizio", color = Color.White)
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        },
                        text = {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                colors = CardDefaults.cardColors(containerColor = Black_casellaN)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    CustomTextField(
                                        value = nome,
                                        onValueChange = { nome = it },
                                        label = { Text("Nome") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 5.dp)
                                    )
                                    CustomTextField(
                                        value = prezzo,
                                        onValueChange = { prezzo = it },
                                        label = { Text("Prezzo") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 5.dp)
                                    )
                                }
                            }
                        }
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    items(servizi) { servizio ->
                        ServizioItem(servizio = servizio, serviziViewModel, onEditClick = {
                            servizioToModify = servizio
                            nome = servizio.nome
                            prezzo = servizio.prezzo
                            showModifyServizio = true
                        })
                }
            }
        }
    }
}
}

@Composable
fun ServizioItem(servizio: Servizio, serviziViewModel: ServiziViewModel, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Black_White
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${servizio.nome}: ${servizio.prezzo}€",
                fontSize = 20.sp,
                color = Color.White,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                IconButton(onClick =  onEditClick  ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Localized description",
                        tint = Blue
                    )
                }
                IconButton(onClick = { serviziViewModel.deleteTurno(FirebaseAuth.getInstance().currentUser!!.uid,servizio.id,
                                    onSuccess = { serviziViewModel.getServizi(FirebaseAuth.getInstance().currentUser!!.uid) }) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Localized description",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}