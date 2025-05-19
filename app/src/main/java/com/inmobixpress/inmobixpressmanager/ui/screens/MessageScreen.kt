package com.inmobixpress.inmobixpressmanager.ui.screens

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inmobixpress.inmobixpressmanager.data.network.model.Historical
import com.inmobixpress.inmobixpressmanager.data.network.model.PublishingState
import com.inmobixpress.inmobixpressmanager.data.network.model.RequestHasPublishing
import com.inmobixpress.inmobixpressmanager.ui.components.AdvancedTimePickerLauncher
import com.inmobixpress.inmobixpressmanager.ui.components.DatePickerWithDialog
import com.inmobixpress.inmobixpressmanager.ui.components.FormAlertDialog
import com.inmobixpress.inmobixpressmanager.ui.model.UIState
import com.inmobixpress.inmobixpressmanager.ui.utils.callProprietor
import com.inmobixpress.inmobixpressmanager.ui.utils.hourToMillis
import com.inmobixpress.inmobixpressmanager.ui.utils.launchEmailChooser
import com.inmobixpress.inmobixpressmanager.ui.utils.launchPDFChooser
import com.inmobixpress.inmobixpressmanager.ui.utils.millisToLocalDateTime
import com.inmobixpress.inmobixpressmanager.ui.utils.sendWhatsAppsProprietor
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    viewModel: MainViewModel,
    id: Int,
    onNavigateBack: () -> Unit
) {
    val showVisitDateDialog by viewModel.visitDayDialogVisible.observeAsState()
    val showVisitTimeDateDialog by viewModel.visitDayTimeDialogVisible.observeAsState()
    val showConfirmDialog by viewModel.confirmDialogVisible.observeAsState()
    val item = viewModel.requests.value!!.first { it.request.id == id }
    val visitDay by viewModel.visitDay.observeAsState()
    val uploadDocument by viewModel.uploadDocument.collectAsState()
    val historical by viewModel.insertHistorical.collectAsState()
    val stateId = rememberSaveable { mutableIntStateOf(1) }
    val update by viewModel.updateRequest.collectAsState()
    var messageError by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val url = rememberSaveable { mutableStateOf("") }
    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
        if (it != null) {
            viewModel.uploadDocument(docURI = it)
        }
    }
    Scaffold(
        modifier = Modifier.padding(all = 16.dp),
        topBar = {
            TitleBar(viewModel = viewModel, id = id, onNavigateBack = onNavigateBack)
        },
        bottomBar = {
            ContactBar(viewModel = viewModel, id = id)
        },
        floatingActionButton = {
            if (item.request.requestState.id == 1) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onVisitDayDialogVisible(visible = true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Today,
                        contentDescription = "Fecha de visita"
                    )
                }
            }
            if (item.request.requestState.id == 3) {
                FloatingActionButton(
                    onClick = {
                        if (url.value.isNotEmpty()) {
                            context.launchPDFChooser(url = url.value)
                        } else {
                            launcher.launch(arrayOf("application/pdf"))
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DocumentScanner,
                        contentDescription = "Documento"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            Text(
                text = item.request.user.name,
                modifier = Modifier.padding(start = 8.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Fecha de creación: ${item.createDate.date} ${item.createDate.time}",
                modifier = Modifier.padding(start = 8.dp, top = 10.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = item.request.message,
                modifier = Modifier.padding(start = 8.dp, top = 10.dp),
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.bodyMedium,
            )
            /*Row(verticalAlignment = Alignment.CenterVertically) {
                State(viewModel = viewModel, request = item)

            }*/
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card {
                    Text(
                        text = "Fecha de visita: $visitDay",
                        modifier = Modifier.padding(all = 16.dp),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            SliderSample(viewModel = viewModel, id = id)
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            StateCard(
                viewModel = viewModel,
                id = id,
                onConfirmChangeState = {
                    if (stateId.intValue == 4) {
                        stateId.intValue = 1
                    } else {
                        stateId.intValue = item.request.requestState.id + 1
                    }
                    viewModel.onConfirmDialogVisible(visible = true)
                },
                onCancelChangeState = {
                    stateId.intValue = 4
                    viewModel.onConfirmDialogVisible(visible = true)
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            if (item.request.requestState.id == 3) {
                StatusCard(viewModel = viewModel, id = id)
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }

    when {
        showConfirmDialog == true -> {
            FormAlertDialog(
                onDismissRequest = { viewModel.onConfirmDialogVisible(false) },
                onConfirmation = {
                    viewModel.onConfirmDialogVisible(false)
                    viewModel.onLoadingVisible(visible = true)
                    val state = when (stateId.intValue) {
                        1 -> viewModel.requestStates()[0]
                        2 -> viewModel.requestStates()[1]
                        3 -> viewModel.requestStates()[2]
                        4 -> viewModel.requestStates()[3]
                        else -> viewModel.requestStates()[0]
                    }
                    viewModel.executeUpdateRequest(
                        request = item.copy(
                            request = item.request.copy(
                                date = viewModel.visitLocal.value!!,
                                requestState = state
                            )
                        )
                    )
                    scope.launch {
                        delay(1500)
                        onNavigateBack()
                    }
                },
                dialogTitle = "${viewModel.titles(id = stateId.intValue)}",
                dialogText = "¿Desea continuar con el cambio de estado?",
                icon = Icons.Default.QuestionMark,
                isError = false,
                confirmationText = "Actualizar"
            )
        }
    }

    if (showVisitDateDialog == true) {
        DatePickerWithDialog(viewModel = viewModel, onDismissRequest = {
            viewModel.onVisitDayDialogVisible(visible = false)
        }) {
            viewModel.onVisitDayDialogVisible(visible = false)
            viewModel.onVisitDayTimeDialogVisible(visible = true)
        }
    }

    if (showVisitTimeDateDialog == true) {
        AdvancedTimePickerLauncher(
            viewModel = viewModel, onDismiss = {
                viewModel.onVisitDayTimeDialogVisible(visible = false)
            }, onConfirm = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.e(
                        "DATETIME",
                        viewModel.getVisitDateTime(hour = it.hour, minute = it.minute).toString()
                    )
                    val dateTime = viewModel.getVisitDateTime(hour = it.hour, minute = it.minute)
                    viewModel.onVisitDayChanged(visitDay = "${dateTime.date} ${dateTime.time}")
                    viewModel.onVisitLocalChanged(visitLocal = dateTime)
                }
                viewModel.onVisitDayTimeDialogVisible(visible = false)
            }
        )
    }

    when (update) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = update) {
                viewModel.onLoadingVisible(visible = true)
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = update) {
                if (update is UIState.Success<String>) {
                    viewModel.onLoadingVisible(visible = false)
                    //onNavigateBack()
                }
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = update) {
                messageError = (update as UIState.Error<String>).error.toString()
                viewModel.onLoadingVisible(visible = false)
                viewModel.onErrorDialogVisible(visible = true)
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = update) {
                viewModel.onLoadingVisible(visible = true)
            }
        }
    }

    when (uploadDocument) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = uploadDocument) {
                viewModel.onLoadingVisible(visible = true)
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = uploadDocument) {
                if ((uploadDocument is UIState.Success<Uri>)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        viewModel.registerHistorical(
                            historical = Historical(
                                publishingState = PublishingState(
                                    id = 6,
                                    name = "ARCHIVADO"
                                ),
                                publishing = item.publishing,
                                startDate = System.currentTimeMillis().plus(hourToMillis(hours = 5))
                                    .millisToLocalDateTime().toKotlinLocalDateTime(),
                                finishDate = System.currentTimeMillis()
                                    .plus(hourToMillis(hours = 8765))
                                    .millisToLocalDateTime().toKotlinLocalDateTime(),
                                contract = (uploadDocument as UIState.Success<Uri>).data.toString()
                            )
                        )
                    }
                }
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = uploadDocument) {
                messageError = (uploadDocument as UIState.Error<Uri>).error.toString()
                viewModel.onLoadingVisible(visible = false)
                viewModel.onErrorDialogVisible(visible = true)
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = uploadDocument) {
                viewModel.onLoadingVisible(visible = true)
            }
        }
    }

    when (historical) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = historical) {
                viewModel.onLoadingVisible(visible = true)
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = historical) {
                if ((historical is UIState.Success<String>)) {
                    url.value = (uploadDocument as UIState.Success<Uri>).data.toString()
                    context.launchPDFChooser(url = url.value)
                }
                viewModel.onLoadingVisible(visible = false)
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = historical) {
                messageError = (historical as UIState.Error<String>).error.toString()
                viewModel.onLoadingVisible(visible = false)
                viewModel.onErrorDialogVisible(visible = true)
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = historical) {
                viewModel.onLoadingVisible(visible = true)
            }
        }
    }
}

@Composable
fun ContactBar(viewModel: MainViewModel, id: Int) {
    val item = viewModel.requests.value!!.first { it.request.id == id }
    val context = LocalContext.current
    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedIconButton(
            onClick = {
                context.callProprietor(
                    viewModel = viewModel,
                    id = item.request.user.id
                )
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Phone,
                contentDescription = "",
            )
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1.0.toFloat()),
            onClick = {
                context.sendWhatsAppsProprietor(
                    viewModel = viewModel,
                    id = item.request.user.id,
                    message = ""
                )
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Whatsapp,
                contentDescription = "",
            )
            Text(modifier = Modifier.padding(start = 4.dp), text = "WhatsApp")
        }
        OutlinedIconButton(
            onClick = {
                context.launchEmailChooser(email = item.request.user.email, title = "", text = "")
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "",
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun State(viewModel: MainViewModel, request: RequestHasPublishing) {
    val state by viewModel.requestState.observeAsState(initial = request.request.requestState.name)
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = state,
            onValueChange = {},
            readOnly = true,
            label = { Text("Estado") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 16.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.requestStates().forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = item.name,
                                tint = viewModel.colors(id = item.id)
                            )
                            Text(text = item.name, modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    },
                    onClick = {
                        viewModel.onRequestStateChanged(item.name)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SliderSample(viewModel: MainViewModel, id: Int) {
    val item = viewModel.requests.value!!.first { it.request.id == id }
    val stateId = item.request.requestState.id
    //val stateId = 4
    var sliderPosition by remember { mutableFloatStateOf(stateId.toFloat()) }
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Card {
            Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)) {
                Text(
                    text = "PASO: ",
                    modifier = Modifier.padding(top = 8.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Box {
                    Text(
                        text = "${sliderPosition.roundToInt()} ",
                        color = viewModel.colors(id = stateId),
                        //color = viewModel.colors(4),
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.CropSquare,
                        contentDescription = "",
                        tint = viewModel.colors(id = stateId),
                        //tint = viewModel.colors(4),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Slider(
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..3f,
            onValueChangeFinished = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            // Only allow multiples of 10. Excluding the endpoints of `valueRange`,
            // there are 9 steps (10, 20, ..., 90).
            steps = 3,
            colors = SliderDefaults.colors().copy(
                disabledActiveTrackColor = MaterialTheme.colorScheme.secondary,
                disabledThumbColor = MaterialTheme.colorScheme.primary
            ),
            enabled = false
        )
    }
}

@Composable
fun StateCard(
    viewModel: MainViewModel,
    id: Int,
    onConfirmChangeState: () -> Unit,
    onCancelChangeState: () -> Unit
) {
    val item = viewModel.requests.value!!.first { it.request.id == id }
    Card(Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = viewModel.titles(id = item.request.requestState.id),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = when (item.request.requestState.id) {
                    3 -> Color.Green
                    4 -> Color.Red
                    else -> Color.Unspecified
                }
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = if (item.request.requestState.id == 3) "¡El inmueble a sido arrendado exitosamente!\n " +
                        "Opcionalmente adjunte el contrato de arrendamiento del cliente como evidencia"
                else "Considere contactar al cliente antes de confirmar el siguiente paso",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            if (item.request.requestState.id != 3) {
                Button(
                    onClick = onConfirmChangeState
                ) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = if (item.request.requestState.id == 4) "Reiniciar" else "Confirmar"
                    )
                }
                TextButton(
                    onClick = onCancelChangeState
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun StatusCard(
    viewModel: MainViewModel,
    id: Int
) {
    val item = viewModel.requests.value!!.first { it.request.id == id }
    Card(Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Resumen del contrato",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                /*color = when (item.request.requestState.id) {
                    3 -> Color.Green
                    4 -> Color.Red
                    else -> Color.Unspecified
                }*/
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Text(
                    text = "Fecha de inicio: ${
                        System.currentTimeMillis().plus(hourToMillis(hours = 5))
                            .millisToLocalDateTime()
                            .toKotlinLocalDateTime().toString().replace("T", " ")
                    }\nFecha de finalizacion: ${
                        System.currentTimeMillis()
                            .plus(hourToMillis(hours = 8765))
                            .millisToLocalDateTime().toKotlinLocalDateTime().toString()
                            .replace("T", " ")
                    }\nPrecio final: S/2800\nMantenimiento: 500",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun TitleBar(viewModel: MainViewModel, id: Int, onNavigateBack: () -> Unit) {
    val item = viewModel.requests.value!!.first { it.request.id == id }
    Row {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = ""
            )
        }
        Text(
            //text = item.request.requestType.name,
            text = "${item.publishing.property.address}, ${item.publishing.property.district.name}",
            modifier = Modifier.padding(start = 8.dp, top = 10.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}