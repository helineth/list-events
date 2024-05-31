package com.arena.eventos.database.model

data class NewsTypes(
    val id: String = "",
    val title:  String = "",
    val body: String = "",
    val headlineTitle: String = "",
    val coverUrl: String = "",
    val covers: List<String> = emptyList(),
)
