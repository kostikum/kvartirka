package com.kostikum.kvartirka

import android.app.Application

class AndroidApplication : Application() {
    companion object {
        lateinit var instance: AndroidApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}