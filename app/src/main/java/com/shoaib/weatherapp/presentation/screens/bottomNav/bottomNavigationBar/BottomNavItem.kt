package com.shoaib.weatherapp.presentation.screens.bottomNav.bottomNavigationBar

import com.shoaib.weatherapp.R
import com.shoaib.weatherapp.presentation.navigation.AppRoute

data class BottomNavItem(
    val label: String,
    val iconRes:Int,
    val route: String
)


val bottomItems = listOf(
    BottomNavItem(
        label = AppRoute.Home.route,
        iconRes = R.drawable.ic_home,
        route = AppRoute.Home.route
    ),
    BottomNavItem(
        label = AppRoute.History.route,
        iconRes = R.drawable.ic_history,
        route = AppRoute.History.route
    )
)