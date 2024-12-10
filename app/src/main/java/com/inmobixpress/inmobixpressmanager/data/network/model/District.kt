package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class District(
    val id: Int,
    val name: String,
    val province: Province,
    val location: Location
)