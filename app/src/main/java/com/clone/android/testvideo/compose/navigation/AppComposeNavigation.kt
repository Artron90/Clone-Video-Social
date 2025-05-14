package com.clone.android.testvideo.compose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.clone.android.testvideo.ui.compose.home.HomeComposeScreen
import com.clone.android.testvideo.ui.compose.home.HomeComposeViewModel

@Composable
fun AppComposeNavigation() {

    val navController = rememberNavController()
    val homeComposeViewModel = hiltViewModel<HomeComposeViewModel>()

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.name
    ) {


        composable(AppScreens.HomeScreen.name) {
            HomeComposeScreen(homeComposeViewModel = homeComposeViewModel)
        }

    }
}
