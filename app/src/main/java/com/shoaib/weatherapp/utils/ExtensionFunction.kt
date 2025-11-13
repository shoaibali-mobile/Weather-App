package com.shoaib.weatherapp.utils

import android.content.res.Resources
import com.shoaib.weatherapp.R

private const val OPEN_WEATHER_ICON_BASE_URL = "https://openweathermap.org/img/wn"
private const val DEFAULT_ICON_CODE = "01d"
private const val ICON_SIZE_SUFFIX = "@2x.png"

fun String?.toIconUrl(): String {
    val iconCode = this?.takeIf { it.isNotBlank() } ?: DEFAULT_ICON_CODE
    return "$OPEN_WEATHER_ICON_BASE_URL/$iconCode$ICON_SIZE_SUFFIX"
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.getEmailError(resources: Resources): String? {
    return when {
        this.isEmpty() -> resources.getString(R.string.error_email_required)
        !this.isValidEmail() -> resources.getString(R.string.error_email_invalid)
        else -> null
    }
}

fun String.getPasswordError(resources: Resources): String? {
    return when {
        this.isEmpty() -> resources.getString(R.string.error_password_required)
        this.length < 6 -> resources.getString(R.string.error_password_min_length)
        else -> null
    }
}