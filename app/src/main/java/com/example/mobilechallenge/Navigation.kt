package com.example.mobilechallenge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilechallenge.ui.viewmodels.LoginViewModel
import com.example.mobilechallenge.ui.views.LoginScreen
import com.example.mobilechallenge.ui.views.TreeScreen

@Composable
fun MobileChallengeNavHost(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(modifier, loginViewModel) {
                navController.navigate("tree")
            }
        }
        composable("tree") {
            TreeScreen(modifier, navController)
        }
    }
}