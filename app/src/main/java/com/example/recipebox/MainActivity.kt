package com.example.recipebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipebox.presentation.navigation.CustomBottomBar
import com.example.recipebox.presentation.navigation.NavGraph
import com.example.recipebox.presentation.navigation.Screen
import com.example.recipebox.ui.theme.RecipeBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            RecipeBoxTheme {
                // Hide on onboarding AND detail
                val shouldShowBottomBar =
                    currentRoute != Screen.Onboarding.route &&
                            (currentRoute?.startsWith(Screen.RecipeDetail.route) != true)

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar) {
                            CustomBottomBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        startDestination = Screen.Onboarding.route,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
