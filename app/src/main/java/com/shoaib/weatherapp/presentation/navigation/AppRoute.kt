package com.shoaib.weatherapp.presentation.navigation

import com.shoaib.weatherapp.utils.Constants

sealed class AppRoute(
    val route : String
) {
    data object Splash : AppRoute(Constants.ROUTE_SPLASH)
    data object Login : AppRoute(Constants.ROUTE_LOGIN)
    data object SignUp : AppRoute(Constants.ROUTE_SIGN_IN)
    data object Home : AppRoute(Constants.ROUTE_HOME)
    data object History : AppRoute(Constants.ROUTE_HISTORY)


    companion object {
        val routesWithBottomBar = setOf(
            Home.route,
            History.route
        )


        fun shouldShowBottomBar(route: String?): Boolean {
            return route in routesWithBottomBar
        }
    }


}