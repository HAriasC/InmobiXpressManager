package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val id: Int,
    val name: String,
    val countryCode: String
)