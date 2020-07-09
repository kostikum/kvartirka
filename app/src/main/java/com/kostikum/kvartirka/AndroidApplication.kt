package com.kostikum.kvartirka

import android.app.Application
import com.kostikum.kvartirka.di.ApplicationComponent
import com.kostikum.kvartirka.di.ApplicationModule
import com.kostikum.kvartirka.di.DaggerApplicationComponent

class AndroidApplication : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}
