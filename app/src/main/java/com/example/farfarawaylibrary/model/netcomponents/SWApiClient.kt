package com.example.farfarawaylibrary.model.netcomponents

import com.example.farfarawaylibrary.model.models.SwApiResponse
import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWApiClient {
    @GET("people/.json")
    suspend fun getAllCharacters(@Query("page") page : Int) : Response<SwApiResponse>

    //@GET("people/?search={character}")
    //suspend fun getCharacter(@Path("character") character : String) : Response<SwCompleteCharacter>
}