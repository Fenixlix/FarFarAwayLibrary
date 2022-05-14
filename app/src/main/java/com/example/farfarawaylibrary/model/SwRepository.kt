package com.example.farfarawaylibrary.model

import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.model.netcomponents.SWApiClient
import com.example.farfarawaylibrary.model.netcomponents.SafeCallResponse
import retrofit2.Response
import javax.inject.Inject

class SwRepository @Inject constructor(
    private val SWApi: SWApiClient
){
    // Get all characters that have the searched name in their name
    suspend fun getCharacterFromApi(name: String) : List<SwCompleteCharacter> {
        val response = safeApiCall { SWApi.getCharacter(name) }
        return if (response.callSuccessful) {
            val character = response.body.results
            character.ifEmpty {
                listOf(SwCompleteCharacter(name = "No found: $name"))
            }
        } else{
            listOf(SwCompleteCharacter(name = response.error.toString()))
        }
    }

    // Get all the characters by page
    suspend fun getCharacterByPage(page : Int) : List<SwCompleteCharacter>{
        val completeList: ArrayList<SwCompleteCharacter> = arrayListOf()
        val response = safeApiCall { SWApi.getAllCharacters(page) }
        if (response.callSuccessful) {
            completeList.addAll(response.body.results)
        }
        return completeList
    }

    // Get the planet name searched by Id
    suspend fun getPlanetName(id: Int) : String {
        val response = safeApiCall { SWApi.getPlanetById(id) }
        return if (response.callSuccessful) {
            val planet = response.body.name
            planet.ifEmpty { "Not found" }
        } else {
            response.error.toString()
        }
    }

    // Get the specie name searched by Id
    suspend fun getSpecieName(id: Int) : String {
        val response = safeApiCall { SWApi.getSpecieById(id) }
        return if (response.callSuccessful) {
            val name = response.body.name
            name.ifEmpty { "Not found" }
        } else {
            response.error.toString()
        }
    }

    // Get the starship name searched by Id
    suspend fun getStarshipName(id: Int) : String {
        val response = safeApiCall {SWApi.getStarshipById(id)}
        return if (response.callSuccessful) {
            val name = response.body.name
            name.ifEmpty { "Not found" }
        } else {
            response.error.toString()
        }
    }

    // Get the vehicle name searched by Id
    suspend fun getVehicleName(id: Int) : String {
        val response = safeApiCall {SWApi.getVehicleById(id)}
        return if (response.callSuccessful) {
            val name = response.body.name
            name.ifEmpty { "Not found" }
        } else {
            response.error.toString()
        }
    }

    // Get the film name searched by Id
    suspend fun getFilmName(id: Int) : String {
        val response = safeApiCall {SWApi.getFilmById(id)}
        return if (response.callSuccessful) {
            val name = response.body.title
            name.ifEmpty { "Not found" }
        } else {
            response.error.toString()
        }
    }

    private inline fun <T> safeApiCall(apiCall : () -> Response<T>) : SafeCallResponse<T> {
        return try {
            SafeCallResponse.successCall(apiCall.invoke())
        }catch (e : Exception){
            SafeCallResponse.failureCall(e)
        }
    }

}