package com.example.mobilechallenge.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.model.LoginModel
import com.example.mobilechallenge.repositories.LoginRepository
import com.example.mobilechallenge.repositories.LoginRepositoryImpl
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
    private val repository: LoginRepository
) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _state = MutableStateFlow<LoginState<String>>(LoginState.Idle)
    val state = _state.asStateFlow()

    fun getToken() {
        viewModelScope.launch {
            val loginModel = LoginModel(
                username = username.value,
                password = password.value
            )

            _state.value = LoginState.Loading
            val result = repository.getToken(loginModel)
            _state.value = result
        }
    }

    fun updateState(newState: LoginState<String>) {
        _state.value = newState
    }

    fun setUsernameValue(value: String) {
        _username.value = value
    }

    fun setPasswordValue(value: String) {
        _password.value = value
    }
}