package com.pedrofr.sportsfinder.di

import com.pedrofr.sportsfinder.data.database.AppDatabase
import com.pedrofr.sportsfinder.utils.prefs.SharedPrefManager
import org.koin.dsl.module

val dbModule = module {
    single { AppDatabase.getInstance(get()) }

    single { get<AppDatabase>().sportsDao()}

    single {SharedPrefManager(get())}

}