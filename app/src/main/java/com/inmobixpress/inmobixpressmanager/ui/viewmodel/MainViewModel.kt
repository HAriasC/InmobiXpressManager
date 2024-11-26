package com.inmobixpress.inmobixpressmanager.ui.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

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

    private val _postalError = MutableLiveData<Boolean>()
    val postalError: LiveData<Boolean> = _postalError

    private val _postalMessageError = MutableLiveData<String>()
    val postalMessageError: LiveData<String> = _postalMessageError

    private val _antique = MutableLiveData<String>()
    val antique: LiveData<String> = _antique

    private val _offerType = MutableLiveData<String>()
    val offerType: LiveData<String> = _offerType

    private val _offerTypes = MutableLiveData<List<String>>()
    val offerTypes: LiveData<List<String>> = _offerTypes

    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price

    private val _priceError = MutableLiveData<Boolean>()
    val priceError: LiveData<Boolean> = _priceError

    private val _priceMessageError = MutableLiveData<String>()
    val priceMessageError: LiveData<String> = _priceMessageError

    private val _propertyState = MutableLiveData<String>()
    val propertyState: LiveData<String> = _propertyState

    private val _propertyStates = MutableLiveData<List<String>>()
    val propertyStates: LiveData<List<String>> = _propertyStates

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

    private val _districts = MutableLiveData<List<String>>()
    val districts: LiveData<List<String>> = _districts

    private val _district = MutableLiveData<String>()
    val district: LiveData<String> = _district

    private val _provinces = MutableLiveData<List<String>>()
    val provinces: LiveData<List<String>> = _provinces

    private val _province = MutableLiveData<String>()
    val province: LiveData<String> = _province

    private val _departments = MutableLiveData<List<String>>()
    val departments: LiveData<List<String>> = _departments

    private val _department = MutableLiveData<String>()
    val department: LiveData<String> = _department

    private val _countries = MutableLiveData<List<String>>()
    val countries: LiveData<List<String>> = _countries

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

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

    fun onAntiqueChanged(year: String) {
        _antique.value = year
    }

    fun onOfferTypeChanged(offerType: String) {
        _offerType.value = offerType
    }

    fun onPriceChanged(price: String) {
        _price.value = price
    }

    fun onPropertyStateChanged(propertyState: String) {
        _propertyState.value = propertyState
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
    fun onProvinceChanged(province: String) {
        _province.value = province
    }

    fun onDepartmentChanged(department: String) {
        _department.value = department
    }

    fun onCountryChanged(country: String) {
        _country.value = country
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
}