package com.inmobixpress.inmobixpressmanager.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inmobixpress.inmobixpressmanager.ui.components.DashboardItem
import com.inmobixpress.inmobixpressmanager.ui.model.catalogItems
import com.inmobixpress.inmobixpressmanager.ui.theme.InmobiXpressManagerTheme
import com.inmobixpress.inmobixpressmanager.ui.theme.Purple40
import com.inmobixpress.inmobixpressmanager.ui.theme.Purple80
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(
    drawerState: DrawerState,
    onItemClick: (Int) -> Unit,
) {
    val uiColor = if (isSystemInDarkTheme()) White else Black
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(206.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                    Text(
                        text = "Dashboard",
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge,
                        color = uiColor
                    )
                }
                Text(
                    text = "Gestiona parámetros generales del catálogo",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    color = uiColor,
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(modifier = Modifier.padding(top = 90.dp)) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
            ) {
                itemsIndexed(catalogItems) { index, item ->
                    DashboardItem(
                        catalogItem = item,
                        onItemClick = {
                            if (index == 0) {
                                onItemClick(index)
                            }
                        },
                        index = 0,
                        gradient = listOf(Purple40, Purple80, Purple80, Purple80, Purple80),
                        scrollProvider = { 0f }
                    )
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenPreview() {
    InmobiXpressManagerTheme {
        DashboardScreen(drawerState = rememberDrawerState(initialValue = DrawerValue.Closed), {})
    }
}