package com.arena.eventos.database.model

data class DiaryTypes(
    val date: String = "",
    val diary: List<DiaryValue> = emptyList(),
    val time: String = ""
)
data class DiaryValue(
    val description: String = "",
    val time: String = "",
    val local: String = ""
)