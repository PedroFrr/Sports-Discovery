package com.pedrofr.sportsfinder.di

import com.pedrofr.sportsfinder.networking.SportsApi
import com.pedrofr.sportsfinder.networking.SportsService
import com.pedrofr.sportsfinder.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single { create() }

    single { SportsApi(get())}
}

private fun create(): SportsService {
    val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create().asLenient()) //asLenient in the sense that we don't have to pass all the Json fields from the endpoint
        .build()
        .create(SportsService::class.java)
}