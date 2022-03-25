package com.example.githubprojectv2.searchview

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubprojectv2.R
import com.example.githubprojectv2.database.FavViewModel
import com.example.githubprojectv2.databinding.ActivityMainBinding
import com.example.githubprojectv2.favoriteview.FavoriteFragment
import com.example.githubprojectv2.settingview.MainViewModel
import com.example.githubprojectv2.settingview.SettingFragment
import com.example.githubprojectv2.settingview.SettingPreferences
import com.example.githubprojectv2.settingview.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(this.dataStore)
        val favViewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }


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
                val mainViewModel = ViewModelProvider(this@MainActivity, ListFactory(query)).get(ListViewModel::class.java)
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
                    .replace(R.id.fragmentContainerView, SettingFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.menu3->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, FavoriteFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }
            else -> return true
        }
    }

}