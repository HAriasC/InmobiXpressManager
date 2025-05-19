package com.inmobixpress.inmobixpressmanager.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Property(
    val id: Int,
    val title: String,
    val description: String,
    val maintenance: Double,
    val address: String,
    val postalCode: String,
    val nBedroom: Int,
    val nBathroom: Double,
    val nGarage: Int,
    val buildingYear: Int,
    val floor: Int,
    val totalArea: Double,
    val builtArea: Double,
    val propertyType: PropertyType,
    val propertyState: PropertyState,
    val location: Location,
    val district: District,
    val user: User
) : Parcelable