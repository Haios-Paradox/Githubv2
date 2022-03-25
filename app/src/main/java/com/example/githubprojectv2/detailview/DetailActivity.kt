package com.example.githubprojectv2.detailview

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubprojectv2.R
import com.example.githubprojectv2.api.Accounts
import com.example.githubprojectv2.database.FavData
import com.example.githubprojectv2.database.FavViewModel
import com.example.githubprojectv2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)

        val userId = intent.getStringExtra(USER_ID)as String
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        val mainViewModel = ViewModelProvider(this@DetailActivity, FollowFactory(userId))
            .get(DetailViewModel::class.java)

        mainViewModel.accountDetail().observe(this) { account->
            setAccount(account)
            setFavorite(false)
            favViewModel.readFavData.observe(this) { favUser ->
                for(user in favUser)
                    if(user.id==account.login) {
                        setFavorite(true)
                        break
                    }
            }
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

    private fun setFavorite(favorite:Boolean){
        if(favorite)Glide.with(this).load(R.drawable.ic_fav_fill).into(binding.accountFav)
        else Glide.with(this).load(R.drawable.ic_fav_empty).into(binding.accountFav)
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

        binding.accountFav.setOnClickListener{
            if(!akun.favorite)setAsFavorite(akun)
            else setAsFavoritent(akun)
        }

        val detailPagerAdapter = DetailPagerAdapter(this)
        binding.viewPager.adapter = detailPagerAdapter



        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun setAsFavoritent(akun: Accounts) {
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        favViewModel.deleteFavData(akun.login)


    }

    private fun setAsFavorite(akun: Accounts) {
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        val favdata = FavData(akun.login,true,akun.avatarUrl)
        favViewModel.addFav(favdata)
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


