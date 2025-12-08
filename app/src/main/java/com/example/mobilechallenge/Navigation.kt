package com.example.mobilechallenge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobilechallenge.ui.viewmodels.LoginViewModel
import com.example.mobilechallenge.ui.views.LoginScreen
import com.example.mobilechallenge.ui.views.TreeScreen

@Composable
fun MobileChallengeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(modifier) { token ->
                navController.navigate("tree/$token") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable(
            route = "tree/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) {
            TreeScreen(modifier) {
                navController.navigate("login") {
                    popUpTo("tree") { inclusive = true }
                }
            }
        }
    }
}