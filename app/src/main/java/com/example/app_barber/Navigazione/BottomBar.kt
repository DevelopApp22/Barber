package com.example.app_barber.Navigazione

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Blue

@Composable
fun Buttom_Bar(selected:MutableState<ImageVector>,navController:NavHostController)
{

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val bottmbar = screenHeight * 0.07f



    BottomAppBar(containerColor = Black_White, modifier = Modifier.height(bottmbar)) {

        IconButton(onClick = {
            selected.value = Icons.Default.Home
            navController.navigate(Screen.Home.root)

        }, modifier = Modifier.weight(1f)) {
            Row {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Localized description",
                    tint = if (selected.value == Icons.Default.Home) Blue else Color.White
                )
                Text(text = " Home", color = if (selected.value == Icons.Default.Home) Blue else Color.White)
            }

        }
        IconButton(onClick = {
            selected.value = Icons.Default.DateRange
            navController.navigate(Screen.Agenda.root)
        }, modifier = Modifier.weight(1f)) {
            Row {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = "Localized description",
                    tint = if (selected.value == Icons.Default.DateRange) Blue else Color.White
                )
                Text(text = " Agenda", color = if (selected.value == Icons.Default.DateRange) Blue else Color.White)
            }


        }

        IconButton(onClick = {
            selected.value = Icons.Filled.Person
            navController.navigate(Screen.Clienti.root)
        }, modifier = Modifier.weight(1f)) {
            Row {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Localized description",
                    tint = if (selected.value == Icons.Default.Person) Blue else Color.White
                )
                Text(text = "Clienti ",color = if (selected.value == Icons.Default.Person) Blue else Color.White)
            }


        }

        IconButton(onClick = {
            selected.value = Icons.Default.ShoppingCart
            navController.navigate(Screen.Clienti.root)
        }, modifier = Modifier.weight(1f)) {
            Row {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Localized description",
                    tint = if (selected.value == Icons.Default.ShoppingCart) Blue else Color.White
                )
                Text(text = "Magazzino ",color = if (selected.value == Icons.Default.ShoppingCart) Blue else Color.White)
            }


        }


    }
}