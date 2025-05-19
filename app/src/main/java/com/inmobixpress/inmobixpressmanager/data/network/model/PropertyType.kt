package com.inmobixpress.inmobixpressmanager.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PropertyType(
    val id: Int,
    val name: String
) : Parcelable