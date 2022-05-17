package com.example.farfarawaylibrary.viewmodel

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.farfarawaylibrary.R
import com.example.farfarawaylibrary.model.SwRepository
import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.model.rvcomponents.CharactersDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwCharacterViewModel @Inject constructor(
    val repo : SwRepository
) : ViewModel() {

    val progressBarEstate = MutableLiveData<Boolean>()

    private var _allCharacters = Pager(config = PagingConfig(20, 20, true,
            20), pagingSourceFactory = { CharactersDataSource(this) }).liveData

    val allCharacters = _allCharacters
    val oneCharacter = MutableLiveData<SwCompleteCharacter>()

    // Get a list of characters thar mach with the given name, and then take the first
    fun getCharacterFromApi(name: String) {
        viewModelScope.launch {
            progressBarEstate.value = true
            val charList = repo.getCharacterFromApi(name)
            oneCharacter.postValue(cleanData(charList.component1()))
            progressBarEstate.postValue(false)
        }
    }

    // Replace the urls for the actual names of the items
    private suspend fun cleanData(character : SwCompleteCharacter) : SwCompleteCharacter{
        val planetId = getId(character.homeworld)
        val planetName = viewModelScope.async {
            if (planetId != null)repo.getPlanetName(planetId) else ""
        }

        val filmsId = getListOfId(character.films)
        val filmNames = viewModelScope.async {
            getNameList(filmsId){repo.getFilmName(it)}
        }

        val speciesId = getListOfId(character.species)
        val specieNames = viewModelScope.async {
            getNameList(speciesId){repo.getSpecieName(it)}
        }

        val starshipsId = getListOfId(character.starships)
        val starshipNames = viewModelScope.async {
            getNameList(starshipsId){repo.getStarshipName(it)}
        }

        val vehiclesId = getListOfId(character.vehicles)
        val vehicleNames = viewModelScope.async {
            getNameList(vehiclesId){repo.getVehicleName(it)}
        }

        return character.copy(homeworld = planetName.await(),
            films = filmNames.await(), species = specieNames.await(),
            starships = starshipNames.await(), vehicles = vehicleNames.await())
    }

    // Utility function to get the names of the given id and direction
    private suspend fun getNameList(idList : List<Int>, getName : suspend (id : Int) -> String) : List<String> {
        return if (idList.isNotEmpty()){
            val names = viewModelScope.async { idList.map { getName(it) } }
            names.await()
        } else {
            emptyList()
        }
    }

    // Utility function to get the Id of a given url string
    private fun getId(urls : String) : Int? {
        return if (urls.isNotEmpty()){
            val segmentedUrl = urls.split("/")
            segmentedUrl[segmentedUrl.size-2].toInt()
        } else {
            null
        }
    }

    // Utility function to get the Id of a given list of url string
    private fun getListOfId(urls : List<String>) : ArrayList<Int> {
        return if (urls.isNotEmpty()){
            val segmentedUrl = urls.map {
                it.split("/")
                .filter { urlSection ->
                    urlSection.contains("[1-9*]".toRegex())
                }
            }
            val idList = arrayListOf<Int>()
            segmentedUrl.forEach {
                idList.add(it[0].toInt())
            }
            idList
        } else {
           arrayListOf()
        }
    }

    // Alert dialog for search a character
    fun searchDialog(
        context: Context,
        root: ViewGroup?,
        function: (name: String) -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_alert_dialog_layout, root)
        builder.setView(view)

        val searchMessageScreen = builder.create()

        val searchBtn = view.findViewById<Button>(R.id.ad_btn_ok)
        val searchName = view.findViewById<EditText>(R.id.ad_et_characterName)

        searchBtn.setOnClickListener {
            if (searchName.text.toString().isNotEmpty()) {
                searchMessageScreen.dismiss()
                function(searchName.text.toString())        // lambda function
            } else {
                Toast.makeText(context, "empty name", Toast.LENGTH_SHORT).show()
            }
        }

        if (searchMessageScreen.window != null) {
            searchMessageScreen.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        searchMessageScreen.show()
    }

}