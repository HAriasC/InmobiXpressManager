package com.inmobixpress.inmobixpressmanager.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.inmobixpress.inmobixpressmanager.ui.screens.InboxScreen
import com.inmobixpress.inmobixpressmanager.ui.screens.ScheduleScreen

@Composable
fun MainNavigation(
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = NavScreen.Home) {
        composable<NavScreen.Home>(
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
            InboxScreen()
        }
        composable<NavScreen.Notification>(
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
            ScheduleScreen()
        }
    }
}