package com.example.app_barber.ui_app.Component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_barbaber.ui.theme.GrayCasella


@Composable
fun IconClienti(
    name: String,
    surname: String,
    size: Dp = 48.dp,
    fontSize: TextUnit = 20.sp
) {

    // Colore basato sul nome
    val color =GrayCasella

    // Icona circolare con l'iniziale
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${name.firstOrNull()?.uppercase() ?: ""}${surname.firstOrNull()?.uppercase() ?: ""}",
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
