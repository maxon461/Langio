package com.example.langio.controllers

import android.content.Context
import com.example.langio.models.Level
import com.example.langio.models.WordInstance
import java.io.InputStreamReader

object DataController {

    private val levelsMap = mutableMapOf<Int, Level>()

    fun readWordsFromFile(context: Context, fileName: String): Map<Int, Level> {
        if (levelsMap.isNotEmpty()) {
            return levelsMap
        }

        try {
            val resourceId = context.resources.getIdentifier(fileName, "raw", context.packageName)
            if (resourceId == 0) {
                throw IllegalArgumentException("File '$fileName' not found in raw resources.")
            }

            val inputStream = context.resources.openRawResource(resourceId)
            val reader = InputStreamReader(inputStream)

            reader.buffered().forEachLine { line ->
                val parts = line.split("|").map { it.trim() }
                if (parts.size == 10) {
                    val chapter = parts[0].toInt()
                    val level = parts[1].toInt()
                    val englishWord = parts[2]
                    val spanishWord = parts[3]
                    val incorrectSpanishWords = parts[4].trim('[', ']').split(";").map { it.trim() }
                    val audioPath = parts[5]
                    val videoPath = parts[6]
                    val imagePath = parts[7]
                    val sampleOfUseEnglish = parts[8]
                    val sampleOfUseSpanish = parts[9]

                    val wordInstance = WordInstance(
                        englishWord,
                        spanishWord,
                        incorrectSpanishWords,
                        audioPath,
                        videoPath,
                        imagePath,
                        sampleOfUseEnglish,
                        sampleOfUseSpanish)

                    val levelData = levelsMap.getOrPut(level) {
                        Level(level, chapter, mutableListOf())
                    }

                    levelData.words.add(wordInstance)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return levelsMap
    }

    fun getWordsForLevel(level: Int): List<WordInstance>? {
        return levelsMap[level]?.words
    }
}
