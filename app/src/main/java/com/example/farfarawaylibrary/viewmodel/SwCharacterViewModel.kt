package com.example.farfarawaylibrary.viewmodel

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farfarawaylibrary.R
import com.example.farfarawaylibrary.model.models.SwCompleteCharacter
import com.example.farfarawaylibrary.model.models.SwSimpleCharacter
import com.example.farfarawaylibrary.model.netcomponents.SWApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwCharacterViewModel @Inject constructor(
    private val SWApi: SWApiClient
) : ViewModel() {

    private val _allCharacters = MutableLiveData<List<SwCompleteCharacter>>()

    val progressBarEstate = MutableLiveData<Boolean>()

    var allCharacters = MutableLiveData<List<SwSimpleCharacter>>()
    var oneCharacter = MutableLiveData<SwCompleteCharacter>()

    // todo: add the safe boundaries for an empty list of characters

    fun getCharacterFromApi(name: String) {
        viewModelScope.launch {
            progressBarEstate.postValue(true)
            val response = SWApi.getCharacter(name)
            if (response.isSuccessful) {
                val character = response.body()?.results
                _allCharacters.value = character!!
                oneCharacter.postValue(_allCharacters.value?.component1())
            }
            progressBarEstate.postValue(false)
        }
    }

    private fun simpleCharacterList() {
        var x = 1
        val allC =
            _allCharacters.value?.map {
                SwSimpleCharacter(id = x++, name = it.name, birth_year = it.birth_year)
            }
        if (allC != null) allCharacters.value = allC!!
    }

    fun onCreate() {
        viewModelScope.launch {
            val completeList: ArrayList<SwCompleteCharacter> = arrayListOf()
            var haveMore = true
            var pointer = 1
            progressBarEstate.postValue(true)
            while (haveMore) {
                val response = SWApi.getAllCharacters(pointer++)
                if (response.isSuccessful) {
                    haveMore = !response.body()?.next.isNullOrEmpty()
                    completeList.addAll(response.body()?.results!!)
                    _allCharacters.value = completeList
                }
            }
            progressBarEstate.postValue(false)
            simpleCharacterList()
        }
    }

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