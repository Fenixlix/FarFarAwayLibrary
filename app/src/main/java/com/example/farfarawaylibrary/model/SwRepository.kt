package com.example.farfarawaylibrary.model

import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.model.netcomponents.SWApiClient
import javax.inject.Inject

class SwRepository @Inject constructor(
    private val SWApi: SWApiClient
){
    // Get all characters that have the searched name in their name
    suspend fun getCharacterFromApi(name: String) : List<SwCompleteCharacter> {
        val response = SWApi.getCharacter(name)
        return if (response.isSuccessful) {
            val character = response.body()!!.results
            character.ifEmpty {
                listOf(SwCompleteCharacter(name = "No found: $name"))
            }
        } else {
            listOf(SwCompleteCharacter(name = "Something Go Wrong"))
        }
    }

    // Get all the characters by page
    suspend fun getCharacterByPage(page : Int) : List<SwCompleteCharacter>{
        val completeList: ArrayList<SwCompleteCharacter> = arrayListOf()
        val response = SWApi.getAllCharacters(page)
        if (response.isSuccessful) {
            completeList.addAll(response.body()?.results!!)
        }
        return completeList
    }

    // Get the planet name searched by Id
    suspend fun getPlanetName(id: Int) : String {
        val response = SWApi.getPlanetById(id)
        return if (response.isSuccessful) {
            val planet = response.body()!!.name
            planet.ifEmpty { "Not found" }
        } else {
            "Something Go Wrong"
        }
    }

    // Get the specie name searched by Id
    suspend fun getSpecieName(id: Int) : String {
        val response = SWApi.getSpecieById(id)
        return if (response.isSuccessful) {
            val name = response.body()!!.name
            name.ifEmpty { "Not found" }
        } else {
            "Something Go Wrong"
        }
    }

    // Get the starship name searched by Id
    suspend fun getStarshipName(id: Int) : String {
        val response = SWApi.getStarshipById(id)
        return if (response.isSuccessful) {
            val name = response.body()!!.name
            name.ifEmpty { "Not found" }
        } else {
            "Something Go Wrong"
        }
    }

    // Get the vehicle name searched by Id
    suspend fun getVehicleName(id: Int) : String {
        val response = SWApi.getVehicleById(id)
        return if (response.isSuccessful) {
            val name = response.body()!!.name
            name.ifEmpty { "Not found" }
        } else {
            "Something Go Wrong"
        }
    }

    // Get the film name searched by Id
    suspend fun getFilmName(id: Int) : String {
        val response = SWApi.getFilmById(id)
        return if (response.isSuccessful) {
            val name = response.body()!!.title
            name.ifEmpty { "Not found" }
        } else {
            "Something Go Wrong"
        }
    }

}