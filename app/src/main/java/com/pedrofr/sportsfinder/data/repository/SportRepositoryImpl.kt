package com.pedrofr.sportsfinder.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateFormat
import com.pedrofr.sportsfinder.data.database.dao.SportsDao
import com.pedrofr.sportsfinder.data.model.Odd
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.networking.NetworkStatusChecker
import com.pedrofr.sportsfinder.networking.SportsApi
import com.pedrofr.sportsfinder.networking.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.*

class SportRepositoryImpl(
    private val sportsDao: SportsDao,
    private val sportsApi: SportsApi,
    private val context: Context
) : SportRepository {

    //TODO replace this call with KOIN  injection
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(context.getSystemService(ConnectivityManager::class.java))
    }

    /*
    It will first check if there's an Internet Connection. If there is we retrieve the results from the API directly and return the data
    Otherwise we'll return from the DB (can be empty if accessed for the first time)
     */
    override suspend fun getSports(): List<Sport> {
        networkStatusChecker.performIfConnectedToInternet {
            //Retrieves API results if there's a Internet Connection
            val results = sportsApi.loadSports()
            if (results is Success) {
                results.data.let { sportsResponse ->
                    if (getSportsDataFromCache().isEmpty()) {
                        val sports = sportsResponse
                            .filter { it.group.contains("soccer", true) }
                            .map {
                                //TODO replace with more attributes with time
                                Sport(
                                    sports_key = it.key,
                                    title = it.title
                                )
                            }
                        withContext(Dispatchers.IO) { sportsDao.addSports(sports) }
                    }

                }
            }
        }
        return if (getSportsDataFromCache().isNotEmpty()) {
            getSportsDataFromCache()
        } else {
            listOf()
        }
    }

    private suspend fun getSportsDataFromCache(): List<Sport> {
        return withContext(Dispatchers.IO) {
            sportsDao.getSports()
        }
    }

    private suspend fun getOddsDataFromCache(sportsKey: String): List<Odd> {
        return withContext(Dispatchers.IO) {
            sportsDao.getOdds(sportsKey)
        }
    }

    override suspend fun getOdds(sportKey: String): Flow<List<Odd>> {

        return flow {
            val results = sportsApi.getOdds(sportKey)
            if (results is Success) {
                results.data.let { oddsResponse ->

                    if (getOddsDataFromCache(sportKey).isEmpty()) {
                        val pinnacleSite = oddsResponse
                            .flatMap { it.sites }
                            .first()
                        val odds = oddsResponse
                            .map { odds ->
                                Odd(
                                    sportsKey = odds.sportsKey,
                                    startTime = DateFormat.format("h:mm a", odds.startTime)
                                        .toString(),
                                    homeTeam = odds.teams[0], //TODO see what team is the homeTeam this is not necessarily true
                                    awayTeam = odds.teams[1],
                                    homeTeamOdd = pinnacleSite.odds.h2h[0],
                                    drawOdd = pinnacleSite.odds.h2h[1],
                                    awayTeamOdd = pinnacleSite.odds.h2h[2]
                                )
                            }
                        emit(odds)
                    }
                }
            }

        }.flowOn(Dispatchers.IO)
    }


}

