package com.pedrofr.animaldiscovery.data.repository

import com.pedrofr.animaldiscovery.data.model.Sport

interface SportRepository {
    suspend fun getSports() : List<Sport>

}