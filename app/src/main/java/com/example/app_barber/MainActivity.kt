package com.example.app_barber

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.Navigazione.Navigation_App
import com.example.app_barber.Navigazione.TopBar
import com.example.app_barber.ui.theme.App_BarberTheme
import com.example.app_barber.ui_app.ViewModel.AuthViewModel
import com.example.app_barber.ui_app.ViewModel.CalendarViewModel
import com.example.app_barber.ui_app.ViewModel.ClienteViewModel
import com.example.app_barber.ui_app.ViewModel.PrenotazioniViewModel
import com.example.app_barber.ui_app.ViewModel.ServiziViewModel
import com.example.app_barber.ui_app.ViewModel.UserViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate


class MainActivity : ComponentActivity() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // Blocco dell'orientamento

        setContent {
             var Calendario = CalendarViewModel()
            val currentYear = LocalDate.now().year
            val currentMonth = LocalDate.now().monthValue
            LaunchedEffect(Unit) {
                Calendario.updateCalendar(currentYear,currentMonth)
            }
            App_BarberTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Black_Back
                ) {
                    Main(CalendarioVM = Calendario)
                }
            }
        }
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main(CalendarioVM: CalendarViewModel) {
    val navController = rememberNavController() //In sostanza, rememberNavController ti consente di ottenere un NavController che è "ricordato" dal framework Compose
    val selected = remember { mutableStateOf(Icons.Default.Home) }

    Navigation_App(navController,selected,CalendarioVM)

}

