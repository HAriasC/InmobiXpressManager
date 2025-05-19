package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PublishingState(
    val id: Int,
    val name: String
)