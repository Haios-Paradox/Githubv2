package com.example.githubprojectv2.searchview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprojectv2.api.ItemsItem
import com.example.githubprojectv2.database.FavData
import com.example.githubprojectv2.database.FavViewModel
import com.example.githubprojectv2.databinding.FragmentListBinding
import com.example.githubprojectv2.detailview.DetailActivity

class ListFragment : Fragment() {
    private lateinit var rvAccount: RecyclerView
    private lateinit var rvAdapter : AdapterAccount


    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        rvAccount = binding.recyclerAccount
        rvAccount.layoutManager = LinearLayoutManager(this.context)
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        val mainViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)

        mainViewModel.accounts().observe(viewLifecycleOwner) {searchData->
            val adapter = setAccountData(searchData)
            favViewModel.readFavData.observe(viewLifecycleOwner) { favUser ->
                updateAccountData(searchData, favUser, adapter)
            }
        }

        mainViewModel.loading().observe(viewLifecycleOwner){
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAccountData(accounts: List<ItemsItem>, fav : List<FavData>, adapter:AdapterAccount) {
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

    private fun setAccountData(accounts: List<ItemsItem>) : AdapterAccount {
        val list = ArrayList<ItemsItem>()

        list.addAll(accounts)
        rvAdapter = AdapterAccount(list)
        rvAccount.adapter = rvAdapter
        rvAdapter.setOnItemClickCallback(object : AdapterAccount.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                showSelectedAccount(data)
            }
            override fun onFavClicked(fav: ItemsItem) {
                if(!fav.favorite)
                    insertToDatabase(fav.login,fav.avatarUrl)
                else {
                    deleteFromDatabase(fav.login)
                }
            }
        })
        return rvAdapter
    }

    private fun deleteFromDatabase(id: String) {
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        favViewModel.deleteFavData(id)

    }

    private fun showSelectedAccount(account : String){
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.USER_ID,account)
        startActivity(intent)
    }

    private fun insertToDatabase(id:String,avatar:String){
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        val favdata = FavData(id,true,avatar)
        favViewModel.addFav(favdata)
    }
}