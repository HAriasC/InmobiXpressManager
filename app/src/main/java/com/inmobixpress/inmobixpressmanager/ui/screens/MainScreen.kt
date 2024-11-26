package com.inmobixpress.inmobixpressmanager.ui.screens

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.inmobixpress.inmobixpressmanager.ui.components.DrawerNavigation
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel,
    onLogOut: () -> Unit,
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    DrawerNavigation(
        viewModel = viewModel,
        drawerState = drawerState,
        navController = navController,
        onLogOut = onLogOut
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), viewModel = MainViewModel(), onLogOut = {})
}