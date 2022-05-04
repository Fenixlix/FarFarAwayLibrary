package com.example.farfarawaylibrary.model.models

data class SwPeopleApiResponse(
    val info : Info,
    val results : List<SwCompleteCharacter>
) {
    data class Info(
        val count : Int,
        val next : String?,
        val previous : String?
        )
}
