package com.example.app_barber.Funzioni.Calendario

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun Calendario(year: Int, month: Int): Array<Array<LocalDate?>> {
    // Righe e colonne del calendario
    val rows = 6
    val columns = 7

    // Otteniamo il primo giorno del mese
    val firstDayOfMonth = LocalDate.of(year, month, 1)

    // Otteniamo l'ultimo giorno del mese
    val lastDayOfMonth = YearMonth.of(year, month).atEndOfMonth()

    // Giorno della settimana del primo giorno del mese (Lunedì = 1, Domenica = 7)
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.value + 6) % 7

    // Otteniamo il numero di giorni nel mese
    val daysInMonth = lastDayOfMonth.dayOfMonth

    // Creiamo un array per rappresentare il calendario del mese
    val monthArray = Array(rows) { arrayOfNulls<LocalDate>(columns) }

    var currentRow = 0
    var currentCol = startDayOfWeek

    // Riempio i giorni del mese corrente
    for (day in 1..daysInMonth) {
        monthArray[currentRow][currentCol] = LocalDate.of(year, month, day)
        currentCol++
        if (currentCol == 7) {
            currentCol = 0
            currentRow++
        }
    }

    // Riempio i giorni vuoti del mese precedente
    var previousMonthDay = firstDayOfMonth.minusDays(startDayOfWeek.toLong())
    for (col in 0 until startDayOfWeek) {
        monthArray[0][col] = previousMonthDay
        previousMonthDay = previousMonthDay.plusDays(1)
    }

    // Riempio i giorni vuoti del mese successivo
    var nextMonthDay = lastDayOfMonth.plusDays(1)
    for (row in currentRow until rows) {
        for (col in currentCol until columns) {
            if (monthArray[row][col] == null) {
                monthArray[row][col] = nextMonthDay
                nextMonthDay = nextMonthDay.plusDays(1)
            }
        }
        currentCol = 0
    }

    return monthArray
}



