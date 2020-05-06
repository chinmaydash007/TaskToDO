package com.example.tasktodo.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktodo.R
import com.example.tasktodo.Utils.PREF_KEYS
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    var activityScape: CoroutineScope = CoroutineScope(Dispatchers.Main)
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        loadData()
    }

    fun loadData() {
        sharedPreferences = getSharedPreferences(PREF_KEYS.shared_pref_name, Context.MODE_PRIVATE)
        val isLoggedIn: Boolean = sharedPreferences.contains(PREF_KEYS.username)

        activityScape.launch {
            delay(1000)
            if (isLoggedIn) {
                var intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                var intent = Intent(this@SplashScreen, OnBoardScreen::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    override fun onPause() {
        activityScape.cancel()
        super.onPause()

    }
}
