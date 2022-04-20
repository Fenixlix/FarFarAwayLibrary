package com.example.farfarawaylibrary.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.model.models.SwSimpleCharacter
import com.example.farfarawaylibrary.model.netcomponents.SWApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwCharacterViewModel @Inject constructor(
    private val SWApi : SWApiClient
) : ViewModel() {

    private val _allCharacters = MutableLiveData<List<SwCompleteCharacter>>()

    var allCharacters = MutableLiveData<List<SwSimpleCharacter>>()

    private fun simpleCharacterList() {
        var x = 1
        val allC =
            _allCharacters.value?.map{
                SwSimpleCharacter(id = x++,name = it.name, birth_year = it.birth_year)
            }
        if (allC!=null) allCharacters.value = allC!!
    }

    fun onCreate() {
        viewModelScope.launch{
            val completeList : ArrayList<SwCompleteCharacter> = arrayListOf()
            for (x in 1..2){
                val response = SWApi.getAllCharacters(x)
                if (response.isSuccessful) {
                    completeList.addAll(response.body()?.results!!)
                    _allCharacters.value = completeList
                    simpleCharacterList()
                }
            }
        }
    }

}