package com.arena.eventos.database.dao

import com.arena.eventos.database.model.EventType


interface EventDao {
    fun getAllEvents(): List<EventType>
}