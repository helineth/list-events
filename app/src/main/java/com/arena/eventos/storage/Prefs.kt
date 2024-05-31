package com.arena.eventos.storage

import android.content.Context
import android.content.SharedPreferences
import com.arena.eventos.database.model.EventType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Prefs(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE)

    fun saveEventType(eventType: EventType) {
        val editor = sharedPreferences.edit()
        val json = Json.encodeToString(eventType)
        editor.putString("eventType", json)
        editor.apply()
    }

    fun getEventType(): EventType? {
        val json = sharedPreferences.getString("eventType", null)
        return if (json == null) {
            null
        } else {
            Json.decodeFromString<EventType>(json)
        }
    }
}