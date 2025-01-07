package com.example.langio.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.FileReader
import java.time.LocalDate

data class UserData(
    var username: String,
    @SerializedName("join_date") var joinDate: String,
    @SerializedName("learned_words") var learnedWords: Int,
    @SerializedName("unlocked_level") var unlockedLevel: Int,
    @SerializedName("minutes_spent_in_app") var minutesSpent: Long,
    @SerializedName("hints_remaining") var hintsRemaining: Int,
    @SerializedName("daily_reward_streak") var dailyRewardStreak: Int,
    @SerializedName("is_daily_reward_taken") var isDailyRewardTaken: Boolean
) {
    companion object {
        fun loadFromJson(context: Context, fileName: String): UserData? {
            return try {
                val file = File(context.filesDir, fileName)
                if (file.exists()) {
                    val reader = FileReader(file)
                    Gson().fromJson(reader, UserData::class.java).also { reader.close() }
                } else {
                    val defaultUserData = UserData(
                        username = "username",
                        joinDate = LocalDate.now().toString(),
                        learnedWords = 0,
                        unlockedLevel = 1,
                        minutesSpent = 0,
                        hintsRemaining = 5,
                        dailyRewardStreak = 1,
                        isDailyRewardTaken = false
                    )
                    saveToJson(context, fileName, defaultUserData)
                    defaultUserData
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }


        fun saveToJson(context: Context, fileName: String, userData: UserData) {
            try {
                val file = File(context.filesDir, fileName)
                file.writeText(Gson().toJson(userData))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
