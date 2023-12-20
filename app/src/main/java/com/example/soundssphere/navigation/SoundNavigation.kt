package com.example.soundssphere.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SoundNavigation()
{
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "Home" )
    {
        composable("Home")
        {

        }
    }




}