package com.example.githubprojectv2.database

import androidx.lifecycle.LiveData

class FavRepository(private val favDao : FavDao) {

    val readAllData: LiveData<List<FavData>> = favDao.getFav()
    suspend fun addFav(favData : FavData){
        favDao.addFav(favData)
    }

    suspend fun deleteFav(data: String) {
        favDao.deleteFav(data)
    }
}