package com.example.langio.models

data class Level(
    val levelNumber: Int,
    val chapter: Int,
    val words: MutableList<WordInstance>
)