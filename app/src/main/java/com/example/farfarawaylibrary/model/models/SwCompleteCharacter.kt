package com.example.farfarawaylibrary.model.models


data class SwCompleteCharacter(
    val name : String = "",
    val height : String = "",
    val mass : String = "",
    val hair_color : String = "",
    val skin_color : String = "",
    val eye_color : String = "",
    val birth_year : String = "",
    val gender : String = "",
    val homeworld : String = "",
    val films : List<String> = emptyList(),
    val species : List<String> = emptyList(),
    val vehicles : List<String> = emptyList(),
    val starships : List<String> = emptyList(),
    val created : String = "",
    val edited : String = "",
    val url : String = "",
)
