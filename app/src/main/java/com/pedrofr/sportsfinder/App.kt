package com.pedrofr.sportsfinder

import android.app.Application
import com.pedrofr.sportsfinder.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    //TODO remove
    companion object {
        private lateinit var instance: App
        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Koin initialization
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModules)
        }
    }

}