package com.inmobixpress.inmobixpressmanager.ui.viewmodel

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.PlacesClient
import com.inmobixpress.inmobixpressmanager.data.network.model.Country
import com.inmobixpress.inmobixpressmanager.data.network.model.Department
import com.inmobixpress.inmobixpressmanager.data.network.model.Device
import com.inmobixpress.inmobixpressmanager.data.network.model.District
import com.inmobixpress.inmobixpressmanager.data.network.model.Historical
import com.inmobixpress.inmobixpressmanager.data.network.model.Image
import com.inmobixpress.inmobixpressmanager.data.network.model.Location
import com.inmobixpress.inmobixpressmanager.data.network.model.OfferType
import com.inmobixpress.inmobixpressmanager.data.network.model.Property
import com.inmobixpress.inmobixpressmanager.data.network.model.PropertyHasOfferType
import com.inmobixpress.inmobixpressmanager.data.network.model.PropertyState
import com.inmobixpress.inmobixpressmanager.data.network.model.PropertyType
import com.inmobixpress.inmobixpressmanager.data.network.model.Province
import com.inmobixpress.inmobixpressmanager.data.network.model.Publishing
import com.inmobixpress.inmobixpressmanager.data.network.model.Request
import com.inmobixpress.inmobixpressmanager.data.network.model.RequestHasPublishing
import com.inmobixpress.inmobixpressmanager.data.network.model.RequestState
import com.inmobixpress.inmobixpressmanager.data.network.model.User
import com.inmobixpress.inmobixpressmanager.repository.PropertyRepository
import com.inmobixpress.inmobixpressmanager.ui.model.ServiceMarker
import com.inmobixpress.inmobixpressmanager.ui.model.UIState
import com.inmobixpress.inmobixpressmanager.ui.model.UIState.Error
import com.inmobixpress.inmobixpressmanager.ui.model.UIState.Loading
import com.inmobixpress.inmobixpressmanager.ui.model.UIState.None
import com.inmobixpress.inmobixpressmanager.ui.model.AutocompleteResult
import com.inmobixpress.inmobixpressmanager.ui.utils.autocompleteAddress
import com.inmobixpress.inmobixpressmanager.ui.utils.getCoordinates
import com.inmobixpress.inmobixpressmanager.ui.utils.millisToLocalDateTime
import com.inmobixpress.inmobixpressmanager.ui.utils.timeToMillis
import com.inmobixpress.inmobixpressmanager.ui.utils.today
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PropertyRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _loadingVisible = MutableLiveData<Boolean>()
    val loadingVisible: LiveData<Boolean> = _loadingVisible

    private val _errorDialogVisible = MutableLiveData<Boolean>()
    val errorDialogVisible: LiveData<Boolean> = _errorDialogVisible

    private val _completeDialogVisible = MutableLiveData<Boolean>()
    val completeDialogVisible: LiveData<Boolean> = _completeDialogVisible

    private val _searchAddressBottomSheetVisible = MutableLiveData<Boolean>()
    val searchAddressBottomSheetVisible: LiveData<Boolean> = _searchAddressBottomSheetVisible

    private val _inboxDetailBottomSheetVisible = MutableLiveData<Boolean>()
    val inboxDetailBottomSheetVisible: LiveData<Boolean> = _inboxDetailBottomSheetVisible

    private val _visitDayDialogVisible = MutableLiveData<Boolean>()
    val visitDayDialogVisible: LiveData<Boolean> = _visitDayDialogVisible

    private val _visitDayTimeDialogVisible = MutableLiveData<Boolean>()
    val visitDayTimeDialogVisible: LiveData<Boolean> = _visitDayTimeDialogVisible

    private val _confirmDialogVisible = MutableLiveData<Boolean>()
    val confirmDialogVisible: LiveData<Boolean> = _confirmDialogVisible

    private val _propertyItem = MutableLiveData<String>()
    val propertyItem: LiveData<String> = _propertyItem

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _titleError = MutableLiveData<Boolean>()
    val titleError: LiveData<Boolean> = _titleError

    private val _titleMessageError = MutableLiveData<String>()
    val titleMessageError: LiveData<String> = _titleMessageError

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _descriptionError = MutableLiveData<Boolean>()
    val descriptionError: LiveData<Boolean> = _descriptionError

    private val _descriptionMessageError = MutableLiveData<String>()
    val descriptionMessageError: LiveData<String> = _descriptionMessageError

    private val _maintenance = MutableLiveData<String>()
    val maintenance: LiveData<String> = _maintenance

    private val _maintenanceError = MutableLiveData<Boolean>()
    val maintenanceError: LiveData<Boolean> = _maintenanceError

    private val _maintenanceMessageError = MutableLiveData<String>()
    val maintenanceMessageError: LiveData<String> = _maintenanceMessageError

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _addressError = MutableLiveData<Boolean>()
    val addressError: LiveData<Boolean> = _addressError

    private val _addressMessageError = MutableLiveData<String>()
    val addressMessageError: LiveData<String> = _addressMessageError

    private val _postal = MutableLiveData<String>()
    val postal: LiveData<String> = _postal

    private val _nBedroom = MutableLiveData("1")
    val nBedroom: LiveData<String> = _nBedroom

    private val _nBathroom = MutableLiveData("1")
    val nBathroom: LiveData<String> = _nBathroom

    private val _nGarage = MutableLiveData("0")
    val nGarage: LiveData<String> = _nGarage

    private val _nFloor = MutableLiveData("1")
    val nFloor: LiveData<String> = _nFloor

    private val _totalArea = MutableLiveData("80")
    val totalArea: LiveData<String> = _totalArea

    private val _builtArea = MutableLiveData("80")
    val builtArea: LiveData<String> = _builtArea

    private val _postalError = MutableLiveData<Boolean>()
    val postalError: LiveData<Boolean> = _postalError

    private val _postalMessageError = MutableLiveData<String>()
    val postalMessageError: LiveData<String> = _postalMessageError

    private val _antique = MutableLiveData("2024")
    val antique: LiveData<String> = _antique

    private val _offerType = MutableLiveData("Alquiler")
    val offerType: LiveData<String> = _offerType

    private val _offerTypeItem = MutableLiveData<OfferType>()
    val offerTypeItem: LiveData<OfferType> = _offerTypeItem

    private val _offerTypeSale = MutableLiveData("Venta")
    val offerTypeSale: LiveData<String> = _offerTypeSale

    private val _offerTypeSaleItem = MutableLiveData<OfferType>()
    val offerTypeSaleItem: LiveData<OfferType> = _offerTypeSaleItem

    private val _offerTypes = MutableStateFlow<UIState<List<OfferType>>>(Loading())
    val offerTypes = _offerTypes.asStateFlow()

    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price

    private val _priceError = MutableLiveData<Boolean>()
    val priceError: LiveData<Boolean> = _priceError

    private val _priceMessageError = MutableLiveData<String>()
    val priceMessageError: LiveData<String> = _priceMessageError

    private val _priceSale = MutableLiveData<String>()
    val priceSale: LiveData<String> = _priceSale

    private val _priceSaleError = MutableLiveData<Boolean>()
    val priceSaleError: LiveData<Boolean> = _priceSaleError

    private val _priceSaleMessageError = MutableLiveData<String>()
    val priceSaleMessageError: LiveData<String> = _priceSaleMessageError

    private val _propertyType = MutableLiveData<String>()
    val propertyType: LiveData<String> = _propertyType

    private val _propertyTypes = MutableStateFlow<UIState<List<PropertyType>>>(Loading())
    val propertyTypes = _propertyTypes.asStateFlow()

    private val _propertyTypeList = MutableLiveData<List<PropertyType>>()
    val propertyTypeList: LiveData<List<PropertyType>> = _propertyTypeList

    private val _propertyState = MutableLiveData<String>()
    val propertyState: LiveData<String> = _propertyState

    private val _propertyStates = MutableStateFlow<UIState<List<PropertyState>>>(Loading())
    val propertyStates = _propertyStates.asStateFlow()

    private val _propertyStateList = MutableLiveData<List<PropertyState>>()
    val propertyStateList: LiveData<List<PropertyState>> = _propertyStateList

    private val _latitude = MutableLiveData<String>()
    val latitude: LiveData<String> = _latitude

    private val _latitudeError = MutableLiveData<Boolean>()
    val latitudeError: LiveData<Boolean> = _latitudeError

    private val _latitudeMessageError = MutableLiveData<String>()
    val latitudeMessageError: LiveData<String> = _latitudeMessageError

    private val _longitude = MutableLiveData<String>()
    val longitude: LiveData<String> = _longitude

    private val _longitudeError = MutableLiveData<Boolean>()
    val longitudeError: LiveData<Boolean> = _longitudeError

    private val _longitudeMessageError = MutableLiveData<String>()
    val longitudeMessageError: LiveData<String> = _longitudeMessageError

    private val _altitude = MutableLiveData<String>()
    val altitude: LiveData<String> = _altitude

    private val _altitudeError = MutableLiveData<Boolean>()
    val altitudeError: LiveData<Boolean> = _altitudeError

    private val _altitudeMessageError = MutableLiveData<String>()
    val altitudeMessageError: LiveData<String> = _altitudeMessageError

    private val _altitudeBase = MutableLiveData<String>()
    val altitudeBase: LiveData<String> = _altitudeBase

    private val _altitudeBaseError = MutableLiveData<Boolean>()
    val altitudeBaseError: LiveData<Boolean> = _altitudeBaseError

    private val _altitudeBaseMessageError = MutableLiveData<String>()
    val altitudeBaseMessageError: LiveData<String> = _altitudeBaseMessageError

    private val _districts = MutableStateFlow<UIState<List<District>>>(Loading())
    val districts = _districts.asStateFlow()

    private val _district = MutableLiveData<String>()
    val district: LiveData<String> = _district

    private val _districtList = MutableLiveData<List<District>>()
    val districtList: LiveData<List<District>> = _districtList

    private val _provinces = MutableStateFlow<UIState<List<Province>>>(Loading())
    val provinces = _provinces.asStateFlow()

    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

    private val _departments = MutableStateFlow<UIState<List<Department>>>(Loading())
    val departments = _departments.asStateFlow()

    private val _department = MutableLiveData<String>()
    val department: LiveData<String> = _department

    private val _countries = MutableStateFlow<UIState<List<Country>>>(Loading())
    val countries = _countries.asStateFlow()

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

    private val _imageUris = MutableLiveData<List<Uri>>()
    val imageUris: LiveData<List<Uri>> = _imageUris

    private val _imageUrisError = MutableLiveData<Boolean>()
    val imageUrisError: LiveData<Boolean> = _imageUrisError

    private val _imageUrisMessageError = MutableLiveData<String>()
    val imageUrisMessageError: LiveData<String> = _imageUrisMessageError

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> = _imageUrls

    private val _images = MutableStateFlow<UIState<List<Image>>>(Loading())
    val images = _images.asStateFlow()

    private val _devices = MutableStateFlow<UIState<List<Device>>>(Loading())
    val devices = _devices.asStateFlow()

    private val _properties = MutableStateFlow<UIState<List<Property>>>(Loading())
    val properties = _properties.asStateFlow()

    private val _property = MutableStateFlow<UIState<Property>>(None())
    val property = _property.asStateFlow()

    private val _insert = MutableStateFlow<UIState<String>>(None())
    val insert = _insert.asStateFlow()

    private val _insertComplex = MutableStateFlow<UIState<String>>(None())
    val insertComplex = _insertComplex.asStateFlow()

    private val _uploadImage = MutableStateFlow<UIState<Uri>>(None())
    val uploadImage = _uploadImage.asStateFlow()

    private val _uploadDocument = MutableStateFlow<UIState<Uri>>(None())
    val uploadDocument = _uploadDocument.asStateFlow()

    private val _insertImage = MutableStateFlow<UIState<String>>(None())
    val insertImage = _insertImage.asStateFlow()

    private val _insertPublishing = MutableStateFlow<UIState<String>>(None())
    val insertPublishing = _insertPublishing.asStateFlow()

    private val _insertHistorical = MutableStateFlow<UIState<String>>(None())
    val insertHistorical = _insertHistorical.asStateFlow()

    private val _update = MutableStateFlow<UIState<String>>(None())
    val update = _update.asStateFlow()

    private val _insertRequest = MutableStateFlow<UIState<String>>(None())
    val insertRequest = _insertRequest.asStateFlow()

    private val _updateRequest = MutableStateFlow<UIState<String>>(None())
    val updateRequest = _updateRequest.asStateFlow()

    private val _deleteRequest = MutableStateFlow<UIState<String>>(None())
    val deleteRequest = _deleteRequest.asStateFlow()

    private val _delete = MutableStateFlow<UIState<String>>(None())
    val delete = _delete.asStateFlow()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _visitMillis = MutableLiveData<Long>()
    val visitMillis: LiveData<Long> = _visitMillis

    private val _visitLocal = MutableLiveData<LocalDateTime>()
    val visitLocal: LiveData<LocalDateTime> = _visitLocal

    private val _visitDay = MutableLiveData<String>("")
    val visitDay: LiveData<String> = _visitDay

    private val _requestXPublishing =
        MutableStateFlow<UIState<List<RequestHasPublishing>>>(Loading())
    val requestXPublishing = _requestXPublishing.asStateFlow()

    private val _requests = MutableLiveData<List<RequestHasPublishing>>(emptyList())
    val requests: LiveData<List<RequestHasPublishing>> = _requests

    private val _deviceItems = MutableLiveData<List<Device>>(emptyList())
    val deviceItems: LiveData<List<Device>> = _deviceItems

    private val _requestState = MutableLiveData("Solicitado")
    val requestState: LiveData<String> = _requestState

    private var propertyId = MutableLiveData(0)

    val foundLocations = mutableStateMapOf<String, ServiceMarker>()

    lateinit var placesClient: PlacesClient

    val locationAutofill = mutableStateListOf<AutocompleteResult>()

    var searchQuery by mutableStateOf("")
        private set

    fun searchAddress(query: String) {
        locationAutofill.clear()
        viewModelScope.launch {
            placesClient.autocompleteAddress(query = query) { response ->
                locationAutofill += response.autocompletePredictions.map {
                    AutocompleteResult(
                        placeId = it.placeId,
                        address = it.getPrimaryText(null).toString(),
                        secondary = it.getSecondaryText(null).toString()
                    )
                }
            }
        }
    }

    fun getCoordinates(result: AutocompleteResult, onLocationResult: (LatLng) -> Unit) {
        viewModelScope.launch {
            placesClient.getCoordinates(result = result, onLocationResult = onLocationResult)
        }
    }

    fun years() = arrayOf(
        "1950",
        "1951",
        "1952",
        "1953",
        "1954",
        "1955",
        "1956",
        "1957",
        "1958",
        "1959",
        "1960",
        "1961",
        "1962",
        "1963",
        "1964",
        "1965",
        "1966",
        "1967",
        "1968",
        "1969",
        "1970",
        "1971",
        "1972",
        "1973",
        "1974",
        "1975",
        "1976",
        "1977",
        "1978",
        "1979",
        "1980",
        "1981",
        "1982",
        "1983",
        "1984",
        "1985",
        "1986",
        "1987",
        "1988",
        "1989",
        "1990",
        "1991",
        "1992",
        "1993",
        "1994",
        "1995",
        "1996",
        "1997",
        "1998",
        "1999",
        "2000",
        "2001",
        "2002",
        "2003",
        "2004",
        "2005",
        "2006",
        "2007",
        "2008",
        "2009",
        "2010",
        "2011",
        "2012",
        "2013",
        "2014",
        "2015",
        "2016",
        "2017",
        "2018",
        "2019",
        "2020",
        "2021",
        "2022",
        "2023",
        "2024"
    )

    fun requestStates() = listOf(
        RequestState(id = 1, name = "Solicitado"),
        RequestState(id = 2, name = "Agendado"),
        RequestState(id = 3, name = "Concluido"),
        RequestState(id = 4, name = "Cancelado"),
    )

    fun colors(id: Int) = when (id) {
        1 -> Color.White
        2 -> Color.Yellow
        3 -> Color.Green
        4 -> Color.Red
        else -> Color.White
    }

    fun titles(id: Int) = when (id) {
        1 -> "Confirmar visita"
        2 -> "Confirmar visita exitosa"
        3 -> "Fin del proceso"
        4 -> "Proceso cancelado"
        else -> "Confirmar solicitud"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getVisitDateTime(hour: Int, minute: Int): LocalDateTime {
        val date = _visitMillis.value ?: System.currentTimeMillis()
        return date.plus(timeToMillis(hours = hour, minute = minute))
            .millisToLocalDateTime()
            .toKotlinLocalDateTime()
    }

    fun onLoadingVisible(visible: Boolean) {
        _loadingVisible.value = visible
    }

    fun onErrorDialogVisible(visible: Boolean) {
        _errorDialogVisible.value = visible
    }

    fun onCompleteDialogVisible(visible: Boolean) {
        _completeDialogVisible.value = visible
    }

    fun onSearchAddressBottomSheetVisible(visible: Boolean) {
        _searchAddressBottomSheetVisible.value = visible
    }

    fun onInboxDetailBottomSheetVisible(visible: Boolean) {
        _inboxDetailBottomSheetVisible.value = visible
    }

    fun onVisitDayDialogVisible(visible: Boolean) {
        _visitDayDialogVisible.value = visible
    }

    fun onVisitDayTimeDialogVisible(visible: Boolean) {
        _visitDayTimeDialogVisible.value = visible
    }

    fun onConfirmDialogVisible(visible: Boolean) {
        _confirmDialogVisible.value = visible
    }

    fun onPropertyItemChanged(property: String) {
        _propertyItem.value = property
    }

    fun onTitleChanged(title: String) {
        _title.value = title
    }

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    fun onMaintenanceChanged(maintenance: String) {
        _maintenance.value = maintenance
    }

    fun onAddressChanged(address: String) {
        _address.value = address
    }

    fun onPostalChanged(postal: String) {
        _postal.value = postal
    }

    fun onNBedroomChanged(nBedroom: String) {
        _nBedroom.value = nBedroom
    }

    fun onNBathroomChanged(nBathroom: String) {
        _nBathroom.value = nBathroom
    }

    fun onNGarageChanged(nGarage: String) {
        _nGarage.value = nGarage
    }

    fun onNFloorChanged(nFloor: String) {
        _nFloor.value = nFloor
    }

    fun onTotalAreaChanged(totalArea: String) {
        _postal.value = totalArea
    }

    fun onBuiltAreaChanged(builtArea: String) {
        _builtArea.value = builtArea
    }

    fun onAntiqueChanged(year: String) {
        _antique.value = year
    }

    fun onOfferTypeChanged(offerType: String) {
        _offerType.value = offerType
    }

    fun onOfferTypeItemChanged(offerType: OfferType) {
        _offerTypeItem.value = offerType
    }

    fun onOfferTypeSaleChanged(offerType: String) {
        _offerTypeSale.value = offerType
    }

    fun onOfferTypeSaleItemChanged(offerType: OfferType) {
        _offerTypeSaleItem.value = offerType
    }

    fun onPriceChanged(price: String) {
        _price.value = price
    }

    fun onPriceSaleChanged(price: String) {
        _priceSale.value = price
    }

    fun onPropertyTypeChanged(propertyType: String) {
        _propertyType.value = propertyType
    }

    fun onPropertyTypeListChanged(propertyTypeList: List<PropertyType>) {
        _propertyTypeList.value = propertyTypeList
    }

    fun onPropertyStateChanged(propertyState: String) {
        _propertyState.value = propertyState
    }

    fun onPropertyStateListChanged(propertyStateList: List<PropertyState>) {
        _propertyStateList.value = propertyStateList
    }

    fun onLatitudeChanged(latitude: String) {
        _latitude.value = latitude
    }

    fun onLongitudeChanged(longitude: String) {
        _longitude.value = longitude
    }

    fun onAltitudeChanged(altitude: String) {
        _altitude.value = altitude
    }

    fun onAltitudeBaseChanged(altitudeBase: String) {
        _altitudeBase.value = altitudeBase
    }

    fun onDistrictChanged(district: String) {
        _district.value = district
    }

    fun onDistrictListChanged(districtList: List<District>) {
        _districtList.value = districtList
    }

    fun onProvinceChanged(province: String) {
        _province.value = province
    }

    fun onDepartmentChanged(department: String) {
        _department.value = department
    }

    fun onCountryChanged(country: String) {
        _country.value = country
    }

    fun onImageUrisChanged(uris: List<Uri>) {
        _imageUris.value = uris
    }

    fun onImageUrlsChanged(urls: List<String>) {
        _imageUrls.value = urls
    }

    fun onRequestsChanged(requests: List<RequestHasPublishing>) {
        _requests.value = requests
    }

    fun onDevicesChanged(devices: List<Device>) {
        _deviceItems.value = devices
    }

    fun onUserChanged(user: User) {
        _user.value = user
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun onRequestStateChanged(state: String) {
        _requestState.value = state
    }

    fun onVisitMillisChanged(visitMillis: Long) {
        _visitMillis.value = visitMillis
    }

    fun onVisitLocalChanged(visitLocal: LocalDateTime) {
        _visitLocal.value = visitLocal
    }

    fun onVisitDayChanged(visitDay: String) {
        _visitDay.value = visitDay
    }

    fun validateTitle(): Boolean {
        if (_title.value.isNullOrBlank()) {
            _titleMessageError.value = "Ingresa un titulo para la publicación"
            _titleError.value = true
        } else {
            _titleError.value = false
        }
        return _titleError.value == false
    }

    fun validateDescription(): Boolean {
        if (_description.value.isNullOrBlank()) {
            _descriptionMessageError.value = "Ingresa una descripcion para la publicación"
            _descriptionError.value = true
        } else {
            _descriptionError.value = false
        }
        return _descriptionError.value == false
    }

    fun validateMaintenance(): Boolean {
        if (_maintenance.value.isNullOrBlank() || _maintenance.value.toString()
                .matches("\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _maintenanceMessageError.value = "Ingresa una cantidad valida de mantenimiento"
            _maintenanceError.value = true
        } else {
            _maintenanceError.value = false
        }
        return _maintenanceError.value == false
    }

    fun validateAddress(): Boolean {
        if (_address.value.isNullOrBlank()) {
            _addressMessageError.value = "Ingresa una dirección valida"
            _addressError.value = true
        } else {
            _addressError.value = false
        }
        return _addressError.value == false
    }

    fun validatePostal(): Boolean {
        if (_postal.value.isNullOrBlank()) {
            _postalMessageError.value = "Ingresa un codigo postal valido"
            _postalError.value = true
        } else {
            _postalError.value = false
        }
        return _postalError.value == false
    }

    fun validatePrice(): Boolean {
        if (_price.value.isNullOrBlank() || _price.value.toString()
                .matches("\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _priceMessageError.value = "Ingresa un precio valido"
            _priceError.value = true
        } else {
            _priceError.value = false
        }
        return _priceError.value == false
    }

    fun validatePriceSale(): Boolean {
        if (_priceSale.value.isNullOrBlank() || _priceSale.value.toString()
                .matches("\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _priceSaleMessageError.value = "Ingresa un precio valido"
            _priceSaleError.value = true
        } else {
            _priceSaleError.value = false
        }
        return _priceSaleError.value == false
    }

    fun validateAllPrice(): Boolean {
        if (validatePrice() && validatePriceSale()) {
            return true
        } else if (validatePrice()) {
            return true
        } else if (validatePriceSale()) {
            return true
        } else {
            return false
        }
    }

    fun validateLatitude(): Boolean {
        if (_latitude.value.isNullOrBlank() || _latitude.value.toString()
                .matches("-?\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _latitudeMessageError.value = "Ingresa una coodenada valida"
            _latitudeError.value = true
        } else {
            _latitudeError.value = false
        }
        return _latitudeError.value == false
    }

    fun validateLongitude(): Boolean {
        if (_longitude.value.isNullOrBlank() || _longitude.value.toString()
                .matches("-?\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _longitudeMessageError.value = "Ingresa una coodenada valida"
            _longitudeError.value = true
        } else {
            _longitudeError.value = false
        }
        return _longitudeError.value == false
    }

    fun validateAltitude(): Boolean {
        if (_altitude.value.isNullOrBlank() || _altitude.value.toString()
                .matches("-?\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _altitudeMessageError.value = "Ingresa una coodenada valida"
            _altitudeError.value = true
        } else {
            _altitudeError.value = false
        }
        return _altitudeError.value == false
    }

    fun validateAltitudeBase(): Boolean {
        if (_altitudeBase.value.isNullOrBlank() || _altitudeBase.value.toString()
                .matches("-?\\d+(\\.\\d+)?".toRegex()).not()
        ) {
            _altitudeBaseMessageError.value = "Ingresa una coodenada valida"
            _altitudeBaseError.value = true
        } else {
            _altitudeBaseError.value = false
        }
        return _altitudeBaseError.value == false
    }

    fun validateImages(): Boolean {
        if (_imageUris.value.isNullOrEmpty()) {
            _imageUrisMessageError.value = "Suba alguna imagen referencial del inmueble"
            _imageUrisError.value = true
        } else {
            _imageUrisError.value = false
        }
        return _imageUrisError.value == false
    }

    fun validateForm(): Boolean {
        if (validateTitle() && validateDescription() && validateMaintenance() && validateAddress()
            && validatePostal() && validateAllPrice() && validateLatitude() && validateLongitude()
            && validateAltitude() && validateAltitudeBase() && offerType.value != "---"
            && propertyType.value != "---" && propertyState.value != "---" && country.value != "---"
            && department.value != "---" && province.value != "---" && district.value != "---"
            && validateImages()
        ) {
            return true
        } else {
            return false
        }
    }

    fun executeRegister() {
        if (validateForm()) {
            viewModelScope.launch {
                Log.e(
                    "List",
                    "${_propertyTypeList.value.toString()} ${_propertyStateList.value.toString()} ${_districtList.value.toString()}"
                )
                Log.e("Match", "${_propertyType.value} ${_propertyState.value} ${_district.value}")
                registerProperty(
                    property = getProperty()
                )
            }
        }
    }

    fun executeRegisterComplex(id: Int) {
        propertyId.postValue(id)
        val list = mutableListOf<Pair<OfferType, Double>>()
        if (validatePrice()) {
            Log.e("ID", _offerTypeItem.value?.id.toString())
            list.add(
                element = Pair(
                    first = OfferType(
                        id = _offerTypeItem.value?.id ?: 0,
                        name = _offerTypeItem.value?.name.toString()
                    ),
                    second = _price.value.toString().toDouble()
                )
            )
        }
        if (validatePriceSale()) {
            Log.e("ID", _offerTypeSaleItem.value?.id.toString())
            list.add(
                element = Pair(
                    first = OfferType(
                        id = _offerTypeSaleItem.value?.id ?: 0,
                        name = _offerTypeSaleItem.value?.name.toString()
                    ),
                    second = _priceSale.value.toString().toDouble()
                )
            )
        }
        list.forEach { item ->
            registerPropertyHasOfferType(
                propertyHasOfferType = PropertyHasOfferType(
                    property = getProperty(id = id),
                    offerType = item.first,
                    price = item.second
                )
            )
        }
    }

    fun executeUploadImages() {
        _imageUris.value?.forEach {
            uploadImage(imageURI = it)
        }
    }

    fun executeRegisterMedia(uri: Uri) {
        registerImage(
            image = Image(
                id = 0,
                url = uri.toString(),
                property = getProperty(id = propertyId.value ?: 0)
            )
        )
    }

    fun executeRegisterPublishing() {
        registerPublishing(
            publishing = Publishing(
                id = 0,
                numberView = 0,
                property = getProperty(id = propertyId.value ?: 0)
            )
        )
    }

    fun loadProperties() {
        viewModelScope.launch {
            repository.loadProperties()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _properties.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _properties.value = it }
        }
    }

    fun loadProperty(id: Int) {
        viewModelScope.launch {
            repository.loadProperty(id = id)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _property.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _property.value = it }
        }
    }

    fun registerProperty(property: Property) {
        viewModelScope.launch {
            repository.registerProperty(property = property)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _insert.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _insert.value = it }
        }
    }

    fun updateProperty(id: Int, property: Property) {
        viewModelScope.launch {
            repository.updateProperty(id = id, property = property)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _update.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _update.value = it }
        }
    }

    fun deleteProperty(id: Int) {
        viewModelScope.launch {
            repository.deleteProperty(id = id)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _delete.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _delete.value = it }
        }
    }

    fun registerPropertyHasOfferType(propertyHasOfferType: PropertyHasOfferType) {
        viewModelScope.launch {
            repository.registerPropertyXOfferType(propertyHasOfferType = propertyHasOfferType)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _insertComplex.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _insertComplex.value = it }
        }
    }

    fun loadOfferTypes() {
        viewModelScope.launch {
            repository.loadOfferTypes()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _offerTypes.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _offerTypes.value = it }
        }
    }

    fun loadPropertyTypes() {
        viewModelScope.launch {
            repository.loadPropertyTypes()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _propertyTypes.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _propertyTypes.value = it }
        }
    }

    fun loadPropertyStates() {
        viewModelScope.launch {
            repository.loadPropertyStates()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _propertyStates.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _propertyStates.value = it }
        }
    }

    fun loadCountries() {
        viewModelScope.launch {
            repository.loadCountries()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _countries.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _countries.value = it }
        }
    }

    fun loadDepartments() {
        viewModelScope.launch {
            repository.loadDepartments()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _departments.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _departments.value = it }
        }
    }

    fun loadProvinces() {
        viewModelScope.launch {
            repository.loadProvinces()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _provinces.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _provinces.value = it }
        }
    }

    fun loadDistricts() {
        viewModelScope.launch {
            repository.loadDistricts()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _districts.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _districts.value = it }
        }
    }

    fun loadImages() {
        viewModelScope.launch {
            repository.loadImages()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _images.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _images.value = it }
        }
    }

    fun loadDevices() {
        viewModelScope.launch {
            repository.loadDevices()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _devices.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _devices.value = it }
        }
    }

    fun uploadImage(imageURI: Uri) {
        viewModelScope.launch {
            repository.uploadImages(name = UUID.randomUUID().toString(), imageURI = imageURI)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _uploadImage.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _uploadImage.value = it }
        }
    }

    fun registerImage(image: Image) {
        viewModelScope.launch {
            repository.registerImage(image = image)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _insertImage.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _insertImage.value = it }
        }
    }

    fun registerPublishing(publishing: Publishing) {
        viewModelScope.launch {
            repository.registerPublishing(publishing = publishing)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _insertPublishing.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _insertPublishing.value = it }
        }
    }

    fun loadRequestsXPublishing() {
        viewModelScope.launch {
            repository.loadRequestsXPublishing()
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _requestXPublishing.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _requestXPublishing.value = it }
        }
    }

    fun insertRequestXPublishing(request: RequestHasPublishing) {
        viewModelScope.launch {
            repository.registerRequestXPublishing(
                requestHasPublishing = request
            )
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _insertRequest.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _insertRequest.value = it }
        }
    }

    fun executeUpdateRequest(request: RequestHasPublishing) {
        updateRequest(request = request.request)
    }

    fun updateRequest(request: Request) {
        viewModelScope.launch {
            repository.updateRequest(
                id = request.id,
                request = request
            )
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _updateRequest.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _updateRequest.value = it }
        }
    }

    fun deleteRequestXPublishing(request: RequestHasPublishing) {
        viewModelScope.launch {
            repository.deleteRequestXPublishing(
                rId = request.request.id,
                pId = request.publishing.id
            )
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _deleteRequest.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _deleteRequest.value = it }
        }
    }

    fun registerHistorical(historical: Historical) {
        viewModelScope.launch {
            repository.registerHistorical(historical = historical)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _insertHistorical.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _insertHistorical.value = it }
        }
    }

    fun uploadDocument(docURI: Uri) {
        viewModelScope.launch {
            repository.uploadDocument(name = UUID.randomUUID().toString(), docURI = docURI)
                .map { it }
                .flowOn(dispatcher)
                .catch {
                    _uploadDocument.value = Error(error = it)
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
                    Loading()
                ).collect { _uploadDocument.value = it }
        }
    }

    private fun getProperty(id: Int = 0) = Property(
        id = id,
        title = _title.value.toString(),
        description = _description.value.toString(),
        maintenance = _maintenance.value.toString().toDouble(),
        address = _address.value.toString(),
        postalCode = _postal.value.toString(),
        nBedroom = _nBedroom.value.toString().toInt(),
        nBathroom = _nBathroom.value.toString().toDouble(),
        nGarage = _nGarage.value.toString().toInt(),
        buildingYear = _antique.value.toString().toInt(),
        floor = _nFloor.value.toString().toInt(),
        totalArea = _totalArea.value.toString().toDouble(),
        builtArea = _builtArea.value.toString().toDouble(),
        propertyType = _propertyTypeList.value!!.first {
            it.name == _propertyType.value.toString()
        },
        propertyState = _propertyStateList.value!!.first {
            it.name == _propertyState.value.toString()
        },
        location = Location(
            id = 30,
            latitude = _latitude.value.toString().toDouble(),
            longitude = _longitude.value.toString().toDouble(),
            altitude = _altitude.value.toString().toDouble(),
            altitudeBase = _altitudeBase.value.toString().toDouble()
        ),
        district = _districtList.value!!.first {
            it.name == _district.value.toString()
        },
        user = _user.value!!
    )

    fun clearForm() {
        onTitleChanged("")
        onDescriptionChanged("")
        onMaintenanceChanged("")
        onAddressChanged("")
        onPostalChanged("")
        onNBedroomChanged("1")
        onNBathroomChanged("1")
        onNGarageChanged("0")
        onNFloorChanged("1")
        onTotalAreaChanged("80")
        onBuiltAreaChanged("80")
        onPriceChanged("")
        onPriceSaleChanged("")
        onLatitudeChanged("")
        onLongitudeChanged("")
        onAltitudeChanged("")
        onAltitudeBaseChanged("")
        onImageUrisChanged(emptyList())
        propertyId.postValue(0)
        _titleError.postValue(false)
        _descriptionError.postValue(false)
        _maintenanceError.postValue(false)
        _addressError.postValue(false)
        _postalError.postValue(false)
        _priceError.postValue(false)
        _priceSaleError.postValue(false)
        _latitudeError.postValue(false)
        _longitudeError.postValue(false)
        _altitudeError.postValue(false)
        _altitudeBaseError.postValue(false)
    }
}