package com.shoaib.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoaib.weatherapp.presentation.navigation.AppRoute
import com.shoaib.weatherapp.presentation.screens.login.LoginScreen
import com.shoaib.weatherapp.presentation.screens.splash.SplashScreen
import com.shoaib.weatherapp.presentation.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme(darkTheme = false) {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppRoute.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(route = AppRoute.Splash.route) {
                            SplashScreen(
                                onNavigateToLogin = {
                                    navController.navigate(AppRoute.Login.route) {
                                        popUpTo(AppRoute.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        
                        composable(route = AppRoute.Login.route) {
                            LoginScreen(
                                onNavigateToHome = {

                                },
                                onSignUpClick = {
                                    
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

