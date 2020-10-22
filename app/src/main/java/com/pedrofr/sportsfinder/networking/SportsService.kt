package com.pedrofr.sportsfinder.networking

import com.pedrofr.sportsfinder.BuildConfig
import com.pedrofr.sportsfinder.networking.response.GetOddsResponse
import com.pedrofr.sportsfinder.networking.response.GetSportsResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SportsService {

    @GET("sports/")
    suspend fun getSports(
        @Query("apiKey") apiKey: String = BuildConfig.ODDS_ACCESS_KEY
    ) : GetSportsResponse

    @GET("odds/")
    suspend fun getOdds(
        @Query("apiKey") apiKey: String = BuildConfig.ODDS_ACCESS_KEY,
        @Query("sport") sport: String,
        @Query("region") region: String = "eu",
        @Query("mkt") market: String = "h2h"
    ) : GetOddsResponse

//    companion object {
//        private const val BASE_URL = "https://api.the-odds-api.com/v3/"
//
//        fun create(): SportsService {
//            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
//
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logger)
//                .build()
//
//            return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(MoshiConverterFactory.create().asLenient()) //asLenient in the sense that we don't have to pass all the Json fields from the endpoint
//                .build()
//                .create(SportsService::class.java)
//        }
//    }
}