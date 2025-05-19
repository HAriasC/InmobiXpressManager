package com.inmobixpress.inmobixpressmanager.ui.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.android.libraries.places.api.net.SearchByTextResponse
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.google.android.libraries.places.api.net.SearchNearbyResponse
import com.inmobixpress.inmobixpressmanager.ui.model.AutocompleteResult
import java.util.Locale


fun PlacesClient.search(
    includedTypes: List<String>,
    latLng: LatLng,
    onSearchResult: (SearchNearbyResponse) -> Unit
) {
    // Define a list of fields to include in the response for each returned place.
    val placeFields = listOf(
        Place.Field.ADDRESS,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.LAT_LNG,
        Place.Field.PHONE_NUMBER,
        Place.Field.PRIMARY_TYPE,
        Place.Field.TYPES
    )

    // Define the search area as a 1000 meter diameter circle in New York, NY.
    val center = latLng
    val circle = CircularBounds.newInstance(center, 800.0)

    // Define a list of types to include.
    //val includedTypes = listOf("restaurant", "cafe")
    // Define a list of types to exclude.
    //val excludedTypes = listOf("pizza_restaurant", "american_restaurant")

    // Use the builder to create a SearchNearbyRequest object.
    val searchNearbyRequest = SearchNearbyRequest.builder(circle, placeFields)
        .setIncludedTypes(includedTypes)
        .setMaxResultCount(20)
        .build()

    // Call placesClient.searchNearby() to perform the search.
    // Define a response handler to process the returned List of Place objects.
    this.searchNearby(searchNearbyRequest).addOnSuccessListener { response ->
        onSearchResult(response)
    }
}

fun PlacesClient.autocompleteLocality(
    query: String,
    onSearchResult: (FindAutocompletePredictionsResponse) -> Unit
) {
    val autocompletePlacesRequest = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .setTypesFilter(listOf("locality", "political"))
        //.setTypesFilter(listOf("country", "political"))
        .setCountries(listOf("pe"))
        .build()
    this.findAutocompletePredictions(autocompletePlacesRequest).addOnSuccessListener { response ->
        onSearchResult(response)
        Log.e("Place", response.autocompletePredictions.toString())
    }
}

fun PlacesClient.autocompleteAddress(
    query: String,
    onSearchResult: (FindAutocompletePredictionsResponse) -> Unit
) {
    val autocompletePlacesRequest = FindAutocompletePredictionsRequest.builder()
        .setOrigin(LatLng(-12.1220553, -77.0374705))
        //.setTypesFilter(listOf(PlaceTypes.ADDRESS, PlaceTypes.STREET_ADDRESS, PlaceTypes.GEOCODE))
        .setCountries(listOf("pe"))
        .setQuery(query)
        .build()
    this.findAutocompletePredictions(autocompletePlacesRequest).addOnSuccessListener { response ->
        onSearchResult(response)
    }
}

fun PlacesClient.getCoordinates(result: AutocompleteResult, onLocationResult: (LatLng) -> Unit) {
    val placeFields = listOf(Place.Field.LOCATION)
    val request = FetchPlaceRequest.newInstance(result.placeId, placeFields)
    this.fetchPlace(request).addOnSuccessListener {
        if (it != null) {
            onLocationResult(it.place.location!!)
        }
    }.addOnFailureListener {
        it.printStackTrace()
    }
}

fun PlacesClient.searchLocality(
    query: String,
    onSearchResult: (SearchByTextResponse) -> Unit
) {
    val placeFields = listOf(
        Place.Field.ADDRESS,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.LAT_LNG,
        Place.Field.PRIMARY_TYPE,
        Place.Field.TYPES
    )
    val searchByTextRequest = SearchByTextRequest.builder(query, placeFields)
        .setIncludedType("locality")
        .setRegionCode("pe")
        .setMaxResultCount(20)
        .build()
    this.searchByText(searchByTextRequest).addOnSuccessListener { response ->
        onSearchResult(response)
    }
}

fun PlacesClient.fetchPlaceDetail(
    placeId: String,
    onSearchResult: (FetchPlaceResponse) -> Unit
) {
    val placeFields = listOf(
        Place.Field.ADDRESS,
        Place.Field.ADDRESS_COMPONENTS,
        Place.Field.ID,
        Place.Field.NAME,
        Place.Field.LAT_LNG,
        Place.Field.PRIMARY_TYPE,
        Place.Field.TYPES
    )
    val request = FetchPlaceRequest.newInstance(placeId, placeFields)
    this.fetchPlace(request).addOnSuccessListener { response ->
        onSearchResult(response)
    }
}

fun Context.getCity(latLng: LatLng): String {
    val geocoder = Geocoder(this, Locale.getDefault())
    val addresses = mutableListOf<Address>()
    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)?.let {
        addresses.addAll(it)
    }
    return addresses.firstOrNull()?.locality ?: ""
}