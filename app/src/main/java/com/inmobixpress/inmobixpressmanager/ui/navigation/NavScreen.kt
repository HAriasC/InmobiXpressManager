package com.inmobixpress.inmobixpressmanager.ui.navigation

import kotlinx.serialization.Serializable

object NavScreen {

    @Serializable
    object Auth

    @Serializable
    object Main

    @Serializable
    object Login

    @Serializable
    object Register

    @Serializable
    object Inbox

    @Serializable
    data class Message(val id: Int)

    @Serializable
    object Notification

    @Serializable
    object Dashboard

    @Serializable
    data class Detail(val id: Int)

    @Serializable
    object Charts
}