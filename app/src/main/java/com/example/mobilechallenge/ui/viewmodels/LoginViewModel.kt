package com.example.mobilechallenge.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _username = mutableStateOf("")
    val username = _username.value

    private val _password = mutableStateOf("")
    val password = _password.value

    fun getToken(context: Context) {
        if (username.isEmpty() || password.isEmpty()) {

        } else {

        }
    }
}