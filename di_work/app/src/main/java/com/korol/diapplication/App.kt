package com.korol.diapplication

import android.app.Application
import com.korol.diapplication.classes.Bicycle
import com.korol.diapplication.classes.MySimplePresenter
import com.korol.diapplication.di.AppComponent
import com.korol.diapplication.di.DaggerAppComponent
import com.korol.diapplication.koin.RepositoryBicycle
import com.korol.diapplication.koin.RepositoryBicycleImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import org.koin.core.context.startKoin


class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(module {
                // single instance of HelloRepository
                single<RepositoryBicycle> { RepositoryBicycleImpl() }
                // Simple Presenter Factory
                factory { MySimplePresenter(get()) }
            })
        }
    }
}