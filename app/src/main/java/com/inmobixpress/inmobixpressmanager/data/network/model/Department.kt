package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val id: Int,
    val name: String,
    val country: Country
)