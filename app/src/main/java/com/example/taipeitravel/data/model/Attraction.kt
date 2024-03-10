package com.example.taipeitravel.data.model
import com.google.gson.annotations.SerializedName

data class AttractionResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("data")
    val attractions: List<Attraction>
)

data class Attraction(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("open_time")
    val openTime: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("tel")
    val tel: String,
    @SerializedName("official_site")
    val officialSite: String,
    @SerializedName("images")
    val images: List<Image>
)

data class Image(
    @SerializedName("src")
    val src: String
)