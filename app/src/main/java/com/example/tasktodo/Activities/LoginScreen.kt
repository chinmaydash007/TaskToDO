package com.example.tasktodo.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasktodo.R
import com.example.tasktodo.Utils.PREF_KEYS
import com.example.tasktodo.Utils.showToast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginScreen : AppCompatActivity() {
    lateinit var textinput: TextInputLayout
    lateinit var loginBtn: MaterialButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPrefEditor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        textinput = findViewById(R.id.name)
        loginBtn = findViewById(R.id.button)
        sharedPrefEditor =
            getSharedPreferences(PREF_KEYS.shared_pref_name, Context.MODE_PRIVATE).edit()
        loginBtn.setOnClickListener {
            textinput.isErrorEnabled = false
            var username: String = textinput.editText?.text.toString()
            if (username.isEmpty()) {
                textinput.isErrorEnabled = true
                textinput.error = "Enter your username"
                return@setOnClickListener
            }
            sharedPrefEditor.putString(PREF_KEYS.username, username)
            sharedPrefEditor.apply()

            gotoMainActivity()


        }

    }

    private fun gotoMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
