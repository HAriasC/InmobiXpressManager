package com.inmobixpress.inmobixpressmanager.ui.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.inmobixpress.inmobixpressmanager.data.network.implement.PropertyServiceImpl
import com.inmobixpress.inmobixpressmanager.data.network.model.Device
import com.inmobixpress.inmobixpressmanager.data.network.model.RequestHasPublishing
import com.inmobixpress.inmobixpressmanager.repository.PropertyRepository
import com.inmobixpress.inmobixpressmanager.ui.components.ReplyDockedSearchBar
import com.inmobixpress.inmobixpressmanager.ui.model.UIState
import com.inmobixpress.inmobixpressmanager.ui.utils.sortDatesDescending
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import io.ktor.client.HttpClient

@Composable
fun InboxScreen(
    viewModel: MainViewModel,
    drawerState: DrawerState,
    onItemClick: (Int) -> Unit,
) {
    val publishing by viewModel.requestXPublishing.collectAsState()
    val devices by viewModel.devices.collectAsState()
    var requestsItemList by rememberSaveable { mutableStateOf(listOf<RequestHasPublishing>()) }
    var messageError by rememberSaveable { mutableStateOf("") }
    ReplyEmailList(
        viewModel = viewModel,
        drawerState = drawerState,
        requests = requestsItemList,
        openedEmail = requestsItemList.firstOrNull(),
        selectedEmailIds = emptySet(),
        toggleEmailSelection = {

        },
        emailLazyListState = rememberLazyListState(),
        navigateToDetail = onItemClick
    )

    when (devices) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = devices) {
                viewModel.onLoadingVisible(visible = true)
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = devices) {
                viewModel.loadRequestsXPublishing()
                if (devices is UIState.Success<List<Device>>) {
                    viewModel.onDevicesChanged(
                        devices = (devices as UIState.Success<List<Device>>).data
                    )
                }
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = devices) {
                if (devices is UIState.Error<List<Device>>) {
                    messageError = (devices as UIState.Error<List<Device>>).error.toString()
                    Log.e("RxP", messageError)
                }
                viewModel.onLoadingVisible(visible = false)
                viewModel.onErrorDialogVisible(visible = true)
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = devices) {
                viewModel.onLoadingVisible(visible = true)
            }
        }
    }

    when (publishing) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = publishing) {
                viewModel.onLoadingVisible(visible = true)
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = publishing) {
                viewModel.onLoadingVisible(visible = false)
                if (publishing is UIState.Success<List<RequestHasPublishing>>) {
                    viewModel.onRequestsChanged(
                        requests = sortDatesDescending(
                            dates = (publishing as UIState.Success<List<RequestHasPublishing>>).data
                        )
                    )
                    requestsItemList = sortDatesDescending(
                        dates = (publishing as UIState.Success<List<RequestHasPublishing>>).data
                    )
                }
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = publishing) {
                if (publishing is UIState.Error<List<RequestHasPublishing>>) {
                    messageError =
                        (publishing as UIState.Error<List<RequestHasPublishing>>).error.toString()
                    Log.e("RxP", messageError)
                }
                viewModel.onLoadingVisible(visible = false)
                viewModel.onErrorDialogVisible(visible = true)
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = publishing) {
                viewModel.onLoadingVisible(visible = true)
            }
        }
    }
    // on below line we are creating a variable for broad cast manager status.
    val broadCastMsg = remember {
        mutableStateOf("Welcome")
    }
    // on below line we are creating a new broad cast manager.
    val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        // we will receive data updates in onReceive method.
        override fun onReceive(context: Context?, intent: Intent) {
            // Get extra data included in the Intent
            val message = intent.getStringExtra("message")
            // on below line we are updating the data in our text view.
            Log.e("FMC", message.toString())
            broadCastMsg.value = message!!
        }
    }
    // on below line we are registering our local broadcast manager.
    LocalBroadcastManager.getInstance(LocalContext.current).registerReceiver(
        broadcastReceiver, IntentFilter("custom-action-local-broadcast")
    )
}

@Composable
fun ReplyEmailList(
    viewModel: MainViewModel,
    drawerState: DrawerState,
    requests: List<RequestHasPublishing>,
    openedEmail: RequestHasPublishing?,
    selectedEmailIds: Set<Int>,
    toggleEmailSelection: (Int) -> Unit,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    Box(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        ReplyDockedSearchBar(
            drawerState = drawerState,
            requests = requests,
            onSearchItemSelected = { searchedEmail ->
                navigateToDetail(searchedEmail.request.id)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )

        if (requests.isEmpty()) {
            InboxEmptyState()
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                state = emailLazyListState
            ) {
                items(
                    count = requests.size,
                    key = { index -> requests[index].request.id },
                ) { index ->
                    ReplyEmailListItem(
                        viewModel = viewModel,
                        request = requests[index],
                        navigateToDetail = { emailId ->
                            navigateToDetail(emailId)
                        },
                        toggleSelection = toggleEmailSelection,
                        isOpened = openedEmail?.request?.id == requests[index].request.id,
                        isSelected = selectedEmailIds.contains(requests[index].request.id)
                    )
                }
                // Add extra spacing at the bottom if
                item {
                    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReplyEmailListItem(
    viewModel: MainViewModel,
    request: RequestHasPublishing,
    navigateToDetail: (Int) -> Unit,
    toggleSelection: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isOpened: Boolean = false,
    isSelected: Boolean = false,
) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .semantics { selected = isSelected }
            .clip(CardDefaults.shape)
            .combinedClickable(
                onClick = { navigateToDetail(request.request.id) },
                onLongClick = { toggleSelection(request.request.id) }
            )
            .clip(CardDefaults.shape),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else if (isOpened) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                val clickModifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { toggleSelection(request.request.id) }
                AnimatedContent(targetState = isSelected, label = "avatar") { selected ->
                    if (selected) {
                        SelectedProfileImage(clickModifier)
                    } else {
                        ReplyProfileImage(
                            request = request,
                            description = request.request.user.name,
                            modifier = clickModifier
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = request.request.user.name,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "${request.createDate.date} ${request.createDate.time}",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Icon(
                        imageVector = Icons.Default.StarBorder,
                        contentDescription = "Favorite",
                        tint = viewModel.colors(id = request.request.requestState.id)
                        //tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Text(
                text = request.request.requestType.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = request.request.message,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SelectedProfileImage(modifier: Modifier = Modifier) {
    Box(
        modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun ReplyProfileImage(
    request: RequestHasPublishing,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        imageVector = if (request.request.requestType.id == 1) Icons.Outlined.Email
        else Icons.Outlined.Today,
        contentDescription = description,
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun InboxEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "la bandeja de entrada esta vacía",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Pruebe con refrescando la pantalla",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun InboxScreenPreview() {
    InboxScreen(
        viewModel = MainViewModel(
            PropertyRepository(
                PropertyServiceImpl(httpClient = HttpClient(), storage = Firebase.storage)
            )
        ),
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        onItemClick = {}
    )
}