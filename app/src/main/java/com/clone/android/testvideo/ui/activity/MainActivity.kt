package com.clone.android.testvideo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.clone.android.testvideo.R
import com.clone.android.testvideo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupNavigation()

    }


    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main)
        if (navHostFragment != null) {
            navController = navHostFragment.findNavController()

            navController.addOnDestinationChangedListener { _, _, _ ->
                supportActionBar?.hide()
            }

            val graphInflater = navHostFragment.findNavController().navInflater
            val navGraph = graphInflater.inflate(R.navigation.main_graph)

            navGraph.setStartDestination(R.id.homeFragment)

            navController.graph = navGraph


        }
        appBarConfiguration = AppBarConfiguration(navController.graph)

    }

}