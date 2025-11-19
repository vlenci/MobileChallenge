package com.example.mobilechallenge.services

import com.example.mobilechallenge.model.LoginModel
import com.example.mobilechallenge.utils.ApiUrls
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST(ApiUrls.LOGIN)
    suspend fun getToken(@Body loginModel: LoginModel): TokenResponse

}

data class TokenResponse(
    val refresh: String,
    val access: String,
)

data class TokenAccess(
    val access: String
)



