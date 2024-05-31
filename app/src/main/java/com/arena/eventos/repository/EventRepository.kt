package com.arena.eventos.repository

import com.arena.eventos.database.model.EventType


interface EventRepository {
    suspend fun getAllEvents() : List<EventType>
}