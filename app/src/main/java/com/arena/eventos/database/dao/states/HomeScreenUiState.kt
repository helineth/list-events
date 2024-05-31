package com.arena.eventos.database.dao.states

import com.arena.eventos.database.model.EventType


data class HomeScreenUiState(
    val events: List<EventType> = emptyList()
)
