package com.shoaib.weatherapp.presentation.screens.bottomNav

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.shoaib.weatherapp.R

data class BottomNavItem(
    val label: String,
    val iconRes:Int,
    val route: String
)

@Composable
fun getBottomNavItems(): List<BottomNavItem> = listOf(
    BottomNavItem(
        label = stringResource(R.string.nav_home),
        iconRes = R.drawable.ic_home,
        route = "home"
    ),
    BottomNavItem(
        label = stringResource(R.string.nav_history),
        iconRes = R.drawable.ic_history,
        route = "history"
    )
)