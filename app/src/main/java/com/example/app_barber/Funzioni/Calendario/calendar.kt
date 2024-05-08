package com.example.app_barber.Funzioni.Calendario


import java.util.Calendar

fun Calendario(year: Int,moth:Int,day: Int): Array<IntArray> {
    //righe e colonne del calendario
    val rows=6
    val column=7

    //Creo il calendario in base al mese in cui mi trovo
    val calendario=Calendar.getInstance()
        .apply {
        set(year,moth,day)
    }
    //Creo il calendario in base al mese precedente
    val calendario_P=Calendar.getInstance()
        .apply {
            set(year,moth-1,day)
        }

    //vedo che giorno della settimana è il 1 del mese
    val giorno_della_settimana=calendario.get(Calendar.DAY_OF_WEEK)

    //vedo quanti giorni ho nel mese che ho inserito
    val giorni_mese=calendario.getActualMaximum(Calendar.DAY_OF_MONTH)

    //Calendaario del mese in base ai giorni della settimana e dal mese
    val month = Array(rows) { IntArray(column) }

    var row=0
    var dayCounter = 1
    var col=giorno_della_settimana-2

    //vedo quanti giorni nella prima riga sono vuoti
    val giorni_vuoti_inizio=column-(column-col)
    //vedo quanti giorni nelle ultime righe sono vuote
    val giorni_vuoti_fine=(rows*column)-(giorni_mese+giorni_vuoti_inizio)

    //giorni mese precedente da mettere nel calendario
    val G_M_P=IntArray(giorni_vuoti_inizio)
    val giorni_mese_precedente=calendario_P.getActualMaximum(Calendar.DAY_OF_MONTH)

    for(i in 0 until giorni_vuoti_inizio )
    {
        if(i==0)
        {
            G_M_P[i]=giorni_mese_precedente
        }
        else
            G_M_P[i]=giorni_mese_precedente-i
    }

    // Inverto gli elementi del G_M_P
    for (i in 0 until G_M_P.size / 2) {
        val temp = G_M_P[i]
        G_M_P[i] = G_M_P[G_M_P.size - 1 - i]
        G_M_P[G_M_P.size - 1 - i] = temp
    }

    //giorni mese successivo
    val G_M_S=IntArray(giorni_vuoti_fine)

    for(i in 0 until giorni_vuoti_fine )
    {
            G_M_S[i]=i+1

    }


    while (dayCounter <= giorni_mese) {
        month[row][col] = dayCounter
        dayCounter++
        col++
        if (col == 7) {
            col = 0
            row++
        }
    }

    var c=0

    for (i in 0 until 7) {
            if(month[0][i]==0){
                month[0][i]=G_M_P[c]
                c++
            }
    }
    c=0
    for (i in 0 until rows) {
        for (j in 0 until 7) {
            if (month[i][j] == 0) {
                month[i][j] = G_M_S[c]
                c++
            }
        }
    }
    return month

    /*
println("mese attuale=$mese_C mese precedente =$mese_P 1 giorno della settimana =$giorno_della_settimana")
    println("Calendario mese completo riempimento")
        for (i in 0 until rows) {
            for (j in 0 until 7) {
                print("${month[i][j]}\t")
            }
            println()
        }

*/
}



