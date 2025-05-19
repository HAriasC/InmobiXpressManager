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
data class RequestHasPublishing(
    val request: Request,
    val publishing: Publishing,
    val createDate: LocalDateTime
) : Parcelable {
    companion object : Parceler<RequestHasPublishing> {
        override fun RequestHasPublishing.write(parcel: Parcel, flags: Int) {
            parcel.writeSerializable(createDate.javaClass)
        }

        @SuppressLint("NewApi")
        override fun create(parcel: Parcel): RequestHasPublishing {
            return RequestHasPublishing(
                request = parcel.readParcelable(
                    Request.Companion::class.java.classLoader,
                    Request::class.java
                )!!,
                publishing = parcel.readParcelable(
                    Publishing.Companion::class.java.classLoader,
                    Publishing::class.java
                )!!,
                createDate = parcel.readSerializable(
                    LocalDateTime.Companion::class.java.classLoader,
                    LocalDateTime::class.java
                )!!
            )
        }
    }
}