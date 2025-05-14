package com.clone.android.testvideo.compose.navigation

enum class AppScreens {
    HomeScreen;

    @Suppress("unused")
    companion object {
        fun fromRoute(route: String?): AppScreens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}