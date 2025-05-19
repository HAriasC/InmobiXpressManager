package com.inmobixpress.inmobixpressmanager.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Location(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val altitudeBase: Double
) : Parcelable