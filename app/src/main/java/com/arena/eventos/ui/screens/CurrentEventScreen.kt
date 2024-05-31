package com.arena.eventos.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.CurrentEventDiaryScreen
import com.arena.eventos.ListOfExpositores
import com.arena.eventos.R
import com.arena.eventos.database.model.EventType
import com.arena.eventos.repository.EventRepositoryImpl
import com.arena.eventos.ui.components.cards.DescriptiveCard
import com.arena.eventos.ui.components.cards.EventCard
import com.arena.eventos.ui.effects.ShimmerEffect
import com.arena.service.Helper
import java.text.SimpleDateFormat

@SuppressLint("RememberReturnType")
@Composable
fun CurrentEventScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    eventId: String

) {

    val isLoading = remember { mutableStateOf(true) }
    val currentEvent = remember { mutableStateOf<EventType?>(null) }
    val currentDate = remember { mutableStateOf<String>("") }
    LaunchedEffect(true) {
        val eventData = EventRepositoryImpl()
        eventData.getEventById(eventId,
            onSuccess = { event ->
                val startDate = event.startDate
                val endDate = event.endDate

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val startDay = sdf.parse(startDate)
                val endDay = sdf.parse(endDate)

                val month = Helper.MONTHS[startDay.month - 1]
                val firstDay = startDay.date
                val lastDay = endDay.date

                currentDate.value = "${firstDay}-${lastDay}, ${month}, 2023"
                currentEvent.value = event
                isLoading.value = false
            },
            onFailure = {
            }
        )
    }
    val event = currentEvent.value

    val onClickSeeExpositores: () -> Unit = {
        if (event != null) {
            navController.navigate("${ListOfExpositores.route}/${event.id}")
        }
    }

    val onClickSeeEventDiary: () -> Unit = {
        if (event != null) {
            navController.navigate("${CurrentEventDiaryScreen.route}/${event.id}")
        }
    }

    var expanded by remember { mutableStateOf(false) }
    val appColor = Color(0xFFFF4306)

    if (isLoading.value) {
        ShimmerEffect()
    } else {
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            val aboutColor = Color(0xFF3A3939)
            var about = event?.description
            val aboutText = if (expanded) about else about?.take(6)

            Box(
                Modifier
                    .padding(1.dp)
                    .fillMaxSize()
            ) {
                Box(
                    Modifier
                        .background(Color.White)
                        .padding(10.dp, 0.dp)
                        .clip(shape = RoundedCornerShape(5.dp))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column() {
                        Row(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            if (event != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(event.coverUrl)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(R.drawable.placeholderimage),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        if (event != null) {
                            Text(
                                text = event.title,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                maxLines = 2
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        if (event != null) {
                            DescriptiveCard(
                                Icons.Rounded.CalendarMonth,
                                currentDate.value,
                                "Luanda"
                            )
                        }
                        event?.localization?.let {
                            DescriptiveCard(
                                Icons.Rounded.LocationOn,
                                "ZEE",
                                it
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Sobre o evento",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Row(
                            Modifier
                                .padding(vertical = 10.dp)
                        ) {

                            val lerMaisText = if (expanded) "ler menos" else "ler mais"
                            ClickableText(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = aboutColor,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 18.sp
                                        )
                                    ) {
                                        if (aboutText != null) {
                                            append(aboutText)
                                        }
                                    }
                                    if (about != null) {
                                        if (about.length > 200) {
                                            withStyle(style = SpanStyle(
                                                color = appColor,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                            ) {
                                                append(" $lerMaisText")
                                            }
                                        }
                                    }
                                },
                                onClick = {
                                    expanded = !expanded
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column() {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                EventCard(
                                    modifier = Modifier.weight(1f),
                                    text = "EXPOSITORES",
                                    Icons.Default.PeopleAlt,
                                    onClick = onClickSeeExpositores
                                )
                                EventCard(
                                    modifier = Modifier.weight(1f),
                                    text = "AGENDA",
                                    Icons.Default.CalendarViewWeek,
                                    onClick = onClickSeeEventDiary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        if (event != null) {
                            event.sponsors?.let { SponsorsCardsContainer(sponsors = it) }
                        }
                    }
                }
            }


        }
    }

}

//@Preview
//@Composable
//fun PreviewEventScreen(){
//    EventScreen()
//}