package com.example.app_barber.Screen.Clienti


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.ClienteItem
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_Dialog
import com.example.app_barbaber.ui.theme.Black_casellaN
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barbaber.ui.theme.Gray_Text
import com.example.app_barbaber.ui.theme.white30
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.data.Model.Cliente.Cliente
import com.example.app_barber.ui_app.Component.CustomTextField
import com.example.app_barber.ui_app.Component.IconClienti
import com.example.app_barber.ui_app.ViewModel.ClienteViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Clienti(navController: NavHostController, selected: MutableState<ImageVector>, clientViewModel: ClienteViewModel) {
    var showCreateClient by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var deletAllert  by remember { mutableStateOf(false) }
    var cerca by remember { mutableStateOf("") }

    val clienti by clientViewModel.clienti.observeAsState(emptyList())
    clientViewModel.getClienti(FirebaseAuth.getInstance().currentUser!!.uid,{})

    // Filtra i clienti in base al valore di ricerca
    val clientiFiltrati = clienti.filter { cliente ->
        cliente.telefono.contains(cerca, ignoreCase = true) ||
                cliente.nome.contains(cerca, ignoreCase = true) ||
                cliente.cognome.contains(cerca, ignoreCase = true)
    }

    var clienteSelezionato by remember { mutableStateOf<Cliente?>(null) }

    var showModifyClient by remember { mutableStateOf(false) }

    // prenotazioni del cliente
    val clientiCompleto by clientViewModel.prenotazioniStorico.observeAsState()

    // servizi del cliente
    val serviziCliente by clientViewModel.serviziStorico.observeAsState(emptyList())

    // prenotazioni attive del cliente
    val prenotazioni by clientViewModel.prenotazioni.observeAsState(emptyList())
    val isPrenotazioniVuote by clientViewModel.isPrenotazioniVuote.observeAsState(false)


    Scaffold(
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black_Back,
                    titleContentColor =Color.White,
                ),
                title = {
                    Text(
                        "Clienti",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 40.sp,


                        )
                },
            )
        },
        bottomBar = {
            Buttom_Bar(selected = selected, navController = navController)
        },
        containerColor = Black_Back
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)


        ) {
            if (showCreateClient) {
                AlertDialog(
                    containerColor = Black_Dialog,
                    modifier = Modifier
                        .width(900.dp)
                        .padding(vertical = 100.dp, horizontal = 100.dp),
                    onDismissRequest = { showCreateClient = false },
                    properties = DialogProperties(usePlatformDefaultWidth = false),
                    confirmButton = {
                        Button(onClick = {
                            clientViewModel.addCliente(
                                FirebaseAuth.getInstance().currentUser!!.uid,
                                name,
                                surname,
                                telefono,
                                onSucces = {
                                    clientViewModel.getClienti(FirebaseAuth.getInstance().currentUser!!.uid,{})
                                    showCreateClient = false
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
                            IconButton(onClick = { showCreateClient = false }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Localized description",
                                    tint = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Aggiungi Cliente", color = Color.White, style = MaterialTheme.typography.bodyLarge,fontSize = 30.sp)
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
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally // Centers the content horizontally within the Column
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))
                                IconClienti(name =name,surname =surname,size = 140.dp, fontSize = 45.sp)

                                Spacer(modifier = Modifier.height(16.dp)) // Space between Icon and Text

                                Row(
                                    verticalAlignment = Alignment.CenterVertically, // Centers the Text vertically within the Row
                                    horizontalArrangement = Arrangement.Center // Centers the Row's content horizontally
                                ) {
                                    Text(
                                        text = "$name $surname",
                                        color = Color.White,
                                        fontSize = 30.sp,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(25.dp))
                                Row {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = "Person Icon",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .padding(top = 15.dp)
                                    )
                                    Column {
                                        CustomTextField(
                                            value = name,
                                            onValueChange = { name = it },
                                            label = { Text("name") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 5.dp)
                                        )
                                        CustomTextField(
                                            value = surname,
                                            onValueChange = { surname = it },
                                            label = { Text("surname") },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 5.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(15.dp))
                                Row {
                                    Icon(
                                        imageVector = Icons.Filled.Call,
                                        contentDescription = "Person Icon",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .padding(top = 15.dp)
                                    )
                                    CustomTextField(
                                        value = telefono,
                                        onValueChange = { telefono = it },
                                        label = { Text("telefono") },
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



            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = Black_casellaN,
                            shape = RoundedCornerShape(topEnd = 16.dp) // Solo angolo in alto a destra arrotondato
                        )
                        .padding(16.dp)

                ) {
                    Column {
                        CustomTextField(
                            value = cerca,
                            onValueChange = { cerca = it },
                            label = { Text("Cerca cliente") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            showSearchIcon = true, // Mostra l'icona di ricerca
                            onClearClick = { cerca = "" } // Cancella il contenuto della TextField
                        )

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            // Raggruppa i clienti per l'iniziale del nome e ordina
                            val groupedClients = clientiFiltrati
                                .sortedBy { it.nome }  // Sort clients alphabetically by name
                                .groupBy { it.nome.firstOrNull()?.toUpperCase() ?: '#' }
                                .toSortedMap()  // Sort the groups by initial

                            groupedClients.forEach { (initial, clients) ->
                                item {
                                    Text(
                                        text = initial.toString(),
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    )
                                }
                                items(clients) { cliente ->
                                    ClienteItem(
                                        cliente = cliente,
                                        onClick = { clienteSelezionato = cliente },
                                        clienteselezionato = clienteSelezionato==cliente
                                    )
                                }
                            }
                        }

                    }

                    FloatingActionButton(
                        onClick = { showCreateClient = true },
                        containerColor = Blue,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                clienteSelezionato?.let { cliente ->
                    clientViewModel.getStoricoCliente(FirebaseAuth.getInstance().currentUser!!.uid, cliente, {})
                    clientViewModel.getServiziCliente(FirebaseAuth.getInstance().currentUser!!.uid, cliente, {})
                    clientViewModel.getPrenotazioneUtente(FirebaseAuth.getInstance().currentUser!!.uid, cliente, {})

                    var nome by remember(cliente) { mutableStateOf(cliente.nome) }
                    var cognome by remember(cliente) { mutableStateOf(cliente.cognome) }
                    var tel by remember(cliente) { mutableStateOf(cliente.telefono) }
                    if (showModifyClient){
                        AlertDialog(
                            containerColor = Black_Dialog,
                            modifier = Modifier
                                .width(900.dp)
                                .padding(vertical = 100.dp, horizontal = 100.dp),
                            onDismissRequest = { showModifyClient = false },
                            properties = DialogProperties(usePlatformDefaultWidth = false),
                            confirmButton = {
                                Button(onClick = {
                                    clientViewModel.modificaCliente(
                                        FirebaseAuth.getInstance().currentUser!!.uid,
                                        cliente.id,
                                        nome,
                                        cognome,
                                        tel,
                                        onSucces = {
                                            // Aggiorna la lista dei clienti e poi chiudi il dialogo
                                            clientViewModel.getClienti(FirebaseAuth.getInstance().currentUser!!.uid,{
                                                showModifyClient = false
                                                var id = clienteSelezionato?.id
                                                clienteSelezionato = clienti.find { it.id == id }
                                            })

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
                                    IconButton(onClick = { showModifyClient = false }) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = "Localized description",
                                            tint = Color.White
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("Modifica Dati Cliente", color = Color.White,style = MaterialTheme.typography.bodyLarge,fontSize = 30.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            },
                            text = {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Black_casellaN)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                            .verticalScroll(rememberScrollState()),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        IconClienti(name =nome,surname =cognome, size = 140.dp, fontSize = 40.sp)
                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            text = "${nome} ${cognome}",
                                            color = Color.White,
                                            fontSize = 30.sp,
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        Spacer(modifier = Modifier.height(25.dp))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Filled.Person,
                                                contentDescription = "Person Icon",
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .padding(end = 10.dp)
                                                    .size(24.dp)
                                            )
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                CustomTextField(
                                                    value = nome,
                                                    onValueChange = { nome=it },
                                                    label = { Text("Nome") },
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 5.dp)
                                                )
                                                CustomTextField(
                                                    value = cognome,
                                                    onValueChange = { cognome=it },
                                                    label = { Text("Cognome") },
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 5.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(15.dp))

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Filled.Call,
                                                contentDescription = "Phone Icon",
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .padding(end = 10.dp)
                                                    .size(24.dp)
                                            )
                                            CustomTextField(
                                                value = tel,
                                                onValueChange = { tel=it },
                                                label = { Text("Telefono") },
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

                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight()
                            .background(Black_casellaN, shape = RoundedCornerShape(topStart = 16.dp))
                            .padding(16.dp)
                    ) {
                        Column(modifier =Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Filled.ArrowBackIos,
                                    contentDescription = "Modifica cliente",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            clienteSelezionato = null
                                        }
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Modifica cliente",
                                    tint =  Color.White,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            showModifyClient = true
                                        }
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "Elimina cliente",
                                    tint = Color.Red,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable {
                                            deletAllert = true
                                        }
                                )
                            }
                            if (deletAllert) {
                                AlertDialog(
                                    onDismissRequest = { deletAllert = false },
                                    title = { Text("Conferma Eliminazione",style = MaterialTheme.typography.bodyLarge, fontSize = 30.sp,color = Color.White) },
                                    text = { Text("Sei sicuro di voler eliminare questo cliente?",color = Color.Gray, style = MaterialTheme.typography.bodyLarge, fontSize = 20.sp) },
                                    confirmButton = {
                                        Button(onClick = {
                                            clienteSelezionato?.let { cliente ->
                                                clientViewModel.deleteCliente(
                                                    FirebaseAuth.getInstance().currentUser!!.uid,
                                                    cliente.id,
                                                    onSucces = {
                                                        clientViewModel.getClienti(FirebaseAuth.getInstance().currentUser!!.uid, {})
                                                        clienteSelezionato = null
                                                    }
                                                )
                                            }
                                            deletAllert = false // Chiude il dialog
                                        }) {
                                            Text("OK")
                                        }
                                    },
                                    dismissButton = {
                                        Button(onClick = {
                                            deletAllert = false // Chiude il dialog senza eliminare
                                        }) {
                                            Text("Annulla")
                                        }
                                    }
                                )
                            }


                            IconClienti(name =cliente.nome,surname =cliente.cognome, size = 120.dp, fontSize = 50.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = cliente.nome, color = Color.White, fontSize = 35.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = cliente.cognome, color = Color.White, fontSize = 35.sp)
                            }

                            Spacer(modifier = Modifier.height(25.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .border(
                                        1.dp,
                                        color = Gray_Text,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(color = white30, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(text = "Info Cliente", color = Gray_Text)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Filled.Phone,
                                            contentDescription = "Telefono",
                                            tint = Color.White,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = cliente.telefono, color = Color.White)
                                    }
                                }
                            }

                            Divider(
                                color = white30,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .border(
                                        1.dp,
                                        color = Gray_Text,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(color = white30, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(text = "Appuntamento: ", color = Gray_Text)
                                    Spacer(modifier = Modifier.height(12.dp))

                                    if (isPrenotazioniVuote) {
                                        Text(text = "Il cliente non ha nessuna prenotazione attiva", color = Color.White)
                                    } else {
                                        LazyColumn {
                                            items(prenotazioni) { prenotazione ->
                                                Text(
                                                    text = "Giorno: ${prenotazione.data}, Ore: ${prenotazione.turno}",
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .border(
                                        1.dp,
                                        color = Gray_Text,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(color = white30, shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            ) {
                                Column {
                                    Text(text = "Storico", color = Gray_Text)
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Numero totale di prenotazioni: ${clientiCompleto?.numeroPrenotazioni}",
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(text = "Numero di prenotazioni per servizio:", color = Color.White)
                                    LazyColumn {
                                        items(serviziCliente.size) { servizioCliente ->
                                            Text(
                                                text = "- ${serviziCliente[servizioCliente].nomeServizio}: ${serviziCliente[servizioCliente].NumeroVolte}",
                                                color = Color.White
                                            )
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