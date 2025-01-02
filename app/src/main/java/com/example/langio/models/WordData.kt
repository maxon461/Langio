package com.example.langio.models

data class WordData(
    val chapterNumber: Int,
    val level: Int,
    val englishWord: String,
    val spanishWord: String,
    val incorrectSpanishWords: List<String>,
    val audioPath: String?,
    val videoPath: String?,
    val imagePath: String?
)