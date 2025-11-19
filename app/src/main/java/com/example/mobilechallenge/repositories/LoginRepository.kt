package com.example.mobilechallenge.repositories

import com.example.mobilechallenge.model.LoginModel
import com.example.mobilechallenge.services.LoginService
import com.example.mobilechallenge.services.TokenResponse
import retrofit2.HttpException

interface LoginRepository {

    suspend fun getToken(loginModel: LoginModel): Result<TokenResponse>

}

class LoginRepositoryImpl(
    private val loginService: LoginService
) : LoginRepository {
    override suspend fun getToken(loginModel: LoginModel): Result<TokenResponse> {
        return try {
            val apiToken = loginService.getToken(loginModel)
            Result.Success(TokenResponse(apiToken.refresh, apiToken.access))
        } catch (e: HttpException) {
            Result.Error(e)
        }
    }

}