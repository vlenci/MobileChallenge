package com.example.mobilechallenge.repositories

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val data: Throwable) : Result<T>()
}

fun <T> Result<T>.isSuccessful(): Boolean {
    return this is Result.Success
}


fun <T> Result<T>.errorMessage(): String? {
    return if (this is Result.Error) this.data.message else null
}
