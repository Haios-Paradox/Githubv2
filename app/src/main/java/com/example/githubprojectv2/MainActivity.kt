package com.example.githubprojectv2

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.githubprojectv2.databinding.ActivityMainBinding
import com.example.githubprojectv2.searchview.ListFactory
import com.example.githubprojectv2.searchview.ListFragment
import com.example.githubprojectv2.searchview.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView



        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Masukan Teks"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                val mainViewModel = ViewModelProvider(
                    this@MainActivity,
                    ListFactory(query))
                    .get(ListViewModel::class.java)
                mainViewModel.search(query)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, ListFragment())
                        .addToBackStack(null)
                        .commit()
                return true

            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, HomeFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.menu2 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, AboutFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            else -> return true
        }
    }

}