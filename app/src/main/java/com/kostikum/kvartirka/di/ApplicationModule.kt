package com.kostikum.kvartirka.di

import android.content.Context
import com.kostikum.kvartirka.AndroidApplication
import com.kostikum.kvartirka.repository.Repository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val baseUrl = "http://api.kvartirka.com/client/1.4/"

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(dataSource: Repository.Network): Repository = dataSource
}
