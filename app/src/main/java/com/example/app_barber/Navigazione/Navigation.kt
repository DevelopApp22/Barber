package com.example.app_barber.Navigazione

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_barbaber.Screen.Agenda
import com.example.app_barbaber.Screen.Clienti
import com.example.app_barbaber.Screen.Home
import com.example.app_barbaber.Screen.Magazzino
import com.example.app_barbaber.Screen.Screen

@Composable
fun Navigation_App(navController:NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.root,
    )
    {

        composable(Screen.Home.root) { Home() }
        composable(Screen.Agenda.root) { Agenda() }
        composable(Screen.Clienti.root) { Clienti() }
        composable(Screen.Magazzino.root) { Magazzino() }


    }

}