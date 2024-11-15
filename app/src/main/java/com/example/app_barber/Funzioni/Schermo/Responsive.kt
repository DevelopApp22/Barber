package com.example.app_barber.Funzioni.Schermo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


enum class TextType {
    HEADER,
    BODY,
    SMALL
}

enum class IconType {
    LARGE, MEDIUM, SMALL
}



@Composable
fun ResponsiveText(text: String, textType: TextType,fontWeight: FontWeight,textAlign: androidx.compose.ui.text.style.TextAlign,color: Color) {

    // Ottenere la configurazione dello schermo
    val configuration = LocalConfiguration.current

    // Determinare le dimensioni dello schermo
    val screenWidth = configuration.screenWidthDp.dp

    // Definire la dimensione del testo in base alle dimensioni dello schermo e al tipo di testo
    val fontSize = when (textType) {
        TextType.HEADER -> when {
            screenWidth < 360.dp -> 24.sp // Schermo piccolo
            screenWidth < 720.dp -> 32.sp // Schermo medio
            else -> 48.sp // Schermo grande
        }

        TextType.BODY -> when {
            screenWidth < 360.dp -> 14.sp // Schermo piccolo
            screenWidth < 720.dp -> 16.sp // Schermo medio
            else -> 18.sp // Schermo grande
        }

        TextType.SMALL -> when {
            screenWidth < 360.dp -> 10.sp // Schermo piccolo
            screenWidth < 720.dp -> 12.sp // Schermo medio
            else -> 14.sp // Schermo grande
        }
    }
    Text(text = text, fontSize = fontSize, fontWeight =fontWeight,textAlign=textAlign, color = color)
}


@Composable
fun ResponsiveIcon(iconType: IconType) {
    // Ottenere la configurazione dello schermo
    val configuration = LocalConfiguration.current

    // Determinare le dimensioni dello schermo
    val screenWidth = configuration.screenWidthDp.dp

    // Definire la dimensione dell'icona in base alle dimensioni dello schermo e al tipo di icona
    val iconSize = when (iconType) {
        IconType.LARGE -> when {
            screenWidth < 360.dp -> 48.dp // Schermo piccolo
            screenWidth < 720.dp -> 56.dp // Schermo medio
            else -> 64.dp // Schermo grande
        }

        IconType.MEDIUM -> when {
            screenWidth < 360.dp -> 24.dp // Schermo piccolo
            screenWidth < 720.dp -> 32.dp // Schermo medio
            else -> 40.dp // Schermo grande
        }

        IconType.SMALL -> when {
            screenWidth < 360.dp -> 16.dp // Schermo piccolo
            screenWidth < 720.dp -> 20.dp // Schermo medio
            else -> 24.dp // Schermo grande
        }
    }
}