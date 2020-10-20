package com.pedrofr.sportsfinder

import android.app.Application
import com.pedrofr.sportsfinder.data.database.AppDatabase
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.data.repository.SportRepositoryImpl
import com.pedrofr.sportsfinder.networking.SportsApi
import com.pedrofr.sportsfinder.networking.SportsService.Companion.create

class App : Application() {

    //TODO remove
    companion object {
        private lateinit var instance: App
        private val database: AppDatabase by lazy {
            AppDatabase.getInstance(instance)
        }
        private val service by lazy { create()}
        private val remoteApi by lazy {SportsApi(service)}
        val repository : SportRepository by lazy { SportRepositoryImpl(database.sportsDao(), remoteApi, instance)}

        fun getAppContext() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }



}