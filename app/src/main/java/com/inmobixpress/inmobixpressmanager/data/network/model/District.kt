package com.inmobixpress.inmobixpressmanager.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class District(
    val id: Int,
    val name: String,
    val province: Province,
    val location: Location
) : Parcelable