package com.inmobixpress.inmobixpressmanager.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.ui.model.DrawerMenu
import com.inmobixpress.inmobixpressmanager.ui.model.Email
import com.inmobixpress.inmobixpressmanager.ui.model.NavigationItem
import com.inmobixpress.inmobixpressmanager.ui.navigation.MainNavigation
import com.inmobixpress.inmobixpressmanager.ui.screens.ReplyProfileImage
import com.inmobixpress.inmobixpressmanager.ui.utils.formatNavRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerNavigation(
    drawerState: DrawerState,
    navController: NavHostController,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(navController = navController) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route)
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.displayCutout),
            bottomBar = { BottomBar(navController = navController) }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                MainNavigation(navController = navController)
            }
        }
    }
}

@Composable
private fun DrawerContent(
    navController: NavHostController,
    onMenuClick: (Any) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DrawerMenu.entries.forEach { item ->
            val selected = navController.currentBackStackEntryAsState()
                .value?.destination?.route?.formatNavRoute() == item.route
            NavigationDrawerItem(
                label = { Text(text = stringResource(id = item.title)) },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = ""
                    )
                },
                badge = {
                    if (item.badgeCount > 0) {
                        Text(text = item.badgeCount.toString())
                    }
                },
                selected = selected,
                onClick = {
                    onMenuClick(item.destination)
                }
            )
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        NavigationBar {
            NavigationItem.entries.forEach { item ->
                val selected = navController.currentBackStackEntryAsState()
                    .value?.destination?.route?.formatNavRoute() == item.route
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (selected.not()) {
                            navController.navigate(item.destination)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = item.title))
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyDockedSearchBar(
    emails: List<Email>,
    onSearchItemSelected: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchResults = remember { mutableStateListOf<Email>() }

    LaunchedEffect(query) {
        searchResults.clear()
        if (query.isNotEmpty()) {
            searchResults.addAll(
                emails.filter {
                    it.subject.startsWith(
                        prefix = query,
                        ignoreCase = true
                    ) || it.sender.fullName.startsWith(
                        prefix =
                        query,
                        ignoreCase = true
                    )
                }
            )
        }
    }

    DockedSearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = {
            query = it
        },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = { Text(text = stringResource(id = R.string.search_message)) },
        leadingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atras",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            active = false
                            query = ""
                        },
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        },
        trailingIcon = {
            ReplyProfileImage(
                drawableResource = R.drawable.ic_launcher_foreground,
                description = "",
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
            )
        },
    ) {
        if (searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    count = searchResults.size,
                    key = { index -> searchResults[index].id },
                ) { index ->
                    ListItem(
                        headlineContent = { Text(searchResults[index].subject) },
                        supportingContent = { Text(searchResults[index].sender.fullName) },
                        leadingContent = {
                            ReplyProfileImage(
                                drawableResource = searchResults[index].sender.avatar,
                                description = "",
                                modifier = Modifier
                                    .size(32.dp)
                            )
                        },
                        modifier = Modifier.clickable {
                            onSearchItemSelected.invoke(searchResults[index])
                            query = ""
                            active = false
                        }
                    )
                }
            }
        } else if (query.isNotEmpty()) {
            Text(
                text = "No hay mensajes encontrados",
                modifier = Modifier.padding(16.dp)
            )
        } else
            Text(
                text = "No hay mensajes que mostrar",
                modifier = Modifier.padding(16.dp)
            )
    }
}