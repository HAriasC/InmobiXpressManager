package com.inmobixpress.inmobixpressmanager.ui.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.InsertChartOutlined
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.DashboardCustomize
import androidx.compose.material.icons.outlined.InsertChartOutlined
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector
import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.ui.navigation.NavScreen
import com.inmobixpress.inmobixpressmanager.ui.utils.formatNavRoute

enum class DrawerMenu(
    val title: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int,
    val divider: Boolean = false,
    val route: String,
    val destination: Any,
) {
    HOME(
        title = R.string.tab_home,
        selectedIcon = Icons.Filled.Mail,
        unselectedIcon = Icons.Outlined.Mail,
        badgeCount = 10,
        route = NavScreen.Inbox.javaClass.name.formatNavRoute(),
        destination = NavScreen.Inbox
    ),
    NOTIFICATION(
        title = R.string.tab_notification,
        selectedIcon = Icons.Filled.Today,
        unselectedIcon = Icons.Outlined.Today,
        badgeCount = 0,
        route = NavScreen.Notification.javaClass.name.formatNavRoute(),
        destination = NavScreen.Notification
    ),
    DASHBOARD(
        title = R.string.tab_dashboard,
        selectedIcon = Icons.Filled.DashboardCustomize,
        unselectedIcon = Icons.Outlined.DashboardCustomize,
        badgeCount = 0,
        route = NavScreen.Dashboard.javaClass.name.replace("$", "."),
        destination = NavScreen.Dashboard
    ),
    CHARTS(
        title = R.string.tab_charts,
        selectedIcon = Icons.Filled.InsertChartOutlined,
        unselectedIcon = Icons.Outlined.InsertChartOutlined,
        badgeCount = 0,
        route = NavScreen.Charts.javaClass.name.replace("$", "."),
        destination = NavScreen.Charts
    ),
    LOGOUT(
        title = R.string.tab_logout,
        selectedIcon = Icons.AutoMirrored.Filled.Logout,
        unselectedIcon = Icons.AutoMirrored.Outlined.Logout,
        badgeCount = 0,
        divider = true,
        route = NavScreen.Auth.javaClass.name.replace("$", "."),
        destination = NavScreen.Auth
    )
}