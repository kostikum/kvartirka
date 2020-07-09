package com.kostikum.kvartirka.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Prices(
    val day: Int,
    val night: Int,
    val hour: Int
)
