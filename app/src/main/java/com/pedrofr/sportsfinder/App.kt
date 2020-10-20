package com.pedrofr.animaldiscovery

import android.app.Application
import com.pedrofr.animaldiscovery.data.model.Sport

class App : Application() {

    //TODO remove
    companion object {
        val sports = listOf(
            Sport(title = "NCAAF", imageUrl = "https://pokeres.bastionbot.org/images/pokemon/5.png"),
            Sport(title = "NFL", imageUrl = "https://pokeres.bastionbot.org/images/pokemon/4.png"),
            Sport(title = "AFL"),
            Sport(title = "MLB"),
            Sport(title = "Basketball Euroleague"),
            Sport(title = "NBA")
        )
    }



}