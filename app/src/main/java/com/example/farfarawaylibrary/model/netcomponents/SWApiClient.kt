package com.example.farfarawaylibrary.model.netcomponents

import com.example.farfarawaylibrary.model.models.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWApiClient {
    @GET("people/")
    suspend fun getAllCharacters(@Query("page") page : Int) : Response<SwPeopleApiResponse>

    @GET("people/")
    suspend fun getCharacter(@Query("search") search : String) : Response<SwPeopleApiResponse>

    @GET("planets/{id}")
    suspend fun getPlanetById(@Path("id") id : Int) : Response<SwPlanet>

    @GET("films/{id}")
    suspend fun getFilmById(@Path("id") id : Int) : Response<SwFilm>

    @GET("species/{id}")
    suspend fun getSpecieById(@Path("id") id : Int) : Response<SwSpecie>

    @GET("starships/{id}")
    suspend fun getStarshipById(@Path("id") id : Int) : Response<SwStarship>

    @GET("vehicles/{id}")
    suspend fun getVehicleById(@Path("id") id : Int) : Response<SwVehicle>

}