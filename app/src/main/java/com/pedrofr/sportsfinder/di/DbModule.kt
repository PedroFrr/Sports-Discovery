package com.pedrofr.sportsfinder.di

import com.pedrofr.sportsfinder.data.database.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { AppDatabase.getInstance(get()) }

    single { get<AppDatabase>().sportsDao()}

}