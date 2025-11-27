package com.example.mobilechallenge.services

import com.example.mobilechallenge.model.LoginModel
import com.example.mobilechallenge.utils.ApiUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST(ApiUrls.LOGIN)
    suspend fun getToken(@Body loginModel: LoginModel): Response<TokenResponse>

}

@Serializable
data class TokenResponse(
    @SerialName("refresh") val refresh: String,
    @SerialName("access") val access: String,
)



