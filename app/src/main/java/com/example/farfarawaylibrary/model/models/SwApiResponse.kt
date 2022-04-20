package com.example.farfarawaylibrary.model.models

data class SwApiResponse(
    private val count : Int,
    private val next : String,
    private val previous : String?,
    val results : List<SwCompleteCharacter>
)
