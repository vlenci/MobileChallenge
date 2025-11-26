package com.example.mobilechallenge.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mobilechallenge.model.LoginModel
import com.example.mobilechallenge.repositories.LoginRepositoryImpl
import com.example.mobilechallenge.repositories.Result
import com.example.mobilechallenge.services.LoginService
import com.example.mobilechallenge.services.TokenResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: LoginService,
) : ViewModel() {

    val loginRepository = LoginRepositoryImpl(service)

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    var token = TokenResponse("", "")

    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()

    fun getToken() {
        viewModelScope.launch {
            val loginModel = LoginModel(
                username = username.value ?: "",
                password = password.value ?: ""
            )

            val result = loginRepository.getToken(loginModel)
            Log.d("username", loginModel.username)
            Log.d("password", loginModel.password)
            Log.d("result", result.toString())
            _state.value = state(result)
        }
    }

    private fun state(result: Result<TokenResponse>): Boolean {
        return when (result) {
            is Result.Error<*> -> false
            is Result.Success<*> -> true
        }
    }

    fun setUsernameValue(value: String) {
        _username.value = value
    }

    fun setPasswordValue(value: String) {
        _password.value = value
    }
}