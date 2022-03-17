package com.example.githubprojectv2.detailview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprojectv2.api.*
import com.example.githubprojectv2.searchview.AdapterAccount
import com.example.githubprojectv2.databinding.FragmentFollowerBinding
import com.example.githubprojectv2.detailview.DetailActivity.Companion.USER_ID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragment(data: String) : Fragment() {
    val userId = data
    private lateinit var rvAccount: RecyclerView
    private lateinit var rvAdapter : AdapterFollow

    private val accountList = ArrayList<ResponseFollowItem>()
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        findAccount()

        return binding.root
    }

    private fun findAccount() {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollower(userId)
        client.enqueue(object : Callback<ArrayList<ResponseFollowItem>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseFollowItem>>,
                response: Response<ArrayList<ResponseFollowItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setAccountData(responseBody)
                    }
                } else {
                    Log.e(TAG, "Fail to connect, bruh moment")

                }
            }
            override fun onFailure(call: Call<ArrayList<ResponseFollowItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setAccountData(account: List<ResponseFollowItem>) {
        accountList.addAll(account)
        rvAccount = binding.rvFollower
        rvAccount.layoutManager = LinearLayoutManager(this.context)
        rvAdapter = AdapterFollow(accountList)
        rvAccount.adapter = rvAdapter
        rvAdapter.setOnItemClickCallback(object : AdapterFollow.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                showSelectedAccount(data)
            }
        })
    }
    private fun showSelectedAccount(account : String){
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(USER_ID,account)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
    }

    companion object {
        private const val TAG = "FollowerFragment"

    }
}