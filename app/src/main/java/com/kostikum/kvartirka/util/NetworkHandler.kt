package com.kostikum.kvartirka.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
    @Inject
    constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnectedOrConnecting
}

val Context.networkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo
