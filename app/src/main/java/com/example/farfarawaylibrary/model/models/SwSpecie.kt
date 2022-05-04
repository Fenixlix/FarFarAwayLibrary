package com.example.farfarawaylibrary.model.models

data class SwSpecie(
    val average_height :String = "",
    val average_lifespan :String = "",
    val classification :String = "",
    val created :String = "",
    val designation :String = "",
    val edited :String = "",
    val eye_colors :String = "",
    val hair_colors :String = "",
    val homeworld :String = "",
    val language :String = "",
    val name :String = "",
    val people : List<String> = emptyList(),
    val films : List<String> = emptyList(),
    val skin_colors :String = "",
    val url :String = "",
)
