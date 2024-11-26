package com.inmobixpress.inmobixpressmanager.ui.utils

import java.text.DecimalFormat

private const val MONEY_FORMAT = "###,###,##0.00"
private const val MONEY_FORMAT_NO_CENTS = "###,###,###"

fun String.priceFormat(): String = "%,d".format(this.toInt())

fun String.bathroomFormat(): String {
    try {
        if (this.split(".")[1].toInt() == 0) {
            return "${this.toDouble().toInt()}"
        } else {
            return "${this.split(".")[0]} 1/2"
        }
    } catch (_: NumberFormatException) {
        return this
    }
}

fun Float.toMoneyFormat(
    removeTrailingZeroes: Boolean = false,
): String {
    val format = if (removeTrailingZeroes && (this % 1 == 0.0f)) DecimalFormat(MONEY_FORMAT_NO_CENTS)
    else DecimalFormat(MONEY_FORMAT)

    return format.format(this)
}

fun String.formatNavRoute(): String {
    val value = this.replace("$", ".")
    return if (value.contains("?")) value.split("?")[0] else value
}