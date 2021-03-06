package com.example.githubprojectv2.detailview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprojectv2.api.ResponseFollowItem
import com.example.githubprojectv2.database.FavData
import com.example.githubprojectv2.database.FavViewModel
import com.example.githubprojectv2.databinding.FragmentFollowsBinding
import com.example.githubprojectv2.detailview.DetailActivity.Companion.USER_ID


class FollowsFragment : Fragment() {

    private lateinit var rvAccount: RecyclerView
    private lateinit var rvAdapter : AdapterFollow

    private val accountList = ArrayList<ResponseFollowItem>()
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        val favViewModel = ViewModelProvider(requireActivity()).get(FavViewModel::class.java)
        val followsModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        followsModel.accountsFollows().observe(viewLifecycleOwner) { followUser->
            val adapter = setAccountData(followUser)
            favViewModel.readFavData.observe(viewLifecycleOwner) { favUser ->
                updateAccountData(followUser,favUser,adapter)
            }
        }
        return binding.root
    }

    private fun setAccountData(account: List<ResponseFollowItem>) : AdapterFollow {
        accountList.addAll(account)
        rvAccount = binding.rvFollows
        rvAccount.layoutManager = LinearLayoutManager(this.context)
        rvAdapter = AdapterFollow(accountList)
        rvAccount.adapter = rvAdapter

        rvAdapter.setOnItemClickCallback(object : AdapterFollow.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                showSelectedAccount(data)
            }
        })
        return rvAdapter
    }

    private fun showSelectedAccount(account : String){
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(USER_ID,account)
        startActivity(intent)
    }

    private fun updateAccountData(accounts: List<ResponseFollowItem>, fav : List<FavData>, adapter: AdapterFollow) {
        for(i in accounts){
            for(j in fav){
                if(i.login == j.id) {
                    i.favorite = true
                    break
                }
                else i.favorite = false
            }
        }
        adapter.notifyDataSetChanged()
    }

}