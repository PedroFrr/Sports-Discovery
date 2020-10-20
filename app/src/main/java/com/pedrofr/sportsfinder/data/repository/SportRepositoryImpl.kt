package com.pedrofr.sportsfinder.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.pedrofr.sportsfinder.data.database.dao.SportsDao
import com.pedrofr.sportsfinder.data.model.Sport
import com.pedrofr.sportsfinder.networking.NetworkStatusChecker
import com.pedrofr.sportsfinder.networking.SportsApi
import com.pedrofr.sportsfinder.networking.Success
import kotlinx.coroutines.Dispatchers
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
    override suspend fun getSports(): List<Sport> {
        networkStatusChecker.performIfConnectedToInternet {
            //Retrieves API results if there's a Internet Connection
            val results = sportsApi.loadSports()
            if (results is Success) {
                results.data.let { sportsResponse ->
                    val sports = sportsResponse.map {
                        //TODO replace with more attributes with time
                        Sport(title = it.title)
                    }
                    withContext(Dispatchers.IO) { sportsDao.addSports(sports) }
                }
            }
        }
        val data = getSportsDataFromCache()
        return if (data.isNotEmpty()) {
            data
        } else {
            listOf()
        }
    }

    private suspend fun getSportsDataFromCache(): List<Sport> {
        return withContext(Dispatchers.IO) {
            sportsDao.getSports()
        }
    }

}

