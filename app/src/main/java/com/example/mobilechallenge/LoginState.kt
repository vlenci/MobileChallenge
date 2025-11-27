package com.example.mobilechallenge

sealed class LoginState<out T> {
    data class Success<out T>(val data: T) : LoginState<T>()

    data class Error(val message: String, val code: Int? = null) : LoginState<Nothing>()

    data object Loading : LoginState<Nothing>()

    data object Idle : LoginState<Nothing>()
}