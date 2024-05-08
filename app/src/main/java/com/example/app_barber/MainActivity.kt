package com.example.app_barber

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.Navigazione.Navigation_App
import com.example.app_barber.Navigazione.TopBar
import com.example.app_barber.ui.theme.App_BarberTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
        setContent {
            App_BarberTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Black_Back
                ) {
                    Main()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main() {
    val navController =
        rememberNavController() //In sostanza, rememberNavController ti consente di ottenere un NavController che è "ricordato" dal framework Compose
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    Scaffold(
        topBar ={
        },
        bottomBar = {
            Buttom_Bar(selected =selected , navController = navController)
        }
    ) {
        Navigation_App(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_BarberTheme {
        Main()
    }
}