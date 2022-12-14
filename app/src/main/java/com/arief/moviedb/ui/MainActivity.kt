package com.arief.moviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.arief.moviedb.R
import com.arief.moviedb.databinding.ActivityMainBinding
import com.arief.moviedb.model.Movies
import com.arief.moviedb.ui.movie.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel : MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initActivity()
    }

    private fun initActivity() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.popularFragment, R.id.nowPlayingFragment, R.id.upcomingFragment))
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        binding.navBottom.setupWithNavController(navController)

        binding.topAppBar.setOnMenuItemClickListener {
            it.onNavDestinationSelected(navController)
            true
        }
    }

    fun insertFavorite(item: Movies){
        viewModel.insertFavorite(item)
    }

    fun deleteFavorite(item: Movies){
        viewModel.deleteFavorite(item)
    }
}