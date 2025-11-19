package com.example.mobilechallenge.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mobilechallenge.repositories.LoginRepositoryImpl
import com.example.mobilechallenge.services.LoginService
import com.example.mobilechallenge.services.TokenResponse

class LoginViewModel(
    private val service: LoginService
) : ViewModel() {



    private val loginRepository = LoginRepositoryImpl(service)
    private val _username = mutableStateOf("")
    val username = _username.value

    private val _password = mutableStateOf("")
    val password = _password.value

    var token = TokenResponse("", "")

    fun getToken(context: Context) {
        if (username.isEmpty() || password.isEmpty()) {

        } else {

        }
    }

    fun setUsernameValue(value: String) {
        _username.value = value
    }

    fun setPasswordValue(value: String) {
        _password.value = value
    }
}