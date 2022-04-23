package com.example.farfarawaylibrary.model.models

data class SwPeopleApiResponse(
    val count : Int,
    val next : String?,
    val previous : String?,
    val results : List<SwCompleteCharacter>
)
