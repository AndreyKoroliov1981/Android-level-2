package com.korol.diapplication.di


import com.korol.diapplication.classes.Bicycle
import com.korol.diapplication.classes.Frame
import com.korol.diapplication.classes.Wheel
import dagger.Module
import dagger.Provides
import kotlin.random.Random


@Module
class AppModule {

    @Provides
    fun provideGetBicycle(): Bicycle {
        val color = Random.nextInt(1, 10) // берем случайный цвет для рамы
        var bC=Bicycle("", Frame(1), Wheel("1"),Wheel("2")) // создаем экземпляр велосипеда

        var mybC=bC.build("",color) // строим новый велосипед
        return mybC
    }
}