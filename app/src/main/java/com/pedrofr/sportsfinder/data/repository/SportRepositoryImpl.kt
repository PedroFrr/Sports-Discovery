package com.pedrofr.animaldiscovery.data.repository

import com.pedrofr.animaldiscovery.data.database.dao.SportsDao
import com.pedrofr.animaldiscovery.data.model.Sport

class SportRepositoryImpl(private val sportsDao: SportsDao): SportRepository {

    override suspend fun getSports(): List<Sport> = listOf()

}