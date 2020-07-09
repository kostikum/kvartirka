package com.kostikum.kvartirka.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlatsResponse(
    val flats: List<Flat>
)
