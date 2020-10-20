package com.pedrofr.animaldiscovery.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pedrofr.animaldiscovery.data.model.Sport
import com.pedrofr.animaldiscovery.utils.DATABASE_NAME

const val DATABASE_VERSION = 1

@Database(version = DATABASE_VERSION, entities = [Sport::class])

abstract class AnimalDatabase() : RoomDatabase() {

    companion object {

        private var INSTANCE: AnimalDatabase? = null

        fun buildDatabase(context: Context): AnimalDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AnimalDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}