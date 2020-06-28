package com.kostikum.kvartirka.util

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Fragment.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this.activity as AppCompatActivity, permission)
