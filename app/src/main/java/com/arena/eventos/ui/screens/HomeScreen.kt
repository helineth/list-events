package com.arena.eventos.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.arena.eventos.database.model.EventType
import com.arena.eventos.database.model.NewsTypes
import com.arena.eventos.repository.EventRepositoryImpl
import com.arena.eventos.repository.NewRepositoryImpl
import com.arena.eventos.ui.components.homenews.HomeNews
import com.arena.eventos.ui.components.slidehome.CurrentActiveEventBanner
import com.arena.eventos.ui.components.slidehome.ListOfActiveEvents
import com.arena.eventos.ui.effects.ShimmerEffect

@SuppressLint("RememberReturnType")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickSeeEvent: (event: EventType) -> Unit = {},
    navController: NavHostController,
) {

    val eventsList = remember { mutableStateListOf<EventType>() }
    val highlightedEvent = remember { mutableStateListOf<EventType>() }
    val listOfNews = remember { mutableStateListOf<NewsTypes>() }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        val eventData = EventRepositoryImpl()
        val newsData = NewRepositoryImpl()
//        eventData.getAllEvents(
//            onSuccess = { result ->
//                result.map { event->
//                    if (event.highlighted == true){
//                        //highlightedEvent.clear()
//                        highlightedEvent.add(event)
//                    }
//                }
//                //eventsList.clear()
//                eventsList.addAll(result)
//                isLoading.value = false
//            },
//            onFailure = {
//            }
//        )

        try {
            val events = eventData.getAllEvents()
            eventsList.addAll(events)
            isLoading.value = false
        } catch (e: Exception) {
            isLoading.value = false
        }

        newsData.getAllNews(
            onSuccess = { result ->
                //listOfNews.clear()
                val slicedArray = result.reversed().slice(0..1)
                listOfNews.addAll(slicedArray)
                isLoading.value = false
            },
            onFailure = {
                isLoading.value = false
            }
        )

    }

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp)
    ) {
        if (isLoading.value) {
            ShimmerEffect()
        } else {
            CurrentActiveEventBanner(onClickSeeCurrentEvent = onClickSeeEvent, highlightedEvent)
            Spacer(Modifier.height(16.dp))
            ListOfActiveEvents(
                eventsList,
                navController,
            )
            Spacer(Modifier.height(20.dp))
            HomeNews(listOfNews, navController)
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}

fun HomeScreen() {
    TODO("Not yet implemented")
}