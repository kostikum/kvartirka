package com.kostikum.kvartirka.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(val id: Int, val name: String)
