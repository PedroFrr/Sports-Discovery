package com.pedrofr.sportsfinder.di

import com.pedrofr.sportsfinder.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SportsListViewModel(get()) }

    viewModel { EventsListViewModel(get(), get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { UserViewModel(get(), get()) }

    viewModel { MainActivityViewModel(get()) }
}