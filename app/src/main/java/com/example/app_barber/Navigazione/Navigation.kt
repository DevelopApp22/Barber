package com.example.app_barber.Navigazione





import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.app_barbaber.Screen.Agenda
import com.example.app_barbaber.Screen.Home
import com.example.app_barbaber.Screen.Magazzino
import com.example.app_barbaber.Screen.Screen
import com.example.app_barber.data.FirestoreRepository
import com.example.app_barber.ui_app.ViewModel.UserViewModel
import com.example.app_barber.Screen.Auth.Login
import com.example.app_barber.Screen.Auth.Register
import com.example.app_barber.Screen.Clienti.Clienti
import com.example.app_barber.Screen.Home.Account.Turni
import com.example.app_barber.Screen.Servizi.Servizi
import com.example.app_barber.ui_app.Screen.Auth.RegisterationUser
import com.example.app_barber.ui_app.Screen.Home.Account.Account
import com.example.app_barber.ui_app.ViewModel.AuthViewModel
import com.example.app_barber.ui_app.ViewModel.CalendarViewModel
import com.example.app_barber.ui_app.ViewModel.ClienteViewModel
import com.example.app_barber.ui_app.ViewModel.PrenotazioniViewModel
import com.example.app_barber.ui_app.ViewModel.ServiziViewModel
import com.example.app_barber.ui_app.ViewModel.TurnoViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ComposableDestinationInComposeScope", "UnrememberedMutableState")
@Composable
fun Navigation_App(navController:NavHostController,selectedIcon: MutableState<ImageVector>,calendarViewModel: CalendarViewModel) {

    lateinit var auth: FirebaseAuth
    // Initialize Firebase Auth
    auth = Firebase.auth

    val currentUser = auth.currentUser

    NavHost(
        navController = navController,
        startDestination = if (currentUser == null) Screen.Login.root else Screen.Home.root
    )
    {
        composable(Screen.Register.root) { Register(navController, AuthViewModel(repository = FirestoreRepository()))}
        composable(Screen.Register_user.root) { RegisterationUser(navController, UserViewModel(firestoreRepository = FirestoreRepository()))}

        composable(Screen.Login.root) { Login(navController,viewModel = AuthViewModel(repository = FirestoreRepository()))}

        composable(Screen.Home.root) { Home(navController, selectedIcon,
            { navController.navigate("account") }) }

        composable(Screen.Agenda.root) {
            Agenda(navController, selectedIcon,PrenotazioniViewModel(firestoreRepository =FirestoreRepository() ),calendarViewModel = calendarViewModel)
        }
        composable(Screen.Account.root) { 
      
         Account(selected = selectedIcon, navController = navController, userViewModel = UserViewModel(firestoreRepository = FirestoreRepository()))
        }
        composable(Screen.Turni.root) {
            Turni(navController = navController, turnoViewModel = TurnoViewModel(firestoreRepository = FirestoreRepository()))
        }
        composable(Screen.Servizi.root) {
            Servizi(navController = navController, serviziViewModel = ServiziViewModel(firestoreRepository = FirestoreRepository()))
        }

        composable(Screen.Clienti.root) { Clienti(navController, selectedIcon, clientViewModel = ClienteViewModel(firestoreRepository = FirestoreRepository())) }

        composable(Screen.Magazzino.root) { Magazzino() }

        }
    }

