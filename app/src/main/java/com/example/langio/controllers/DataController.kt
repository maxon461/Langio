package com.example.langio.controllers

import com.example.langio.models.WordData

object DataController {

    fun readWordsFromFile(fileName: String): List<WordData> {
        val wordsList = mutableListOf<WordData>()

        val inputStream = DataController::class.java.classLoader?.getResourceAsStream(fileName)
        if (inputStream != null) {
            inputStream.bufferedReader().forEachLine { line ->
                val parts = line.split(";")

                if (parts.size == 13) {
                    val chapterNumber = parts[0].trim().toIntOrNull() ?: 0
                    val level = parts[1].trim().toIntOrNull() ?: 0
                    val englishWord = parts[2].trim()
                    val spanishWord = parts[3].trim()
                    val incorrectSpanishWords = parts.drop(4).take(6).map { it.trim() }
                    val audioPath = parts[10].trim().takeIf { it.isNotEmpty() }
                    val videoPath = parts[11].trim().takeIf { it.isNotEmpty() }
                    val imagePath = parts[12].trim().takeIf { it.isNotEmpty() }

                    wordsList.add(
                        WordData(
                            chapterNumber,
                            level,
                            englishWord,
                            spanishWord,
                            incorrectSpanishWords,
                            audioPath,
                            videoPath,
                            imagePath
                        )
                    )
                }
            }
        } else {
            throw IllegalArgumentException("File: '$fileName' not found")
        }

        return wordsList
    }
}

//TODO() READ FILES WITH USER'S STATS AND DATA (number of hints, pfp, username, joined date, learned_words, actual_level, minutes_spent_in_app, actual_level)
//TODO() IDK IF WE SHOULD REMEMBER USER SCORE ON EACH LEVEL (IT'D BE KILLER)