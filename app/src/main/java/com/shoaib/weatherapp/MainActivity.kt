package com.shoaib.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shoaib.weatherapp.presentation.viewModel.auth.AuthViewModel
import com.shoaib.weatherapp.presentation.navigation.AppRoute
import com.shoaib.weatherapp.presentation.screens.home.HomeScreen
import com.shoaib.weatherapp.presentation.screens.login.LoginScreen
import com.shoaib.weatherapp.presentation.screens.signup.SignUpScreen
import com.shoaib.weatherapp.presentation.screens.splash.SplashScreen
import com.shoaib.weatherapp.presentation.theme.WeatherAppTheme
import com.shoaib.weatherapp.presentation.viewModel.auth.AuthViewModelFactory
import com.shoaib.weatherapp.presentation.viewModel.weather.WeatherViewModel
import com.shoaib.weatherapp.presentation.viewModel.weather.WeatherViewModelFactory
import com.shoaib.weatherapp.utils.LocationPermissionHandler

import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var weatherViewModelFactory: WeatherViewModelFactory

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as WeatherApp).appComponent.inject(this)

        enableEdgeToEdge()

        val locationPermissionHandler = LocationPermissionHandler(application)

        setContent {
            val weatherViewModel = ViewModelProvider(
                this@MainActivity,
                weatherViewModelFactory
            )[WeatherViewModel::class.java]

            val authViewModel = ViewModelProvider(
                this@MainActivity,
                authViewModelFactory
            )[AuthViewModel::class.java]


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

                        composable(route = AppRoute.SignUp.route) {
                            SignUpScreen(
                                viewModel = authViewModel,
                                onNavigateToLogin = {
                                    navController.navigate(AppRoute.Login.route) {
                                        popUpTo(AppRoute.SignUp.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(route = AppRoute.Login.route) {
                            LoginScreen(
                                viewModel = authViewModel,
                                onNavigateToHome = {
                                    navController.navigate(AppRoute.Home.route) {
                                        popUpTo(AppRoute.Home.route) { inclusive = true }
                                    }
                                },
                                onSignUpClick = {
                                    navController.navigate(AppRoute.SignUp.route) {
                                        popUpTo(AppRoute.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(route = AppRoute.Home.route) {
                            HomeScreen(
                                viewModel = weatherViewModel,
                                locationPermissionHandler = locationPermissionHandler
                            )
                        }

                    }
                }
            }
        }
    }
}

