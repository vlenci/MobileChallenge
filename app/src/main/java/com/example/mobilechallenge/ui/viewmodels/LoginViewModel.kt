package com.example.mobilechallenge.ui.viewmodels

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: LoginService
) : ViewModel() {

    lateinit var navController: NavController

    val loginRepository = LoginRepositoryImpl(service)

    private var currentUiStateJob: Job? = null

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    var token = TokenResponse("", "")

    fun getToken() {
        if (username.value.isNullOrBlank() || password.value.isNullOrBlank()) {
            Log.e("login sem dados", "login sem dados")
        } else {
            currentUiStateJob?.cancel()
            currentUiStateJob = viewModelScope.launch {

                val loginModel = LoginModel(
                    username = username.value ?: "",
                    password = password.value ?: ""
                )

                val tokenResponse = loginRepository.getToken(loginModel)

                if (tokenResponse is Result.Success) {
                    navController.navigate("tree")
                } else {
                    Log.e("erro login", "Erro de login: ${Result}")
                }

            }

        }
    }

    fun setUsernameValue(value: String) {
        username.value = value
    }

    fun setPasswordValue(value: String) {
        password.value = value
    }
}