package com.example.app_barber.Screen.Auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barber.data.Model.User
import com.example.app_barber.ui_app.Component.CustomTextField
import com.example.app_barber.ui_app.Component.PasswordTextField
import com.example.app_barber.ui_app.ViewModel.AuthViewModel

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavHostController,viewModel: AuthViewModel){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var user by remember { mutableStateOf(User()) }

    var showDialog by remember { mutableStateOf(false) }
    var error  by remember { mutableStateOf("") }
    val authState by viewModel.authState.observeAsState() //osservare lo stato di autenticazione


        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Black_Back,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    title = {
                        Text("")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(Screen.Login.root) }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Localized description",
                            )
                        }
                    },
                )
            },
            containerColor = Black_Back,


            ) { innerPadding ->

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Register",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                    CustomTextField(
                        value = user.name,
                        onValueChange = { newName ->
                            user = user.copy(name = newName)  },
                        label = { Text("Nome") },
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .width(500.dp),
                    )
                    CustomTextField(
                        value = user.surname,
                        onValueChange = {newsurname ->
                            user = user.copy(surname = newsurname)},
                        label = { Text("Cognome") },
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .width(500.dp),
                    )
                    CustomTextField(
                        value = user.name_activity,
                        onValueChange = {newname_activity ->
                            user = user.copy(name_activity = newname_activity) },
                        label = { Text("Nome attività") },
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .width(500.dp),
                    )
                    CustomTextField(
                        value = user.via,
                        onValueChange = {newvia ->
                            user = user.copy( via= newvia) },
                        label = { Text("Via") },
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .width(500.dp),
                    )
                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .width(500.dp),
                    )
                    PasswordTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .padding(bottom = 50.dp)
                            .width(500.dp),
                    )
                    Button(
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                error = "Inserisci i dati richieti"
                                showDialog = true
                                return@Button
                            }

                            viewModel.registerWithEmail(email, password, onSuccess = {
                                viewModel.createUser(viewModel.auth.currentUser!!.uid, user, onSuccess = {
                                    navController.navigate(Screen.Home.root)
                                },{})

                            }, onFailure = { exception ->
                                error = exception.message.toString()
                                showDialog = true

                            })

                        },modifier = Modifier
                            .padding(bottom = 30.dp)
                            .width(175.dp)
                            .height(50.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "Register",fontSize = 25.sp)
                    }

                }


            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Errore") },
                    text = { Text(error) },
                    confirmButton = {
                        Button(onClick = {
                            // Logica per riprovare
                            showDialog = false
                        }) {
                            Text("OK")
                        }
                    },

                    )
            }
        }

}
