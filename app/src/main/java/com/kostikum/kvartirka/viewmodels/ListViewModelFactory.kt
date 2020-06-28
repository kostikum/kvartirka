package com.kostikum.kvartirka.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListViewModelFactory(private val activity: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(activity.application) as T
        }
        throw IllegalArgumentException("Unable to construct view model!")
    }
}
