package com.inmobixpress.inmobixpressmanager.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector
import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.ui.navigation.NavScreen

enum class NavigationItem(
    val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String,
    val destination: Any
) {
    HOME(
        title = R.string.tab_home,
        selectedIcon = Icons.Filled.Mail,
        unselectedIcon = Icons.Outlined.Mail,
        badgeCount = 0,
        route = NavScreen.Home.javaClass.name.replace("$", "."),
        destination = NavScreen.Home
    ),
    NOTIFICATION(
        title = R.string.tab_notification,
        selectedIcon = Icons.Filled.Today,
        unselectedIcon = Icons.Outlined.Today,
        badgeCount = 0,
        route = NavScreen.Notification.javaClass.name.replace("$", "."),
        destination = NavScreen.Notification
    )
}
