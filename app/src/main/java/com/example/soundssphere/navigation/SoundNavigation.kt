package com.example.soundssphere.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soundssphere.ui.HomeScreenViewModel
import com.example.soundssphere.ui.screens.HomeScreen

@Composable
fun SoundNavigation(viewModel: HomeScreenViewModel)
{
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Home")
    {
        composable("Home")
        {
            HomeScreen(viewModel, navController)
        }
    }

}