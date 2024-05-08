package com.example.app_barbaber.Screen

sealed class Screen(val root:String){
    data object Home :Screen("home")
    data object Agenda :Screen("agenda")
    data object Magazzino :Screen("magazzino")
    data object Clienti :Screen("clienti")
}