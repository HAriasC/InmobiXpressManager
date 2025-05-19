package com.inmobixpress.inmobixpressmanager.data.network.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Historical(
    val publishingState: PublishingState,
    val publishing: Publishing,
    val startDate: LocalDateTime,
    val finishDate: LocalDateTime,
    val contract: String
)