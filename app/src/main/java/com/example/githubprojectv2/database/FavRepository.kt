package com.example.githubprojectv2.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavRepository(private val favDao : FavDao) {

    val readAllData: LiveData<List<FavData>> = favDao.getFav()
    private var _checkExists = MutableLiveData<Boolean>()

    fun checkExists() : LiveData<Boolean>{
        return _checkExists
    }


    suspend fun addFav(favData : FavData){
        favDao.addFav(favData)
    }

    suspend fun deleteFav(data: String) {
        favDao.deleteFav(data)
    }



    suspend fun checkFavData(data:String){
        _checkExists.value =  favDao.checkFav(data)
    }
}