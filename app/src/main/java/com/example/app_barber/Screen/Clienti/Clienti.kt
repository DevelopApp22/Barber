package com.example.app_barbaber.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_barbaber.ui.theme.Black_Back

@Composable
fun Clienti() {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black_Back
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentAlignment = Alignment.Center){
            Column {
                Text("Clienti", color = Color.White)
            }
        }
    }

}