package com.korol.diapplication

import android.app.Application
import com.korol.diapplication.di.DaggerAppComponent


class App: Application() {
        val appComponent= DaggerAppComponent.create()
}