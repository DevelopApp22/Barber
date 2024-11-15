package com.example.app_barber.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.app_barber.R

val CustomFontFamily = FontFamily(
    Font(R.font.font, FontWeight.Normal),  // Font normale

)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    // Specifico per bottoni
    labelSmall = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),


)