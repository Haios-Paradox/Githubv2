package com.example.githubprojectv2.searchview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubprojectv2.api.ApiConfig
import com.example.githubprojectv2.api.ItemsItem
import com.example.githubprojectv2.api.ResponseGit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel(var data: String) : ViewModel() {

    private val _accounts = MutableLiveData<List<ItemsItem>>()
    fun accounts() : LiveData<List<ItemsItem>>{
        return _accounts
    }

    private var _loading = MutableLiveData<Boolean>()
    fun loading() : LiveData<Boolean>{
        return _loading
    }

    companion object {
        private const val TAG = "ListFragment"
    }

    fun search(query: String) {
        findAccount(query)
    }

    private fun findAccount(id : String){

        showLoading(true)

        val client = ApiConfig.getApiService().getSearch(id,50)
        client.enqueue(object : Callback<ResponseGit> {
            override fun onResponse(
                call: Call<ResponseGit>,
                response: Response<ResponseGit>
            ){
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _accounts.value = (responseBody.items)
                    }
                } else {
                    Log.e(TAG, "Fail to connect, bruh moment")
                }
            }
            override fun onFailure(call: Call<ResponseGit>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }
}