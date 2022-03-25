package com.example.githubprojectv2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFav(favData : FavData)

    @Query("SELECT * FROM fav_data ORDER BY id ASC")
    fun getFav(): LiveData<List<FavData>>

    @Delete()
    suspend fun deleteFav(favData: FavData)//Not sure how to use this

    @Query("DELETE FROM fav_data WHERE id = :id")
    suspend fun deleteFav(id:String)

    @Query("SELECT EXISTS(SELECT * FROM fav_data WHERE id = :id)")
    suspend fun checkFav(id : String) : Boolean
}