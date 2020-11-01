package com.pedrofr.sportsfinder.di

import com.pedrofr.sportsfinder.data.repository.SportRepository
import com.pedrofr.sportsfinder.data.repository.SportRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { SportRepositoryImpl(get(), get(), get()) as SportRepository }
}