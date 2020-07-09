package com.kostikum.kvartirka.di

import com.kostikum.kvartirka.AndroidApplication
import com.kostikum.kvartirka.ui.ListFragment
import com.kostikum.kvartirka.ui.PhotosFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)

    fun inject(listFragment: ListFragment)
    fun inject(photosFragment: PhotosFragment)
}
