package com.example.githubprojectv2.detailview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubprojectv2.api.Accounts
import com.example.githubprojectv2.api.ApiConfig
import com.example.githubprojectv2.api.ResponseFollowItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(var data: String) : ViewModel() {
    private val _accountsFollower = MutableLiveData<List<ResponseFollowItem>>()
    fun accountsFollower() : LiveData<List<ResponseFollowItem>> {
        return _accountsFollower
    }

    private val _accountsFollows = MutableLiveData<List<ResponseFollowItem>>()
    fun accountsFollows() : LiveData<List<ResponseFollowItem>> {
        return _accountsFollows
    }

    private val _accountDetail = MutableLiveData<Accounts>()
    fun accountDetail() : LiveData<Accounts>{
        return _accountDetail
    }

    private var _loading = MutableLiveData<Boolean>()
    fun loading() : LiveData<Boolean>{
        return _loading
    }

    private var _accountLoading = MutableLiveData<Boolean>()
    fun accountLoading() : LiveData<Boolean>{
        return _accountLoading
    }

    init{
        findFollowing()
        findFollower()
        findAccountData()
    }


    private fun findFollower() {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollower(data)
        client.enqueue(object : Callback<ArrayList<ResponseFollowItem>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseFollowItem>>,
                response: Response<ArrayList<ResponseFollowItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                        _accountsFollower.value = responseBody!!

                } else {
                    //Log.e(FollowerFragment.TAG, "Fail to connect, bruh moment")

                }
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowItem>>, t: Throwable) {
                showLoading(false)
                //Log.e(FollowerFragment.TAG, "onFailure: ${t.message}")
            }
        })

    }
    private fun findFollowing() {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollows(data)
        client.enqueue(object : Callback<ArrayList<ResponseFollowItem>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseFollowItem>>,
                response: Response<ArrayList<ResponseFollowItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()

                        _accountsFollows.value = responseBody!!

                } else {
                    //Log.e(FollowerFragment.TAG, "Fail to connect, bruh moment")

                }
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowItem>>, t: Throwable) {
                showLoading(false)
                //Log.e(FollowerFragment.TAG, "onFailure: ${t.message}")
            }
        })

    }
    private fun findAccountData(){
        showLoadingAccount(true)
        val client = ApiConfig.getApiService().getUser(data)
        client.enqueue(object : Callback<Accounts> {
            override fun onResponse(
                call: Call<Accounts>,
                response: Response<Accounts>
            ) {
                showLoadingAccount(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()

                        _accountDetail.value = responseBody!!

                } else {
                    Log.e(DetailActivity.TAG, "Fail to connect, bruh moment")
                }
            }

            override fun onFailure(call: Call<Accounts>, t: Throwable) {
                Log.e(DetailActivity.TAG, "Fail to connect, bruh moment")
            }


        })
    }

    private fun showLoading(isLoading : Boolean) {
        _loading.value = isLoading
    }
    private fun showLoadingAccount(isLoading : Boolean){
        _accountLoading.value = isLoading
    }
}