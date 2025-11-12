package com.shoaib.weatherapp.presentation.navigation

import com.shoaib.weatherapp.utils.constants

sealed class AppRoute(
    val route : String
) {
    data object Splash : AppRoute(constants.ROUTE_SPLASH)
    data object Login : AppRoute(constants.ROUTE_LOGIN)
    data object SignUp : AppRoute(constants.ROUTE_SIGN_IN)
    data object Home : AppRoute(constants.ROUTE_HOME)
    data object History : AppRoute(constants.ROUTE_HISTORY)


}