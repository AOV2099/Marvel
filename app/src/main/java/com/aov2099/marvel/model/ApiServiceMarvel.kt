package com.aov2099.marvel.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiServiceMarvel {
    @GET
    suspend fun getCharacters(@Url URL: String ): Response< SuperHeroInitialResponse >

    @GET
    suspend fun getComisByCharacter(@Url URL: String ): Response< ComicsResponse >
}