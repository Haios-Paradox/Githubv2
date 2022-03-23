package com.example.githubprojectv2.detailview

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubprojectv2.R
import com.example.githubprojectv2.api.Accounts
import com.example.githubprojectv2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)

        val userId = intent.getStringExtra(USER_ID)as String
        val mainViewModel = ViewModelProvider(this@DetailActivity, FollowFactory(userId))
            .get(DetailViewModel::class.java)
        mainViewModel.accountDetail().observe(this) {
            setAccount(it)
        }
        mainViewModel.loading().observe(this){
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        mainViewModel.accountLoading().observe(this){
            if (it) {
                binding.progressBarUser.visibility = View.VISIBLE
            } else {
                binding.progressBarUser.visibility = View.GONE
            }
        }
        setContentView(binding.root)
    }

    private fun setAccount(akun: Accounts) {

        Glide.with(this)
            .load(akun.avatarUrl)
            .circleCrop()
            .into(binding.accountAvatar)
        binding.accountName.text = akun.name
        binding.accountUname.text = akun.login
        binding.accountBio.text = akun.bio
        binding.accountCompany.text = akun.company
        binding.accountEmail.text = akun.email
        binding.accountLocation.text = akun.location
        binding.accountFollower.text = akun.followers.toString()
        binding.accountFollowing.text = akun.following.toString()
        binding.accountRepo.text = akun.publicRepos.toString()

        val detailPagerAdapter = DetailPagerAdapter(this)
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


