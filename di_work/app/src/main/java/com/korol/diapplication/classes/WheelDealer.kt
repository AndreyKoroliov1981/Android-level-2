package com.korol.diapplication.classes

import dagger.Module
import dagger.Provides
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
object WheelDealer  {
    var nameDialer:String="Noname"

    fun init(data:String) {
        nameDialer=data
    }

@Provides
     fun getWheel():Wheel {
        val sdf = SimpleDateFormat("dd/M/yyyyhh:mm:ss:SSS") //создаем уникальный id номер
        val currentDate = sdf.format(Date())
       try {
           Thread.sleep(1) //Приостанавливает поток на 1 миллисекунду, для того, чтобы не создавались одинаковые iD
       } catch (e: Exception) {
       }
        return Wheel("$nameDialer$currentDate")
    }
}