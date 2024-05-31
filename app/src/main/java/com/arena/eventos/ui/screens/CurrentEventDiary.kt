package com.arena.eventos.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arena.eventos.database.model.DiaryTypes
import com.arena.eventos.database.model.DiaryValue
import com.arena.eventos.database.model.EventType
import com.arena.eventos.repository.EventRepositoryImpl
@Composable
fun CurrentEventDiary(
    modifier: Modifier = Modifier,
    eventId: String
) {

    val currentEvent = remember { mutableStateOf<EventType?>(null) }
    LaunchedEffect(true) {
        val eventData = EventRepositoryImpl()
        eventData.getEventById(eventId,
            onSuccess = { event ->
                currentEvent.value = event
            },
            onFailure = {
            }
        )
    }
    val event = currentEvent.value
    val diaries = event?.diary ?: emptyList()
    val total: List<DiaryTypes> = diaries.sortedByDescending { it.date }

    Column(
        modifier
            .padding(vertical = 16.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxHeight(Float.POSITIVE_INFINITY),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(total) { elem ->
                DiaryCard(
                    modifier = Modifier.weight(1f),
                    date = elem.date,
                    diary = elem.diary
                )
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}


@Composable
fun DiaryCard(
    modifier: Modifier = Modifier,
    date: String,
    diary: List<DiaryValue>
) {
    val appColor = Color(0xFFFF4306)

    val orderedList = diary.sortedBy { it.time }
    Column(
        //verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Row {
            Text(
                text = date,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = appColor
            )
        }
        orderedList.map { data ->
            Row {
                Text(
                    text = data.time,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(bottom = 10.dp, start = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(16.dp))
                Column {
                    Text(
                        text = data.description,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(bottom = 3.dp, start = 12.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                    if (data.local.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                Icons.Rounded.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFFBEBEBE),
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text(
                                    text = data.local,
                                    color = Color(0xFF717171),
                                )
                            }
                        }
                    }

                }

            }
        }

    }
}

@Preview
@Composable
fun PreviewCurrentEventDiary() {
    ExpositorScreen()
}




