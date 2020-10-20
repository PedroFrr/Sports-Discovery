package com.pedrofr.sportsfinder.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pedrofr.sportsfinder.BuildConfig
import com.pedrofr.sportsfinder.networking.response.GetSportsResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsService {

    @GET("sports/")
    suspend fun getSports(
        @Query("apiKey") apiKey: String = BuildConfig.ODDS_ACCESS_KEY
    ) : GetSportsResponse

    companion object {
        private const val BASE_URL = "https://api.the-odds-api.com/v3/"

        fun create(): SportsService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val contentType = "application/json".toMediaType()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SportsService::class.java)
        }
    }
}