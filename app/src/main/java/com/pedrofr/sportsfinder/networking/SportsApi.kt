package com.pedrofr.sportsfinder.networking

import com.pedrofr.sportsfinder.networking.response.EventResponse
import com.pedrofr.sportsfinder.networking.response.SportsResponse

class SportsApi(private val apiService: SportsService) {

    suspend fun loadSports(): Result<List<SportsResponse>> =
            try {
                val response = apiService.getSports()
                val results = response.results
                Success(results)
            } catch (error: Throwable) {
                Failure(error)
            }

    suspend fun getOdds(sportKey: String): Result<List<EventResponse>> =
        try {
            val response = apiService.getOdds(sport = sportKey)
            val results = response.results
            Success(results)

        } catch (error: Throwable) {
            Failure(error)
        }

}