package com.korol.diapplication.koin

import com.korol.diapplication.classes.Bicycle
import com.korol.diapplication.classes.Frame
import com.korol.diapplication.classes.Wheel
import kotlin.random.Random

interface RepositoryBicycle {
    fun giveBicycle(): Bicycle
}

class RepositoryBicycleImpl():RepositoryBicycle {
    override fun giveBicycle():Bicycle{
        val color = Random.nextInt(1, 10) // берем случайный цвет для рамы
        var bCKoin=Bicycle("", Frame(1), Wheel("1"), Wheel("2")) // создаем экземпляр велосипеда

        var mybC=bCKoin.build("",color) // строим новый велосипед
        return mybC
    }
}