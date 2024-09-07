package com.inmobixpress.inmobixpressmanager.ui.model

import androidx.annotation.DrawableRes

data class EmailAttachment(
    @DrawableRes val resId: Int,
    val contentDesc: String
)