package com.inmobixpress.inmobixpressmanager.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.data.network.model.RequestHasPublishing
import com.inmobixpress.inmobixpressmanager.ui.model.ServiceMarker
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

private typealias KeyedLocationData = Pair<String, ServiceMarker>

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAddressBottomSheet(
    viewModel: MainViewModel,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    markerState: MarkerState = rememberMarkerState(),
    rotationMarker: MutableFloatState = rememberSaveable { mutableFloatStateOf(0.0f) },
    onItemClick: (LatLng) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.onSearchAddressBottomSheetVisible(visible = false)
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Log.e("GPS", "${rotationMarker.floatValue} ${markerState.position}")
        val properties by remember {
            mutableStateOf(
                MapProperties(
                    mapType = MapType.TERRAIN
                )
            )
        }
        val scope = rememberCoroutineScope()
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Confirmar") },
                    onClick = {
                        viewModel.onSearchAddressBottomSheetVisible(visible = false)
                    },
                    icon = {
                        Image(
                            imageVector = ImageVector.vectorResource(
                                R.drawable.maps_icon
                            ),
                            contentDescription = "Google Maps",
                            modifier = Modifier.size(size = 32.dp)
                        )
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        properties = properties
                    ) {
                        LocationMarker(keyedLocationData = viewModel.foundLocations.toList())
                    }

                    SearchLocationBar(
                        viewModel = viewModel,
                        onItemClick = {
                            scope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newCameraPosition(
                                        CameraPosition.fromLatLngZoom(
                                            LatLng(it.latitude, it.longitude),
                                            14f
                                        )
                                    )
                                )
                                onItemClick(it)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar(
    viewModel: MainViewModel,
    onRefresh: () -> Unit = {},
    onItemClick: (LatLng) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    // Initialize the SDK
    Places.initializeWithNewPlacesApiEnabled(context, "AIzaSyCgQtOFMwKZXP7ABlthN7OR19hvqFFlKt4")
    // Create a new PlacesClient instance
    val placesClient = Places.createClient(context)
    viewModel.placesClient = placesClient

    Box(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .semantics { isTraversalGroup = true }) {
        DockedSearchBar(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = viewModel.searchQuery,
                    onQueryChange = {
                        viewModel.onSearchQueryChange(it)
                        viewModel.searchAddress(query = it)
                    },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Ingresa una dirección") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
                    trailingIcon = {
                        if (expanded) {
                            IconButton(
                                onClick = {
                                    if (viewModel.searchQuery.isNotEmpty()) {
                                        viewModel.onSearchQueryChange("")
                                        viewModel.foundLocations.entries.clear()
                                        onRefresh()
                                    } else {
                                        expanded = false
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = ""
                                )
                            }
                        } else {
                            IconButton(
                                onClick = {
                                    expanded = false
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            if (viewModel.locationAutofill.isEmpty()) {
                PropertyListEmptyState()
            } else {
                LazyColumn {
                    items(viewModel.locationAutofill) { item ->
                        ListItem(
                            headlineContent = { Text(text = item.address) },
                            supportingContent = { Text(text = item.secondary) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.LocationOn,
                                    contentDescription = null
                                )
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier =
                            Modifier
                                .clickable {
                                    viewModel.foundLocations.entries.clear()
                                    viewModel.getCoordinates(
                                        result = item,
                                        onLocationResult = {
                                            viewModel.foundLocations[item.placeId] = ServiceMarker(
                                                result = item,
                                                location = it
                                            )
                                            onItemClick(it)
                                        }
                                    )
                                    viewModel.onSearchQueryChange(
                                        newQuery = "${item.address}, ${item.secondary}"
                                    )

                                    expanded = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyListEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(horizontal = 8.dp)
    ) {
        Text(
            text = "No se encontraron direcciones que coincidan con el texto",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Pruebe con otra dirección",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun LocationMarker(keyedLocationData: Collection<KeyedLocationData>) {
    keyedLocationData.forEach { (key, marker) ->
        key(key) {
            MarkerComposable(
                state = MarkerState(position = marker.location),
                anchor = Offset(0.45f, 0.5f),
                title = "${marker.result.address}, ${marker.result.secondary}"
            ) {
                FilledIconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxDetailBottomSheet(
    viewModel: MainViewModel,
    request: RequestHasPublishing,
    onNavigateToLogin: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ModalBottomSheet(
        onDismissRequest = {
            viewModel.onLoadingVisible(visible = false)
            //viewModel.onWhatsAppBottomSheetVisible(false)
            viewModel.clearForm()
        },
        sheetState = rememberModalBottomSheetState()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                )

            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Message,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                )

            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

        }
    }
}