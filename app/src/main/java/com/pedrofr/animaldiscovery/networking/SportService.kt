package com.pedrofr.animaldiscovery.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pedrofr.animaldiscovery.networking.response.GetSportsResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET

interface SportService {

    @GET("species")
    suspend fun getAnimals() : GetSportsResponse

    companion object {
        private const val BASE_URL = "https://www.fishwatch.gov/api/"

        fun create(): SportService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val contentType = "application/json".toMediaType()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .build()
                .create(SportService::class.java)
        }
    }
}