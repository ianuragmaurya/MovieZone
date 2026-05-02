package com.am.moviesfix

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.am.moviesfix.utils.PrefManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var prefManager: PrefManager
    private lateinit var viewModel: MovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        prefManager = PrefManager(this)
        applyTheme()

        setContentView(R.layout.activity_main)


        val toolbar = findViewById<MaterialToolbar>(R.id.appBar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


        bottomNav.setupWithNavController(navController)


    }



    private fun applyTheme() {
        if (prefManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {

                    viewModel.searchMovies(it)
                }
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.fetchMovies()
                }
                return true
            }
        })
        return true
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId ) {
            R.id.action_theme_toggle -> {
                val isDark = !prefManager.isDarkMode()
                prefManager.saveDarkMode(isDark)
                applyTheme()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
