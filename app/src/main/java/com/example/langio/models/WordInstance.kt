package com.example.langio.models

data class WordInstance(
    val englishWord: String,
    val spanishWord: String,
    val incorrectSpanishWords: List<String>,
    val sampleOfUseEnglish: String,
    val sampleOfUseSpanish: String
)