package com.example.githubprojectv2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_data")
data class FavData(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val favorite : Boolean,
    val avatar:String
)