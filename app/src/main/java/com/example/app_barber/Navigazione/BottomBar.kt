package com.example.app_barber.Navigazione

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_barbaber.Screen.Screen
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_Navigation
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Blue

@Composable
fun Buttom_Bar(
    selected: MutableState<ImageVector>,
    navController: NavHostController
) {
    BottomAppBar(
        containerColor = Black_Navigation,
        modifier = Modifier
            .background(Black_Navigation)
            .fillMaxWidth()
            .height(56.dp) // Altezza della BottomAppBar
    ) {
        BottomBarItem(
            icon = Icons.Filled.Home,
            label = "Home",
            isSelected = selected.value == Icons.Default.Home,
            onClick = {
                selected.value = Icons.Default.Home
                navController.navigate(Screen.Home.root)
            },
            modifier = Modifier.weight(1f)
        )

        BottomBarItem(
            icon = Icons.Filled.DateRange,
            label = "Agenda",
            isSelected = selected.value == Icons.Default.DateRange,
            onClick = {
                selected.value = Icons.Default.DateRange
                navController.navigate(Screen.Agenda.root)
            },
            modifier = Modifier.weight(1f)
        )

        BottomBarItem(
            icon = Icons.Filled.Person,
            label = "Clienti",
            isSelected = selected.value == Icons.Default.Person,
            onClick = {
                selected.value = Icons.Default.Person
                navController.navigate(Screen.Clienti.root)
            },
            modifier = Modifier.weight(1f)
        )

        BottomBarItem(
            icon = Icons.Default.ShoppingCart,
            label = "Magazzino",
            isSelected = selected.value == Icons.Default.ShoppingCart,
            onClick = {
                selected.value = Icons.Default.ShoppingCart
                navController.navigate(Screen.Magazzino.root)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Layout ottimizzato per icona e testo affiancati con dimensione proporzionata
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Blue else Color.White,
                modifier = Modifier.size(24.dp) // Dimensione icona ottimizzata
            )
            Spacer(modifier = Modifier.width(4.dp)) // Spazio tra icona e testo
            Text(
                text = label,
                color = if (isSelected) Blue else Color.White,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
