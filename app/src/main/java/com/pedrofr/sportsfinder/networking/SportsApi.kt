package com.pedrofr.sportsfinder.networking

import com.pedrofr.sportsfinder.networking.response.OddsResponse
import com.pedrofr.sportsfinder.networking.response.SportsResponse
import java.util.stream.Collectors


class SportsApi(private val apiService: SportsService ) {

    suspend fun loadSports(): Result<List<SportsResponse>> =
        try {
            val response = apiService.getSports()
            val results = response.results
            Success(results)

        } catch (error: Throwable) {
            Failure(error)
        }

    suspend fun getOdds(sportKey: String): Result<List<OddsResponse>> =
        try {
            val response = apiService.getOdds(sport = sportKey)
            //TODO only display Pinnacle results

            val results = response.results



            Success(results)

        } catch (error: Throwable) {
            Failure(error)
        }

}