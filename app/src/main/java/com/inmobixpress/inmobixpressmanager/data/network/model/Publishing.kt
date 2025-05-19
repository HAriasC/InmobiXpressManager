package com.inmobixpress.inmobixpressmanager.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Publishing(
    val id: Int,
    val numberView: Int,
    val property: Property
) : Parcelable