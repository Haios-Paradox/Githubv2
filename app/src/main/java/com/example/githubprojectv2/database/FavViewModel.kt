package com.example.githubprojectv2.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : AndroidViewModel(application) {
    val readFavData : LiveData<List<FavData>>
    private val repository : FavRepository

    init {
        val favDao = FavDatabase.getDatabase(application).favDao()
        repository = FavRepository(favDao)
        readFavData = repository.readAllData
    }

    fun addFav(favData : FavData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFav(favData)
        }
    }

    fun deleteFavData(data:String) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFav(data)
        }
    }

}