package com.example.app_barber.Screen.Auth


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barbaber.ui.theme.Black_Dialog
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barber.ui_app.Component.CustomTextField
import com.example.app_barber.ui_app.Component.PasswordTextField
import com.example.app_barber.ui_app.ViewModel.AuthViewModel
import com.google.firebase.auth.userProfileChangeRequest

@Composable
fun Login(navController: NavHostController, viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) } // Stato di caricamento

    val authState by viewModel.authState.observeAsState()
    var error by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Login",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
            modifier = Modifier.padding(30.dp)
        )
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .padding(bottom = 20.dp)
                .width(500.dp)
        )
        PasswordTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .padding(bottom = 50.dp)
                .width(500.dp)
        )
        Button(
            onClick = {
                if(email==""||password==""){
                    error = "Inserisisci le credenziali"
                    showDialog = true
                }else{
                    isLoading = true
                    viewModel.loginWithEmail(email, password, onSuccess = {
                        isLoading = false // Ferma il caricamento
                        navController.navigate(Screen.Home.root)
                    }, onFailure = { exception ->
                        isLoading = false // Ferma il caricamento
                        error = exception.message.toString()
                        showDialog = true
                    })
                }

            },
            modifier = Modifier.width(175.dp) .height(50.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Blue), shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Login", fontSize = 25.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                navController.navigate(Screen.Register.root)
            },
            modifier = Modifier
                .padding(bottom = 30.dp)
                .width(175.dp)
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Register",fontSize = 25.sp)
        }
    }

    if (isLoading) {
        // Mostra il caricamento circolare grande al centro dello schermo
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Blue,
                modifier = Modifier.size(100.dp)
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Errore",color = Color.White, style = MaterialTheme.typography.bodyLarge, fontSize = 30.sp) },
            text = { Text(error,color = Color.Gray, style = MaterialTheme.typography.bodyLarge, fontSize = 20.sp) },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text("OK")
                }
            },containerColor = Black_Dialog

        )
    }
}

