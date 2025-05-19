package com.inmobixpress.inmobixpressmanager.ui.model

import com.google.android.gms.maps.model.LatLng

data class ServiceMarker(
    val result: AutocompleteResult,
    val location: LatLng
)
