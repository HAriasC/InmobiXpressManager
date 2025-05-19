package com.inmobixpress.inmobixpressmanager.data.network.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.datetime.LocalDateTime
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Request(
    val id: Int,
    val date: LocalDateTime,
    val message: String,
    val requestType: RequestType,
    val requestState: RequestState,
    val user: User
) : Parcelable {
    companion object : Parceler<Request> {
        override fun Request.write(parcel: Parcel, flags: Int) {
            parcel.writeSerializable(date.javaClass)
        }

        @SuppressLint("NewApi")
        override fun create(parcel: Parcel): Request {
            return Request(
                id = parcel.readInt(),
                date = parcel.readSerializable(
                    LocalDateTime.Companion::class.java.classLoader,
                    LocalDateTime::class.java
                )!!,
                message = parcel.readString().toString(),
                requestType = parcel.readParcelable(
                    RequestType.Companion::class.java.classLoader,
                    RequestType::class.java
                )!!,
                requestState = parcel.readParcelable(
                    RequestState.Companion::class.java.classLoader,
                    RequestState::class.java
                )!!,
                user = parcel.readParcelable(
                    User.Companion::class.java.classLoader,
                    User::class.java
                )!!
            )
        }
    }
}