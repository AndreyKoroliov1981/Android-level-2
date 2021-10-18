package com.korol.diapplication.classes


import dagger.Module
import java.text.SimpleDateFormat
import java.util.*

class Frame(val color: Int)  {
        private val sdf = SimpleDateFormat("dd/M/yyyyhh:mm:ss") //создаем уникальный id номер
        private val currentDate = sdf.format(Date())
        val idNumber = "$currentDate"
        }



