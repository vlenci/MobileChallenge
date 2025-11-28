package com.example.mobilechallenge.repositories

import android.content.Context
import com.example.mobilechallenge.LoginState
import com.example.mobilechallenge.model.LoginModel
import com.example.mobilechallenge.services.LoginService
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import retrofit2.HttpException

interface LoginRepository {

    suspend fun getToken(loginModel: LoginModel): LoginState<String>

}

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    @ApplicationContext private val context: Context
) : LoginRepository {
    override suspend fun getToken(loginModel: LoginModel): LoginState<String> {
        return try {
            val response = loginService.getToken(loginModel)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    LoginState.Success(body.access)
                } else {
                    LoginState.Error("Body vazio")
                }
            } else {
                LoginState.Error("Erro na API", response.code())
            }
        } catch (e: HttpException) {
            LoginState.Error(e.message ?: "Invalid Credentials", e.code())
        }
    }
}