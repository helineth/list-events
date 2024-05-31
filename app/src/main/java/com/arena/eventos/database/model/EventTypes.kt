package com.arena.eventos.database.model


data class EventType(
    val id: String = "",
    val title: String = "",
    val coverUrl: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val state: Boolean = false,
    val expositores: List<ExpositorType>? = null,
    val sponsors: List<SponsorTypes>? = null,
    val localization: String = "",
    val description: String = "",
    val diary: List<DiaryTypes> = emptyList(),
    val highlighted: Boolean? = null
)