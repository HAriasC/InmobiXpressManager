package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertyHasOfferType(
    val property: Property,
    val offerType: OfferType,
    val price: Double
)