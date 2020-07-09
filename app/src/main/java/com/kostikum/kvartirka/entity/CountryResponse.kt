package com.kostikum.kvartirka.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryResponse(
    val countries: List<Country>
)
