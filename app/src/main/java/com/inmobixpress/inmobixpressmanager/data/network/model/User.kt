package com.inmobixpress.inmobixpressmanager.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val motherLastName: String,
    val businessName: String,
    val email: String,
    val identityDocument: String,
    val username: String,
    val password: String,
    val documentType: DocumentType,
    val country: Country
) : Parcelable
