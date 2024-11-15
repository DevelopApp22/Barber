package com.example.app_barbaber.Screen

sealed class Screen(val root:String) {
    data object Home : Screen("home")
    data object Agenda : Screen("agenda")
    data object Magazzino : Screen("magazzino")
    data object Clienti : Screen("clienti")
    data object Account : Screen("account")
    data object Turni : Screen("turni")
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Register_user : Screen("register_user")
    data object Servizi :Screen("servizi")

}