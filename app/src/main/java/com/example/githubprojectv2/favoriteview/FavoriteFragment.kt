package com.example.githubprojectv2.favoriteview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubprojectv2.database.FavData
import com.example.githubprojectv2.database.FavViewModel
import com.example.githubprojectv2.databinding.FragmentFavoriteBinding
import com.example.githubprojectv2.detailview.DetailActivity

class FavoriteFragment : Fragment() {
    private lateinit var rvFavorites: RecyclerView
    private lateinit var rvAdapter : AdapterFavorite


    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        rvFavorites = binding.rvFavorite
        rvFavorites.layoutManager = (LinearLayoutManager(this.context))
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        favViewModel.readFavData.observe(viewLifecycleOwner) {
            setFavorite(it)
        }
        return binding.root
    }

    private fun setFavorite(data: List<FavData>) {
        val listFavorite = ArrayList<FavData>()
        listFavorite.addAll(data)
        rvAdapter = AdapterFavorite(listFavorite)
        rvFavorites.adapter = rvAdapter
        rvAdapter.setOnItemClickCallback(object : AdapterFavorite.OnItemClickCallback {
            override fun onItemClicked(data: String) {
                showSelectedAccount(data)
            }

            override fun onFavClicked(fav: FavData) {
                deleteFromDatabase(fav.id)
            }
        })
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

}