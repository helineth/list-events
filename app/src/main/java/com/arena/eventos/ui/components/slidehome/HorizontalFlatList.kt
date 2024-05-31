package com.arena.eventos.ui.components.slidehome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.CurrentEventScreen
import com.arena.eventos.R
import com.arena.eventos.database.model.EventType
import com.arena.service.Helper.MONTHS
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListOfActiveEvents(
    eventsList: SnapshotStateList<EventType>,
    navController: NavHostController,
) {
    val appColor = Color(0xFFFF4306)

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Eventos",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

//            Text(
//                text = "ver mais",
//                style = TextStyle(
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Right,
//                    color = appColor
//                ),
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(vertical = 6.dp)
//            )
        }

        LazyRow {
            items(eventsList) { event ->

                val startDate = event.startDate
                val endDate = event.endDate

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val startDay = sdf.parse(startDate)
                val endDay = sdf.parse(endDate)

                val month = MONTHS[startDay.month - 1]
                val firstDay = startDay.date
                val lastDay = endDay.date


                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(230.dp)
                        .padding(start = 16.dp)
                        .clickable {
                            navController.navigate("${CurrentEventScreen.route}/${event.id}")
                        }
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(event.coverUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholderimage),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )

                        Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp)) {
                            Text(
                                text =  "${firstDay}-${lastDay}, ${month}, 2023",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Right,
                                    color = appColor
                                )
                            )

                            Text(
                                text = event.title,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Left
                                ),
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Text(
                                text = event.localization,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Left,
                                    color = Color(0xFF777777)
                                ),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


