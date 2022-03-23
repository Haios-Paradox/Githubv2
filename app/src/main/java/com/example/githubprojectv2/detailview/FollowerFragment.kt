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
import com.example.githubprojectv2.databinding.FragmentFollowerBinding
import com.example.githubprojectv2.detailview.DetailActivity.Companion.USER_ID

class FollowerFragment : Fragment() {

    private lateinit var rvAccount: RecyclerView
    private lateinit var rvAdapter : AdapterFollow

    private val accountList = ArrayList<ResponseFollowItem>()
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFollowerBinding.inflate(inflater, container, false)

        val followerModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        followerModel.accountsFollower().observe(viewLifecycleOwner) {
            setAccountData(it)
        }
        return binding.root
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

}