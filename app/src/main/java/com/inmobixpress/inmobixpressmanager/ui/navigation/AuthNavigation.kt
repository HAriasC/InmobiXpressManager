package com.inmobixpress.inmobixpressmanager.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.inmobixpress.inmobixpressmanager.ui.screens.LoginScreen
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.LoginViewModel

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    viewModel: LoginViewModel,
) {
    navigation<NavScreen.Auth>(startDestination = NavScreen.Login) {
        composable<NavScreen.Login>(
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }
        ) {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToMain = {
                    navController.navigate(NavScreen.Main)
                }
            )
        }
    }
}