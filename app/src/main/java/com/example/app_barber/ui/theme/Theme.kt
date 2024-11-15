package com.example.app_barber.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_Background
import com.example.app_barbaber.ui.theme.Blue

private val DarkColorScheme = darkColorScheme(
    primary = Blue, // sfondo bottone
    onPrimary= Color.White , //testo bottone

    secondary = Color.White,
    onSecondary = Color.Black,

    tertiary = Color.White ,
    onTertiary = Color.Black,


    background = Black_Background,
    onBackground = Color.White,

    surface = Black_Background ,
    onSurface = Color.Black ,//testo text field

    surfaceVariant = Color.White,         // Textfield background
    onSurfaceVariant = Color.Gray,        // Textfield label color

)



@Composable
fun App_BarberTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorScheme
    val MyShapes = Shapes(
        small = RoundedCornerShape(16.dp),  // Per componenti piccoli come Button
        medium = RoundedCornerShape(20.dp), // Per componenti medi come Card
        large = RoundedCornerShape(10.dp)   // Per componenti grandi
    )


    MaterialTheme(
        typography = Typography,
        shapes = MyShapes,
        colorScheme = colors,
        content = content
    )


}
