package com.inmobixpress.inmobixpressmanager.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.inmobixpress.inmobixpressmanager.ui.screens.ChartScreen
import com.inmobixpress.inmobixpressmanager.ui.screens.DashboardScreen
import com.inmobixpress.inmobixpressmanager.ui.screens.DetailScreen
import com.inmobixpress.inmobixpressmanager.ui.screens.InboxScreen
import com.inmobixpress.inmobixpressmanager.ui.screens.PropertyRegistrationScreen
import com.inmobixpress.inmobixpressmanager.ui.screens.ScheduleScreen
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainNavigation(
    viewModel: MainViewModel,
    navController: NavHostController,
    drawerState: DrawerState,
    onLogOut: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = NavScreen.Inbox) {
        composable<NavScreen.Inbox>(
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
            InboxScreen(drawerState = drawerState)
        }
        composable<NavScreen.Message>(
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
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<NavScreen.Message>()
            /*MessageScreen(
                viewModel = viewModel,
                id = args.id,
                onNavigateBack = {
                    scope.launch {
                        viewModel.onVisibleContactBarChanged(false)
                        delay(timeMillis = 200)
                        viewModel.onVisibleChanged(true)
                        delay(timeMillis = 200)
                        navController.popBackStack()
                    }
                }
            )*/
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
            ScheduleScreen(drawerState = drawerState)
        }
        composable<NavScreen.Dashboard>(
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
            DashboardScreen(drawerState = drawerState) { index ->
                navController.navigate(NavScreen.Detail(id = index))
            }
        }
        composable<NavScreen.Detail>(
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
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<NavScreen.Detail>()
            DetailScreen(
                viewModel = viewModel,
                index = args.id,
                onNavigateBack = {
                    scope.launch {
                        //viewModel.onVisibleContactBarChanged(false)
                        delay(timeMillis = 200)
                        //viewModel.onVisibleChanged(true)
                        delay(timeMillis = 200)
                        navController.navigateUp()
                    }
                }
            )
        }
        composable<NavScreen.Charts>(
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
            ChartScreen()
        }
    }
}