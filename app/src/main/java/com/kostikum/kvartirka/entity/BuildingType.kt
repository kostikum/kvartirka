package com.kostikum.kvartirka.entity

import com.kostikum.kvartirka.AndroidApplication
import com.kostikum.kvartirka.R

enum class BuildingType {
    FLAT {
        override fun provideResourceInt() = R.string.building_type_flat
    },
    COTTAGE {
        override fun provideResourceInt() = R.string.building_type_cottage
    },
    UNKNOWN {
        override fun provideResourceInt() = R.string.building_type_unknown
    };

    private val appInstance = AndroidApplication.instance
    fun getResName() = appInstance.getString(provideResourceInt())
    abstract fun provideResourceInt(): Int
}
