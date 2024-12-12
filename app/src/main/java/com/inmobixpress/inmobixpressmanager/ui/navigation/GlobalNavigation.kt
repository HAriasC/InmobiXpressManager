package com.inmobixpress.inmobixpressmanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.inmobixpress.inmobixpressmanager.data.network.implement.LoginServiceImpl
import com.inmobixpress.inmobixpressmanager.data.network.implement.PropertyServiceImpl
import com.inmobixpress.inmobixpressmanager.repository.LoginRepository
import com.inmobixpress.inmobixpressmanager.repository.PropertyRepository
import com.inmobixpress.inmobixpressmanager.ui.screens.MainScreen
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.LoginViewModel
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import io.ktor.client.HttpClient

@Composable
fun GlobalNavigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    mainViewModel: MainViewModel,
) {
    NavHost(navController = navController, startDestination = NavScreen.Auth) {
        authNavigation(
            navController = navController,
            viewModel = loginViewModel,
            mainViewModel = mainViewModel
        )
        composable<NavScreen.Main> {
            MainScreen(
                viewModel = mainViewModel,
                onLogOut = {
                    navController.navigate(NavScreen.Auth) {
                        popUpTo(id = 0)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun GlobalNavigationPreview() {
    GlobalNavigation(
        loginViewModel = LoginViewModel(LoginRepository(LoginServiceImpl(HttpClient()))),
        mainViewModel = MainViewModel(
            PropertyRepository(
                PropertyServiceImpl(
                    httpClient = HttpClient(),
                    storage = Firebase.storage
                )
            )
        )
    )
}