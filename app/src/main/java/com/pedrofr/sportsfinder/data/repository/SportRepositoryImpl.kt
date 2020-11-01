package com.pedrofr.sportsfinder.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.pedrofr.sportsfinder.data.database.dao.SportsDao
import com.pedrofr.sportsfinder.data.model.*
import com.pedrofr.sportsfinder.networking.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

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

    private suspend fun getEventsFromDataCache(sportsKey: String): List<Event> {
        return withContext(Dispatchers.IO) {
            sportsDao.getEvents(sportsKey)
        }
    }

    override suspend fun getEvents(sportKey: String): Flow<Result<List<*>>> {

        return flow {
            emit(Loading)
            networkStatusChecker.performIfConnectedToInternet {
                val results = sportsApi.getOdds(sportKey)
                if (results is Success) {
                    results.data.let { oddsResponse ->
                        //If we have an event date not on DB, add it
                        //TODO only add the new event, no need to delete everything and add
                        val lastSavedEventDate = sportsDao.getLastEvent(sportKey)?.startTime
                        val lastReturnedEventDate = oddsResponse.last().startTime
                        if (lastSavedEventDate != lastReturnedEventDate) {
                            val events = oddsResponse
                                .map { odds ->
                                    val pinnacleSite = odds.sites
                                        .first()
                                    val homeTeamPosition = odds.teams.indexOf(odds.homeTeam)
                                    val awayTeamPosition = if (homeTeamPosition == 0) {
                                        1
                                    } else {
                                        0
                                    }
                                    Event(
                                        sportsKey = odds.sportsKey,
                                        startTime = odds.startTime,
                                        homeTeam = odds.homeTeam, //TODO see what team is the homeTeam this is not necessarily true
                                        awayTeam = odds.teams.first { team -> team != odds.homeTeam },
                                        homeTeamOdd = pinnacleSite.odds.h2h[homeTeamPosition],
                                        awayTeamOdd = pinnacleSite.odds.h2h[awayTeamPosition],
                                        drawOdd = pinnacleSite.odds.h2h[2]
                                    )
                                }
                            sportsDao.insertEvents(events)
                        }
                    }
                }
            }
            emit(Success(getEventsFromDataCache(sportKey)))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserDetail(userId: String): User? = sportsDao.getUserDetailById(userId)

    override suspend fun getNumberOfUserBets(userId: String): Int =
        sportsDao.getNumberOfUserBets(userId)

    override suspend fun createUser(user: User) = sportsDao.createNewUser(user)

    override fun getUserDetailByUsername(username: String): User? =
        sportsDao.getUserDetailByUsername(username)

    override suspend fun fetchSportsByQuery(query: String): Flow<Result<List<*>>> {
        return flow {
            emit(Loading)
            if (query.length < 2) {
                emit(Success(getSports()))
            } else {
                val fetchResultsByQuery = sportsDao.fetchSportsByTitle(query)
                if (fetchResultsByQuery.isEmpty()) {
                    emit(NoResults)
                } else {
                    emit(Success(fetchResultsByQuery))
                }
            }
        }.flowOn(Dispatchers.IO)

    }

    private suspend fun getSports(): List<Sport> {

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
                        sportsDao.insertSports(sports)
                    }
                }
            }
        }
        return (sportsDao.getSports())

    }

    private suspend fun getSportsDataFromCache(): List<Sport> {
        return withContext(Dispatchers.IO) {
            sportsDao.getSports()
        }
    }

    override fun fetchPendingBets
                (userId: String): Flow<List<BetWithEvents>> = sportsDao.getBetsWithEvents(userId)

    override suspend fun createPendingBet(bet: Bet) = sportsDao.insertBet(bet)

    override suspend fun createBetWithEvent(betWithEvents: BetWithEventCrossRef) = sportsDao.insertBetWithEvents(betWithEvents)
}

