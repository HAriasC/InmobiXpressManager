package com.inmobixpress.inmobixpressmanager.ui.screens

import androidx.compose.runtime.Composable
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel

@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    index: Int,
    onNavigateBack: () -> Unit
) {
    when(index) {
        0 -> {
            viewModel.loadProperties()
            PropertyRegistrationScreen(viewModel = viewModel)
        }
        1 -> {

        }
    }
}