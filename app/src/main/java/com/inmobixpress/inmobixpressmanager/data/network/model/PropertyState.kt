package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertyState(
    val id: Int,
    val name: String
)