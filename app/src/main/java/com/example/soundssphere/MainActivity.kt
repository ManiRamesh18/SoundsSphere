package com.example.soundssphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.soundssphere.navigation.SoundNavigation
import com.example.soundssphere.ui.HomeScreenViewModel
import com.example.soundssphere.ui.SplashViewModel
import com.example.soundssphere.ui.theme.SoundsSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()

    private val homeScreenViewModel : HomeScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        splash.setKeepOnScreenCondition{ viewModel.isLoading.value }

        setContent {
            SoundsSphereTheme {
                SoundNavigation(homeScreenViewModel)
            }
        }
    }
}