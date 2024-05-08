package com.example.app_barbaber.Screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_barbaber.ui.theme.Black_Back
import com.example.app_barbaber.ui.theme.Black_White
import com.example.app_barbaber.ui.theme.Blue
import com.example.app_barber.ui.theme.App_BarberTheme


@Composable
fun Home() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Black_Back
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp,20.dp),
            contentAlignment = Alignment.Center){
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(vertical = 20.dp))
                {
                    item{
                        Card_Agenda(name = "Agenda")

                    }
                    item{
                        Card_Agenda(name = "Magazzino")

                    }
                    item{
                        Card_Agenda(name = "Promemoria")

                    }
                    
                }


            }
        }
    }

@Composable
fun Card_Agenda(name:String)
{
Card (modifier = Modifier
    .heightIn(250.dp, 300.dp)
    .padding(6.dp), elevation = CardDefaults.cardElevation(10.dp), colors = CardDefaults.cardColors(
    Blue)


){
    Box(modifier = Modifier
        .padding(4.dp)
        ){

        Column(Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
            Text(text = name, color = Color.White, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(40.dp))
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(text = "Nome", color = Color.White)
                Spacer(modifier =Modifier.weight(1f))
                Text(text = "Ora", color = Color.White)
                Spacer(modifier =Modifier.weight(1f))
                Text(text = "Servizo", color = Color.White)
            }

            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(text = "Alessandro", color = Color.White)
                Spacer(modifier =Modifier.weight(1f))
                Text(text = "10:30", color = Color.White)
                Spacer(modifier =Modifier.weight(1f))
                Text(text = "Piega", color = Color.White)
            }
            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(text = "Claudio", color = Color.White)
                Spacer(modifier =Modifier.weight(1f))
                Text(text = "11:30", color = Color.White)
                Spacer(modifier =Modifier.weight(1f))
                Text(text = "Taglio", color = Color.White)
            }
        }
    }



}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_BarberTheme {
        Home()
    }
}