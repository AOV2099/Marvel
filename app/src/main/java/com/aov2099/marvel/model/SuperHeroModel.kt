package com.aov2099.marvel.model

import com.google.gson.annotations.SerializedName

const val CHARACTER_EXTRA_ID = "characterExtraId"
const val CHARACTER_EXTRA_NAME = "characterExtraName"
const val CHARACTER_EXTRA_IMAGE = "characterExtraImage"
const val CHARACTER_EXTRA_DESC = "characterExtraDesc"

const val GOOGLE_SIGN_IN = 100

//API CONSTANTS
const val API_KEY = "25f3fe39e3720334767cb43442d565eb"
const val API_TIMESTAMP = "1646083836"
const val API_HASH = "618b464cc5c67ff70c741794c01a761c"
const val API_LIMIT = "100"

data class SuperHeroInitialResponse (
    @SerializedName("status") var status : String,
    @SerializedName( "data" ) var responseData : ResponseData
)

data class ResponseData(
    @SerializedName( "limit" ) var limit: Int,
    @SerializedName( "total" ) var total: Int,
    @SerializedName( "results" ) var characters: List<SuperHero>
)

data class SuperHero(
    @SerializedName( "id" ) var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("thumbnail") var image: ResponseImage,
    //@SerializedName("comics") var comics: Comics,
    @SerializedName("urls") var urls: List<CharacterUrls>,
)

data class ResponseImage(
    @SerializedName("path") var url : String,
    @SerializedName("extension") var ext: String,
)

data class ComicsResponse(
    @SerializedName("status") var status : String,
    @SerializedName( "data" ) var responseData : ComicResponseData
)

data class ComicResponseData(
    @SerializedName( "limit" ) var limit: Int,
    @SerializedName( "total" ) var total: Int,
    @SerializedName( "results" ) var comics: List<Comic>
)

data class Comic(
    @SerializedName( "id" ) var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("thumbnail") var image: ResponseImage,

)

data class CharacterUrls (
    @SerializedName("type") var type: String,
    @SerializedName("url") var url: String
)

/*data class Comics(
    @SerializedName("available") var num: Int,
    @SerializedName("items") var comic: List<Comic>
)




)*/