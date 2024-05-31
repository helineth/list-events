package com.arena.eventos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.R
import com.arena.eventos.currentExpositorProfileScreen
import com.arena.eventos.database.model.EventType
import com.arena.eventos.database.model.ExpositorType
import com.arena.eventos.repository.EventRepositoryImpl
import java.util.Locale

@Composable
fun ListOfExpositorsInEvent(
    modifier: Modifier = Modifier,
    eventId: String,
    navController: NavHostController
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
    val allexpositores = event?.expositores ?: emptyList()
    val total: List<ExpositorType> = allexpositores.map { it }

    Column(
        modifier
            .padding(vertical = 10.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxHeight(Float.POSITIVE_INFINITY),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(total) { elem ->
                ExpositorCard(
                    modifier = Modifier.weight(1f),
                    text = elem.name,
                    logoUrl = elem.logoUrl,
                    id = elem.docId,
                    navController = navController
                )
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun ExpositorCard(
    modifier: Modifier = Modifier,
    text: String,
    logoUrl: String,
    id: String,
    navController: NavHostController
) {
    val appColor = Color(0xFFCCCBCB)
    Box(
        modifier = modifier
            .padding(10.dp)
            .height(140.dp)
            .fillMaxWidth(0.5f)
            .clickable {
                navController.navigate("${currentExpositorProfileScreen.route}/${id}")
            }
            .border(
                BorderStroke(1.dp, appColor),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(logoUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholderimage),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(60.dp)
                    //.clip(CircleShape)
                    .background(Color(0xFFFDFCFC))
//                    .border(
//                        BorderStroke(
//                            color = Color.LightGray,
//                            width = 1.dp
//                        ),
//                        shape = RoundedCornerShape(100)
//                    )
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = text.replaceFirstChar {
                    if (it.isUpperCase())
                        it.titlecase(Locale.ROOT)
                    else it.toString() },
                textAlign = TextAlign.Center,
                color = Color(0xFF353434),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 3
            )
        }
    }
}

@Preview
@Composable
fun PreviewListOfExpositorsInEvent() {
    ExpositorScreen()
}
