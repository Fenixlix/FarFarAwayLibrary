package com.example.farfarawaylibrary.model.netcomponents

import com.example.farfarawaylibrary.model.models.SwPeopleApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SWApiClient {
    @GET("people/")
    suspend fun getAllCharacters(@Query("page") page : Int) : Response<SwPeopleApiResponse>

    @GET("people/")
    suspend fun getCharacter(@Query("search") search : String) : Response<SwPeopleApiResponse>
}