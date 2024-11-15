package com.example.app_barber.ui_app.Screen.Auth

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barber.data.Model.User
import com.example.app_barber.ui_app.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterationUser(navController: NavHostController,  userViewModel: UserViewModel) {
    var user by remember { mutableStateOf(User()) }

    Column {

    }
}