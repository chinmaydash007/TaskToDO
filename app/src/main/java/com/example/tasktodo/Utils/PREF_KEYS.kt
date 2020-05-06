package com.example.tasktodo.Utils

import android.content.Context
import android.util.Log
import android.widget.Toast

object PREF_KEYS {
    const val shared_pref_name: String = "MY_SHARED_PREF"
    const val username: String = "USERNAME"
    const val is_logged_in: String = "LOGGED"
    const val is_first_time: String = "FIRST_TIME"

}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun logger(message: String) {
    Log.d("mytag", message)
}