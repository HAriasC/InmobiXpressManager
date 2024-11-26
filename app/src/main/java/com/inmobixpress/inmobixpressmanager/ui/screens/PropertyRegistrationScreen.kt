package com.inmobixpress.inmobixpressmanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material.icons.outlined.AttachEmail
import androidx.compose.material.icons.outlined.Bathtub
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Elevator
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.PriceCheck
import androidx.compose.material.icons.outlined.SquareFoot
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chillibits.composenumberpicker.HorizontalNumberPicker
import com.chillibits.composenumberpicker.PickerButton
import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun PropertyRegistrationScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Title,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                Title(viewModel = viewModel) {
                    focusManager.moveFocus(FocusDirection.Next)
                    scope.launch {
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                Description(viewModel = viewModel) {
                    focusManager.moveFocus(FocusDirection.Next)
                    scope.launch {
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.WaterDrop,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                Maintenance(viewModel = viewModel) {
                    focusManager.moveFocus(FocusDirection.Next)
                    scope.launch {
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                Address(viewModel = viewModel) {
                    focusManager.moveFocus(FocusDirection.Next)
                    scope.launch {
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AttachEmail,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                PostalCode(viewModel = viewModel) {
                    focusManager.moveFocus(FocusDirection.Next)
                    scope.launch {
                        scrollState.scrollTo(scrollState.maxValue)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Bathtub,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                Text(text = "Nª Baños", modifier = Modifier.padding(horizontal = 4.dp))
                NumberPicker(min = 1, max = 5) { value ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Bed,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                NumberPicker(min = 1, max = 5) { value ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Bed,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                NumberPicker(min = 0, max = 4) { value ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)
                )
                Antique(viewModel = viewModel)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Elevator,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                NumberPicker(min = 0, max = 4) { value ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.SquareFoot,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                NumberPicker(min = 0, max = 4) { value ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.SquareFoot,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                )
                NumberPicker(min = 0, max = 4) { value ->

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.PriceCheck,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)
                )
                Column {
                    OfferType(viewModel = viewModel)
                    Price(viewModel = viewModel) {
                        focusManager.moveFocus(FocusDirection.Next)
                        scope.launch {
                            scrollState.scrollTo(scrollState.maxValue)
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocalOffer,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                )
                PropertyState(viewModel = viewModel)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AddLocation,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                )
                Column {
                    Row {
                        Latitude(viewModel = viewModel) {
                            focusManager.moveFocus(FocusDirection.Next)
                            scope.launch {
                                scrollState.scrollTo(scrollState.maxValue)
                            }
                        }
                        Longitude(viewModel = viewModel) {
                            focusManager.moveFocus(FocusDirection.Next)
                            scope.launch {
                                scrollState.scrollTo(scrollState.maxValue)
                            }
                        }
                        ExtendedFloatingActionButton(
                            text = { Text("ARCore") },
                            onClick = { /* Tus acciones */ },
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.logo_ar),
                                    contentDescription = "Realidad aumentada"
                                )
                            })
                    }
                    Row {
                        Altitude(viewModel = viewModel) {
                            focusManager.moveFocus(FocusDirection.Next)
                            scope.launch {
                                scrollState.scrollTo(scrollState.maxValue)
                            }
                        }
                        AltitudeBase(viewModel = viewModel) {
                            focusManager.moveFocus(FocusDirection.Next)
                            scope.launch {
                                scrollState.scrollTo(scrollState.maxValue)
                            }
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Map,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                )
                District(viewModel = viewModel)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Map,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                )
                Department(viewModel = viewModel)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Map,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                )
                Province(viewModel = viewModel)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Map,
                    contentDescription = "",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                )
                Country(viewModel = viewModel)
            }
        }
        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
fun Title(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val title by viewModel.title.observeAsState(initial = "")
    val nameError by viewModel.titleError.observeAsState(initial = false)
    val nameMessageError by viewModel.titleMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = title,
        onValueChange = {
            viewModel.onTitleChanged(it)
            viewModel.validateTitle()
        },
        singleLine = true,
        label = { Text("Titulo") },
        supportingText = {
            Row {
                Text(
                    text = if (nameError) nameMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = nameError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateTitle()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (nameError) error(message = nameMessageError)
            }
    )
}

@Composable
fun Description(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val description by viewModel.description.observeAsState(initial = "")
    val descriptionError by viewModel.descriptionError.observeAsState(initial = false)
    val descriptionMessageError by viewModel.descriptionMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = description,
        onValueChange = {
            viewModel.onDescriptionChanged(it)
            viewModel.validateDescription()
        },
        singleLine = true,
        label = { Text("Descripción") },
        supportingText = {
            Row {
                Text(
                    text = if (descriptionError) descriptionMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = descriptionError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateDescription()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (descriptionError) error(message = descriptionMessageError)
            }
    )
}

@Composable
fun Maintenance(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val maintenance by viewModel.maintenance.observeAsState(initial = "")
    val maintenanceError by viewModel.maintenanceError.observeAsState(initial = false)
    val maintenanceMessageError by viewModel.maintenanceMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = maintenance,
        onValueChange = {
            viewModel.onMaintenanceChanged(it)
            viewModel.validateMaintenance()
        },
        singleLine = true,
        label = { Text("Mantenimiento") },
        supportingText = {
            Row {
                Text(
                    text = if (maintenanceError) maintenanceMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = maintenanceError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateMaintenance()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (maintenanceError) error(message = maintenanceMessageError)
            }
    )
}

@Composable
fun Address(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val address by viewModel.address.observeAsState(initial = "")
    val addressError by viewModel.addressError.observeAsState(initial = false)
    val addressMessageError by viewModel.addressMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = address,
        onValueChange = {
            viewModel.onAddressChanged(it)
            viewModel.validateAddress()
        },
        singleLine = true,
        label = { Text("Dirección") },
        supportingText = {
            Row {
                Text(
                    text = if (addressError) addressMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = addressError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateAddress()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (addressError) error(message = addressMessageError)
            }
    )
}

@Composable
fun PostalCode(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val postal by viewModel.postal.observeAsState(initial = "")
    val postalError by viewModel.postalError.observeAsState(initial = false)
    val postalMessageError by viewModel.postalMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = postal,
        onValueChange = {
            viewModel.onPostalChanged(it)
            viewModel.validatePostal()
        },
        singleLine = true,
        label = { Text("Código postal") },
        supportingText = {
            Row {
                Text(
                    text = if (postalError) postalMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = postalError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validatePostal()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (postalError) error(message = postalMessageError)
            }
    )
}

@Composable
fun NumberPicker(min: Int, max: Int, default: Int = 0, onValueChange: (Int) -> Unit) {
    val number = remember { mutableStateOf(default) }
    Column {
        PickerButton(
            size = 45.dp,
            drawable = R.drawable.ic_minus_icon,
            enabled = number.value < max,
            onClick = {
                if (number.value < max) number.value=number.value+1;
                onValueChange(number.value)
            }
        )
        Text(
            text = number.value.toString(),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
                .width(IntrinsicSize.Max)
                .align(CenterHorizontally)
        )
        PickerButton(
            size = 45.dp,
            drawable = R.drawable.ic_minus_icon,
            enabled = number.value > min,
            onClick = {
                if (number.value > min) number.value--
                onValueChange(number.value)
            }
        )
    }
    HorizontalNumberPicker(
        min = min,
        max = max,
        default = default,
        modifier = Modifier.padding(10.dp),
        onValueChange = onValueChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Antique(viewModel: MainViewModel) {
    val antique by viewModel.antique.observeAsState(initial = viewModel.years().last())
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = antique,
            onValueChange = {},
            readOnly = true,
            label = { Text("Antiguedad") },
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
            viewModel.years().forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onAntiqueChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferType(viewModel: MainViewModel) {
    val offerType by viewModel.offerType.observeAsState(
        initial = viewModel.offerTypes.value?.get(0) ?: ""
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = offerType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de oferta") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.offerTypes.value?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onOfferTypeChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Price(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val price by viewModel.price.observeAsState(initial = "")
    val priceError by viewModel.priceError.observeAsState(initial = false)
    val priceMessageError by viewModel.priceMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = price,
        onValueChange = {
            viewModel.onPriceChanged(it)
            viewModel.validatePrice()
        },
        singleLine = true,
        label = { Text("Precio") },
        supportingText = {
            Row {
                Text(
                    text = if (priceError) priceMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = priceError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validatePrice()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (priceError) error(message = priceMessageError)
            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyState(viewModel: MainViewModel) {
    val propertyState by viewModel.propertyState.observeAsState(
        initial = viewModel.propertyStates.value?.get(0) ?: ""
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = propertyState,
            onValueChange = {},
            readOnly = true,
            label = { Text("Estado de inmueble") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.offerTypes.value?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onPropertyStateChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Latitude(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val latitude by viewModel.latitude.observeAsState(initial = "")
    val latitudeError by viewModel.latitudeError.observeAsState(initial = false)
    val latitudeMessageError by viewModel.latitudeMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = latitude,
        onValueChange = {
            viewModel.onLatitudeChanged(it)
            viewModel.validateLatitude()
        },
        singleLine = true,
        label = { Text("Latitud") },
        supportingText = {
            Row {
                Text(
                    text = if (latitudeError) latitudeMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = latitudeError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateLatitude()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (latitudeError) error(message = latitudeMessageError)
            }
    )
}

@Composable
fun Longitude(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val longitude by viewModel.longitude.observeAsState(initial = "")
    val longitudeError by viewModel.longitudeError.observeAsState(initial = false)
    val longitudeMessageError by viewModel.longitudeMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = longitude,
        onValueChange = {
            viewModel.onLongitudeChanged(it)
            viewModel.validateLongitude()
        },
        singleLine = true,
        label = { Text("Longitud") },
        supportingText = {
            Row {
                Text(
                    text = if (longitudeError) longitudeMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = longitudeError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateLongitude()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (longitudeError) error(message = longitudeMessageError)
            }
    )
}

@Composable
fun Altitude(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val altitude by viewModel.altitude.observeAsState(initial = "")
    val altitudeError by viewModel.altitudeError.observeAsState(initial = false)
    val altitudeMessageError by viewModel.altitudeMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = altitude,
        onValueChange = {
            viewModel.onAltitudeChanged(it)
            viewModel.validateAltitude()
        },
        singleLine = true,
        label = { Text("Altitud") },
        supportingText = {
            Row {
                Text(
                    text = if (altitudeError) altitudeMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = altitudeError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateAltitude()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (altitudeError) error(message = altitudeMessageError)
            }
    )
}

@Composable
fun AltitudeBase(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val altitudeBase by viewModel.altitudeBase.observeAsState(initial = "")
    val altitudeBaseError by viewModel.altitudeBaseError.observeAsState(initial = false)
    val altitudeBaseMessageError by viewModel.altitudeBaseMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = altitudeBase,
        onValueChange = {
            viewModel.onAltitudeBaseChanged(it)
            viewModel.validateAltitudeBase()
        },
        singleLine = true,
        label = { Text("Altitud base") },
        supportingText = {
            Row {
                Text(
                    text = if (altitudeBaseError) altitudeBaseMessageError else "",
                    modifier = Modifier.clearAndSetSemantics { }
                )
            }
        },
        isError = altitudeBaseError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions {
            viewModel.validateAltitudeBase()
            onFocusChanged.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)
            .semantics {
                if (altitudeBaseError) error(message = altitudeBaseMessageError)
            }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun District(viewModel: MainViewModel) {
    val district by viewModel.district.observeAsState(
        initial = viewModel.districts.value?.get(0) ?: ""
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = district,
            onValueChange = {},
            readOnly = true,
            label = { Text("Distrito") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.districts.value?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onDistrictChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Province(viewModel: MainViewModel) {
    val province by viewModel.province.observeAsState(
        initial = viewModel.provinces.value?.get(0) ?: ""
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = province,
            onValueChange = {},
            readOnly = true,
            label = { Text("Provincia") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.provinces.value?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onProvinceChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Department(viewModel: MainViewModel) {
    val department by viewModel.department.observeAsState(
        initial = viewModel.departments.value?.get(0) ?: ""
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = department,
            onValueChange = {},
            readOnly = true,
            label = { Text("Departamento") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.departments.value?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onDepartmentChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Country(viewModel: MainViewModel) {
    val country by viewModel.country.observeAsState(
        initial = viewModel.countries.value?.get(0) ?: ""
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = country,
            onValueChange = {},
            readOnly = true,
            label = { Text("Pais") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .padding(top = 16.dp, end = 8.dp, bottom = 8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            viewModel.countries.value?.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onCountryChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PropertyRegistrationScreenPreview() {
    PropertyRegistrationScreen(viewModel = MainViewModel())
}