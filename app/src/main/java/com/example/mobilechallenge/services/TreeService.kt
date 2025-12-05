package com.example.mobilechallenge.services

import com.example.mobilechallenge.model.TreeModel
import com.example.mobilechallenge.utils.ApiUrls
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TreeService {
    @GET(ApiUrls.TREE)
    suspend fun getTree(
        @Header("Authorization") token: String,
        @Query("site") siteId: Int
    ): Response<SiteResponse>
}

@Serializable
data class SiteResponse(
    val id: Int,
    val name: String,
    val revision: Int,
    val tree: List<AssetResponse>,
)

@Serializable
data class AssetResponse(
    val id: Int,
    @SerialName("refresh_setups") val refreshSetups: Boolean,
    @SerialName("asset_type") val assetType: Int?,
    @SerialName("original_asset_type") val originalAssetType: Int?,
    val group: String?,
    val criticality: String?,
    @SerialName("functional_location") val functionalLocation: String?,
    val status: Boolean?,
    val name: String,
    val tag: String?,
    val level: Int?,
    val order: Int,
    val parent: Int?,
    val site: Int?,
)