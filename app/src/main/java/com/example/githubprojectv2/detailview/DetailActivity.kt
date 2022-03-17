package com.example.githubprojectv2.detailview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.githubprojectv2.api.ApiConfig
import com.example.githubprojectv2.api.Accounts
import com.example.githubprojectv2.R
import com.example.githubprojectv2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)

        getAccountData(intent.getStringExtra(USER_ID)as String)




        setContentView(binding.root)
    }

    private fun getAccountData(id: String){
        val client = ApiConfig.getApiService().getUser(id)
        client.enqueue(object : Callback<Accounts> {
            override fun onResponse(
                call: Call<Accounts>,
                response: Response<Accounts>
            ) {
                //showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setAccount(responseBody)
                    }
                } else {
                    Log.e(TAG, "Fail to connect, bruh moment")
                }
            }

            override fun onFailure(call: Call<Accounts>, t: Throwable) {
                Log.e(TAG, "Fail to connect, bruh moment")
            }


        })
    }

    private fun setAccount(akun: Accounts) {

        Glide.with(this)
            .load(akun.avatarUrl)
            .circleCrop()
            .into(binding.avatar)
        binding.accountName.text = akun.name
        binding.accountUname.text = akun.login

        val detailPagerAdapter = DetailPagerAdapter(this,akun.login)
        binding.viewPager.adapter = detailPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


    }

    companion object{
        const val USER_ID = "User_ID"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val TAG = "Yabe"
    }
}


