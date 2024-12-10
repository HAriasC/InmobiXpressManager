package com.inmobixpress.inmobixpressmanager.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AddLocation
import androidx.compose.material.icons.outlined.AttachEmail
import androidx.compose.material.icons.outlined.Bathtub
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Elevator
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HomeWork
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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.chillibits.composenumberpicker.PickerButton
import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.data.network.implement.PropertyServiceImpl
import com.inmobixpress.inmobixpressmanager.data.network.model.Country
import com.inmobixpress.inmobixpressmanager.data.network.model.Department
import com.inmobixpress.inmobixpressmanager.data.network.model.District
import com.inmobixpress.inmobixpressmanager.data.network.model.OfferType
import com.inmobixpress.inmobixpressmanager.data.network.model.Property
import com.inmobixpress.inmobixpressmanager.data.network.model.PropertyState
import com.inmobixpress.inmobixpressmanager.data.network.model.PropertyType
import com.inmobixpress.inmobixpressmanager.data.network.model.Province
import com.inmobixpress.inmobixpressmanager.repository.PropertyRepository
import com.inmobixpress.inmobixpressmanager.ui.components.LoadingScreen
import com.inmobixpress.inmobixpressmanager.ui.components.MessageDialog
import com.inmobixpress.inmobixpressmanager.ui.model.UIState
import com.inmobixpress.inmobixpressmanager.ui.viewmodel.MainViewModel
import io.ktor.client.HttpClient

@Composable
fun PropertyRegistrationScreen(viewModel: MainViewModel) {
    val showLoading by viewModel.loadingVisible.observeAsState(initial = true)
    val showErrorDialog by viewModel.errorDialogVisible.observeAsState(initial = false)
    val showCompleteDialog by viewModel.completeDialogVisible.observeAsState(initial = false)
    var messageError by rememberSaveable { mutableStateOf("") }
    val properties by viewModel.properties.collectAsState()
    val offerTypes by viewModel.offerTypes.collectAsState()
    val propertyTypes by viewModel.propertyTypes.collectAsState()
    val propertyStates by viewModel.propertyStates.collectAsState()
    val countries by viewModel.countries.collectAsState()
    val departments by viewModel.departments.collectAsState()
    val provinces by viewModel.provinces.collectAsState()
    val districts by viewModel.districts.collectAsState()
    val insert by viewModel.insert.collectAsState()
    val insertComplex by viewModel.insertComplex.collectAsState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val scrollState = rememberScrollState()
    Surface {
        Scaffold(
            floatingActionButton = {
                Row {
                    ExtendedFloatingActionButton(
                        text = { Text("Registrar") },
                        onClick = {
                            viewModel.executeRegister()
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = "Eliminar",
                                modifier = Modifier.size(size = 32.dp)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    ExtendedFloatingActionButton(
                        text = { Text("Eliminar") },
                        onClick = { /* Tus acciones */ },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = "Eliminar",
                                modifier = Modifier.size(size = 32.dp)
                            )
                        }
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            Surface {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .imePadding()
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)
                            )
                            Properties(viewModel = viewModel)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        HorizontalDivider()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Title,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
                            )
                            Title(viewModel = viewModel) {
                                focusManager.moveFocus(FocusDirection.Next)
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
                            }
                        }
                        Row {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Bathtub,
                                    contentDescription = "",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 2.dp
                                    )
                                )
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text(
                                        text = "Nª Baños",
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontSize = 12.sp
                                    )
                                    NumberPicker(min = 1, max = 5) { value ->
                                        viewModel.onNBathroomChanged(nBathroom = value.toString())
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Bed,
                                    contentDescription = "",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 2.dp
                                    )
                                )
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text(
                                        text = "Nª Dormitorios",
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontSize = 12.sp
                                    )
                                    NumberPicker(min = 1, max = 5) { value ->
                                        viewModel.onNBedroomChanged(nBedroom = value.toString())
                                    }
                                }
                            }
                        }
                        Row {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.DirectionsCar,
                                    contentDescription = "",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 2.dp
                                    )
                                )
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text(
                                        text = "Nª Estacionamientos",
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontSize = 12.sp
                                    )
                                    NumberPicker(min = 0, max = 5) { value ->
                                        viewModel.onNGarageChanged(nGarage = value.toString())
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Elevator,
                                    contentDescription = "",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 2.dp
                                    )
                                )
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text(
                                        text = "Nª Piso",
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontSize = 12.sp
                                    )
                                    NumberPicker(min = 1, max = 60) { value ->
                                        viewModel.onNFloorChanged(nFloor = value.toString())
                                    }
                                }
                            }
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.SquareFoot,
                                    contentDescription = "",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 2.dp
                                    )
                                )
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text(
                                        text = "Area total",
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontSize = 12.sp
                                    )
                                    NumberPicker(min = 10, max = 1500, default = 80) { value ->
                                        viewModel.onTotalAreaChanged(totalArea = value.toString())
                                    }
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.SquareFoot,
                                    contentDescription = "",
                                    modifier = Modifier.padding(
                                        start = 8.dp,
                                        end = 8.dp,
                                        bottom = 2.dp
                                    )
                                )
                                Column(horizontalAlignment = CenterHorizontally) {
                                    Text(
                                        text = "Area construida",
                                        modifier = Modifier.padding(horizontal = 4.dp),
                                        fontSize = 12.sp
                                    )
                                    NumberPicker(min = 10, max = 1500, default = 80) { value ->
                                        viewModel.onBuiltAreaChanged(builtArea = value.toString())
                                    }
                                }
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
                        HorizontalDivider()
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
                                }
                                OfferTypeSale(viewModel = viewModel)
                                PriceSale(viewModel = viewModel) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        HorizontalDivider()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.HomeWork,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                            )
                            PropertyType(viewModel = viewModel)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        HorizontalDivider()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.LocalOffer,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                            )
                            PropertyState(viewModel = viewModel)
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        HorizontalDivider()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.AddLocation,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                            )
                            Column {
                                Latitude(viewModel = viewModel) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                                Longitude(viewModel = viewModel) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                                Altitude(viewModel = viewModel) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                                AltitudeBase(viewModel = viewModel) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                }
                                Row {
                                    ExtendedFloatingActionButton(
                                        text = { Text("Google Maps") },
                                        onClick = { /* Tus acciones */ },
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
                                    Spacer(modifier = Modifier.size(16.dp))
                                    ExtendedFloatingActionButton(
                                        text = { Text("ARCore") },
                                        onClick = { /* Tus acciones */ },
                                        icon = {
                                            Image(
                                                imageVector = ImageVector.vectorResource(
                                                    R.drawable.logo_ar
                                                ),
                                                contentDescription = "Realidad aumentada",
                                                modifier = Modifier.size(size = 32.dp)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                        HorizontalDivider()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Map,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                            )
                            Country(viewModel = viewModel)
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
                            Department(viewModel = viewModel)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Map,
                                contentDescription = "",
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
                            )
                            District(viewModel = viewModel)
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }

        AnimatedVisibility(
            visible = showLoading,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
        ) {
            LoadingScreen(message = "Cargando...")
        }

        when {
            showErrorDialog -> {
                MessageDialog(
                    onDismissRequest = {
                        viewModel.onErrorDialogVisible(visible = false)
                    },
                    onConfirmation = {
                        viewModel.onErrorDialogVisible(visible = false)
                    },
                    dialogTitle = "Lo sentimos, ocurrió un error",
                    dialogText = messageError,
                    icon = Icons.Default.Warning,
                    isError = true,
                    confirmationText = "Entendido"
                )
            }

            showCompleteDialog -> {
                MessageDialog(
                    onDismissRequest = {
                        viewModel.loadProperties()
                        viewModel.onCompleteDialogVisible(visible = false)
                    },
                    onConfirmation = {
                        viewModel.loadProperties()
                        viewModel.onCompleteDialogVisible(visible = false)
                    },
                    dialogTitle = "¡La propiedad fue registrada correctamente!",
                    dialogText = "",
                    icon = Icons.Default.DoneOutline,
                    isError = true,
                    confirmationText = "Entendido"
                )
            }
        }

        when (properties) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = properties) {
                    Log.e("LOAD", "loading")
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = properties) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.loadOfferTypes()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = properties) {
                    messageError = (properties as UIState.Error<List<Property>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = properties) {
                    Log.e("NONE", "loading")
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (offerTypes) {
            is UIState.Loading -> {
                Log.e("LOAD", "loading")
                LaunchedEffect(key1 = offerTypes) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = offerTypes) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.loadPropertyTypes()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = offerTypes) {
                    messageError = (offerTypes as UIState.Error<List<OfferType>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = offerTypes) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (propertyTypes) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = propertyTypes) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = propertyTypes) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.onPropertyTypeListChanged(
                        propertyTypeList = (propertyTypes as UIState.Success<List<PropertyType>>).data
                    )
                    viewModel.loadPropertyStates()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = propertyTypes) {
                    messageError =
                        (propertyTypes as UIState.Error<List<PropertyType>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = propertyTypes) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (propertyStates) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = propertyStates) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = propertyStates) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.onPropertyStateListChanged(
                        propertyStateList = (propertyStates as UIState.Success<List<PropertyState>>).data
                    )
                    viewModel.loadCountries()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = propertyStates) {
                    messageError =
                        (propertyStates as UIState.Error<List<PropertyState>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = propertyStates) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (countries) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = countries) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = countries) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.loadDepartments()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = countries) {
                    messageError = (countries as UIState.Error<List<Country>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = countries) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (departments) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = departments) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = departments) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.loadProvinces()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = departments) {
                    messageError = (departments as UIState.Error<List<Department>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = departments) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (provinces) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = provinces) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = provinces) {
                    //viewModel.onLoadingVisible(visible = false)
                    viewModel.loadDistricts()
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = provinces) {
                    messageError = (provinces as UIState.Error<List<Province>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = provinces) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (districts) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = districts) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = districts) {
                    viewModel.onDistrictListChanged(
                        districtList = (districts as UIState.Success<List<District>>).data
                    )
                    viewModel.onLoadingVisible(visible = false)
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = districts) {
                    messageError = (districts as UIState.Error<List<District>>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = districts) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (insert) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = insert) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = insert) {
                    viewModel.executeRegisterComplex(
                        id = (insert as UIState.Success<String>).data.split("|id:")[1].toInt()
                    )
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = insert) {
                    messageError = (insert as UIState.Error<String>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = insert) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }

        when (insertComplex) {
            is UIState.Loading -> {
                LaunchedEffect(key1 = insertComplex) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }

            is UIState.Success -> {
                LaunchedEffect(key1 = insertComplex) {
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onCompleteDialogVisible(visible = true)
                }
            }

            is UIState.Error -> {
                LaunchedEffect(key1 = insertComplex) {
                    messageError = (insertComplex as UIState.Error<String>).error.toString()
                    viewModel.onLoadingVisible(visible = false)
                    viewModel.onErrorDialogVisible(visible = true)
                }
            }

            is UIState.None -> {
                LaunchedEffect(key1 = insertComplex) {
                    viewModel.onLoadingVisible(visible = true)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Properties(viewModel: MainViewModel) {
    val properties by viewModel.properties.collectAsState()
    val property by viewModel.propertyItem.observeAsState(initial = "---")
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = property,
            onValueChange = {},
            readOnly = true,
            label = { Text("Lista de propiedades") },
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
            val list = mutableListOf("---")
            (properties as UIState.Success<List<Property>>).data.forEach { item ->
                val value = "${item.district.name} - ${item.address}"
                list.add(value)
            }
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        viewModel.onPropertyItemChanged(item)
                        expanded = false
                    }
                )
            }
        }
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
fun NumberPicker(min: Int, max: Int, default: Int = min, onValueChange: (Int) -> Unit) {
    val number = remember { mutableIntStateOf(default) }
    Row {
        PickerButton(
            size = 45.dp,
            drawable = R.drawable.ic_remove,
            enabled = number.intValue > min,
            onClick = {
                if (number.intValue > min) number.intValue--
                onValueChange(number.intValue)
            }
        )
        Text(
            text = number.intValue.toString(),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
                .width(IntrinsicSize.Max)
                .align(Alignment.CenterVertically)
        )
        PickerButton(
            size = 45.dp,
            drawable = R.drawable.ic_add,
            enabled = number.intValue < max,
            onClick = {
                if (number.intValue < max) number.intValue += 1;
                onValueChange(number.intValue)
            }
        )

    }
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
    val offerTypes by viewModel.offerTypes.collectAsState()
    val offerType by viewModel.offerType.observeAsState(
        initial = "Alquiler"
    )
    var expanded by remember { mutableStateOf(false) }
    when (offerTypes) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = offerTypes) {
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = offerTypes) {
                viewModel.onOfferTypeItemChanged(
                    offerType = (offerTypes as UIState.Success<List<OfferType>>).data[0]
                )
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = offerTypes) {
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = offerTypes) {
            }
        }
    }
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
            val item = (offerTypes as UIState.Success<List<OfferType>>).data[0]
            DropdownMenuItem(
                text = { Text(text = item.name) },
                onClick = {
                    viewModel.onOfferTypeItemChanged(offerType = item)
                    viewModel.onOfferTypeChanged(item.name)
                    expanded = false
                }
            )
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
        label = { Text("Precio de alquiler") },
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
fun OfferTypeSale(viewModel: MainViewModel) {
    val offerTypes by viewModel.offerTypes.collectAsState()
    val offerType by viewModel.offerTypeSale.observeAsState(
        initial = "Venta"
    )
    var expanded by remember { mutableStateOf(false) }
    when (offerTypes) {
        is UIState.Loading -> {
            LaunchedEffect(key1 = offerTypes) {
            }
        }

        is UIState.Success -> {
            LaunchedEffect(key1 = offerTypes) {
                viewModel.onOfferTypeSaleItemChanged(
                    offerType = (offerTypes as UIState.Success<List<OfferType>>).data[1]
                )
            }
        }

        is UIState.Error -> {
            LaunchedEffect(key1 = offerTypes) {
            }
        }

        is UIState.None -> {
            LaunchedEffect(key1 = offerTypes) {
            }
        }
    }
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
            val item = (offerTypes as UIState.Success<List<OfferType>>).data[1]
            DropdownMenuItem(
                text = { Text(text = item.name) },
                onClick = {
                    viewModel.onOfferTypeSaleItemChanged(offerType = item)
                    viewModel.onOfferTypeSaleChanged(item.name)
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun PriceSale(viewModel: MainViewModel, onFocusChanged: () -> Unit) {
    val price by viewModel.priceSale.observeAsState(initial = "")
    val priceError by viewModel.priceSaleError.observeAsState(initial = false)
    val priceMessageError by viewModel.priceSaleMessageError.observeAsState(initial = "")
    OutlinedTextField(
        value = price,
        onValueChange = {
            viewModel.onPriceSaleChanged(it)
            viewModel.validatePriceSale()
        },
        singleLine = true,
        label = { Text("Precio de venta") },
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
            viewModel.validatePriceSale()
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
fun PropertyType(viewModel: MainViewModel) {
    val propertyTypes by viewModel.propertyTypes.collectAsState()
    val propertyType by viewModel.propertyType.observeAsState(
        initial = "---"
    )
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = propertyType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de inmueble") },
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
            (propertyTypes as UIState.Success<List<PropertyType>>).data.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        viewModel.onPropertyTypeChanged(item.name)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyState(viewModel: MainViewModel) {
    val propertyStates by viewModel.propertyStates.collectAsState()
    val propertyState by viewModel.propertyState.observeAsState(
        initial = "---"
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
            (propertyStates as UIState.Success<List<PropertyState>>).data.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        viewModel.onPropertyStateChanged(item.name)
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
    val districts by viewModel.districts.collectAsState()
    val district by viewModel.district.observeAsState(
        initial = "Miraflores"
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
            (districts as UIState.Success<List<District>>).data.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        viewModel.onDistrictChanged(item.name)
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
    val provinces by viewModel.provinces.collectAsState()
    val province by viewModel.province.observeAsState(
        initial = "Lima"
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
            (provinces as UIState.Success<List<Province>>).data.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        viewModel.onProvinceChanged(item.name)
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
    val departments by viewModel.departments.collectAsState()
    val department by viewModel.department.observeAsState(
        initial = "Lima"
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
            (departments as UIState.Success<List<Department>>).data.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        viewModel.onDepartmentChanged(item.name)
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
    val countries by viewModel.countries.collectAsState()
    val country by viewModel.country.observeAsState(
        initial = "Perú"
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
            (countries as UIState.Success<List<Country>>).data.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        viewModel.onCountryChanged(item.name)
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
    PropertyRegistrationScreen(
        viewModel = MainViewModel(
            PropertyRepository(
                PropertyServiceImpl(
                    HttpClient()
                )
            )
        )
    )
}