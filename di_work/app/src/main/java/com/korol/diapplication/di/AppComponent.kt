package com.korol.diapplication.di

import com.korol.diapplication.MainActivity
import com.korol.diapplication.classes.Bicycle
import com.korol.diapplication.classes.Frame
import com.korol.diapplication.classes.WheelDealer
import dagger.Component

@Component (modules=[AppModule::class,WheelDealer::class])

interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}