package com.example.githubprojectv2.searchview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprojectv2.api.ApiConfig
import com.example.githubprojectv2.detailview.DetailActivity
import com.example.githubprojectv2.api.ItemsItem
import com.example.githubprojectv2.api.ResponseGit
import com.example.githubprojectv2.databinding.FragmentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {
    private lateinit var rvAccount: RecyclerView
    private lateinit var rvAdapter : AdapterAccount

    private val accountList = ArrayList<ItemsItem>()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        findAccount()

        return binding.root
    }

    private fun findAccount() {
        showLoading(true)
        val client = ApiConfig.getApiService().getSearch(USER_ID,50)
        client.enqueue(object : Callback<ResponseGit> {
            override fun onResponse(
                call: Call<ResponseGit>,
                response: Response<ResponseGit>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setAccountData(responseBody.items)
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

    private fun setAccountData(account: List<ItemsItem>) {
            accountList.addAll(account)
        rvAccount = binding.recyclerAccount
        rvAccount.layoutManager = LinearLayoutManager(this.context)
        rvAdapter = AdapterAccount(accountList)
        rvAccount.adapter = rvAdapter

        rvAdapter.setOnItemClickCallback(object : AdapterAccount.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                showSelectedAccount(data)
            }
        })
    }

    private fun showSelectedAccount(account : String){
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.USER_ID,account)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {

        }

    companion object {
        private const val TAG = "ListFragment"
        private const val USER_ID = "sidiqpermana"
    }


}