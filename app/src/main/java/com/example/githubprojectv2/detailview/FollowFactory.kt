package com.example.githubprojectv2.detailview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FollowFactory(
    var data : String
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(data) as T

    }
}