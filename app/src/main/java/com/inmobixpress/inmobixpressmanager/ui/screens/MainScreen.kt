package com.inmobixpress.inmobixpressmanager.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.inmobixpress.inmobixpressmanager.data.network.implement.PropertyServiceImpl
import com.inmobixpress.inmobixpressmanager.repository.PropertyRepository
import com.inmobixpress.inmobixpressmanager.ui.components.DrawerNavigation
import com.inmobixpress.inmobixpressmanager.ui.components.LoadingScreen
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import io.ktor.client.HttpClient

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
    val showLoading by viewModel.loadingVisible.observeAsState(initial = false)
    DrawerNavigation(
        viewModel = viewModel,
        drawerState = drawerState,
        navController = navController,
        onLogOut = onLogOut
    )
    AnimatedVisibility(
        visible = showLoading,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        LoadingScreen(message = "Cargando...")
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        navController = rememberNavController(),
        viewModel = MainViewModel(
            PropertyRepository(
                PropertyServiceImpl(
                    httpClient = HttpClient(),
                    storage = Firebase.storage
                )
            )
        ),
        onLogOut = {}
    )
}