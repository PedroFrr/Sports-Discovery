package com.pedrofr.sportsfinder.di

import com.pedrofr.sportsfinder.ui.user.UserViewModel
import com.pedrofr.sportsfinder.viewmodels.LoginViewModel
import com.pedrofr.sportsfinder.viewmodels.OddsListViewModel
import com.pedrofr.sportsfinder.viewmodels.SportsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SportsListViewModel(get()) }

    viewModel { OddsListViewModel(get()) }

    viewModel { LoginViewModel(get()) }

    viewModel { UserViewModel(get(), get()) }
}