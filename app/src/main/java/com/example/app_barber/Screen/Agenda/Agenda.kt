package com.example.app_barbaber.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barber.Funzioni.Calendario.Calendario
import com.example.app_barber.Navigazione.Buttom_Bar
import com.example.app_barber.Navigazione.Navigation_App
import com.example.app_barber.Navigazione.TopBar
import com.example.app_barber.ui.theme.App_BarberTheme
import java.util.Calendar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Agenda() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val gridHeight = screenHeight * 0.8f
    val HTopBar= screenHeight*0.08f
    val scritte= screenHeight*0.05f
    val calendar = Calendar.getInstance()
    var Calendario = Calendario(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1)

    val listMatrix = mutableListOf<Int>()

    for (row in Calendario) {
        for (value in row) {
            listMatrix.add(value)
        }

    }


    Surface(
        modifier=Modifier.fillMaxSize(1f),
        color = Black_Back
    ) {
        Scaffold(
            topBar ={
                TopBar(HTopBar)
            },
            containerColor = Black_Back

        ) {
            innerPadding ->
            Column (verticalArrangement = Arrangement.Bottom, modifier = Modifier.padding(innerPadding) ){

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier=Modifier.height(scritte),
                    verticalArrangement = Arrangement.Bottom
                )
                {
                    item {
                        Text(text = "Lunedi", color = Color.White)
                    }
                    item {
                        Text(text = "Martedi", color = Color.White)
                    }
                    item {
                        Text(text = "Mercoledi", color = Color.White)
                    }
                    item {
                        Text(text = "Giovedi", color = Color.White)
                    }
                    item {
                        Text(text = "Venerdi", color = Color.White)
                    }
                    item {
                        Text(text = "Sabato", color = Color.White)
                    }
                    item {
                        Text(text = "Domenica", color = Color.White)
                    }

                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(7), modifier =
                    Modifier
                        .height(gridHeight)
                    ,
                    verticalArrangement = Arrangement.Bottom

                )
                {


                    items(42){
                            values->
                        casella(item = listMatrix[values])
                    }
                }
            }
        }


            }


        }





    @Composable
    fun casella(item: Int) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val cHeight = screenHeight * 0.133333333333333333f
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    BorderStroke(2.dp, Color.Gray)
                ),
            color = Black_White
        ) {
            Box(modifier = Modifier.height(cHeight))
            {
                Column {
                    Text(text = item.toString(), color = Color.White)





                }

            }
        }


    }




@Preview(showBackground = true)
@Composable
fun calendario() {
    App_BarberTheme {
        Agenda()
    }
}
       


