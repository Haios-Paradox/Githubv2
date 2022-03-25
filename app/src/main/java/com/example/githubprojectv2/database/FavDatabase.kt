package com.example.githubprojectv2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavData::class], version = 2, exportSchema = false)
abstract class FavDatabase : RoomDatabase() {

    abstract fun favDao() : FavDao

    companion object{
        @Volatile
        private var INSTANCE: FavDatabase? = null

        fun getDatabase(context : Context):FavDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java,
                    "fav_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}