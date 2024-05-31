package com.arena.eventos.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.R
import com.arena.eventos.database.model.SponsorTypes
import com.arena.eventos.ui.components.homecards.onCardHomeClicked
import com.arena.eventos.ui.components.modals.ModalSearchInput
import com.arena.eventos.ui.components.modals.SearchResults


@Composable
fun ExpositorScreen(
    modifier: Modifier = Modifier,

    ) {

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    )
    {
        //ExpositorHeaderBanner()
        //Spacer(Modifier.height(8.dp))
        ExpositorSearchBar()
        Spacer(Modifier.height(8.dp))
        //ExpositorCardsButtons()
        AlphabetList()
        Spacer(Modifier.height(8.dp))
        //SponsorsCardsContainer()
    }

}


@Composable
fun ExpositorHeaderBanner() {
    val imageBanner = R.drawable.current_banner
    Box(
        modifier =
        Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        Image(
            painter = painterResource(imageBanner),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth() // Make the Image fill the available size
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(1.dp, Color.White)
                )

        )
    }
}

@Composable
fun ExpositorSearchBar(modifier: Modifier = Modifier) {
    var searchItem by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var searchResults by remember { mutableStateOf(emptyList<String>()) }

    Box(
        Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFE9E8E8),
            )

    ) {
        TextField(
            value = searchItem,
            onValueChange = { searchItem = it },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            placeholder = {
                Text(
                    "CATEGORA, EXPOSITOR...",
                    fontSize = 12.sp
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 46.dp)
                .border(
                    BorderStroke(10.dp, Color.White)
                )
                .clickable { isSearching = true }
        )

        if (isSearching) {
            ModalSearchInput(
                onSearch = { query ->
                    // Simulate search logic and update the results
                    searchResults = listOf("Result 1", "Result 2", "Result 3")
                },
                onDismiss = { isSearching = false }
            )
        }

        if (searchResults.isNotEmpty()) {
            SearchResults(searchResults)
        }
    }
}

@Composable
fun ExpositorCardsButtons() {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardButton(
                modifier = Modifier.weight(1f),
                text = "POR SECTOR",
                Icons.Default.CalendarMonth
            )
            CardButton(
                modifier = Modifier.weight(1f),
                text = "PLANTA",
                Icons.Default.CardGiftcard
            )
        }
    }
}

@Composable
fun CardButton(modifier: Modifier, text: String, iconImage: ImageVector) {
    val appColor = Color(0xFFFF4306)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(70.dp)
            .border(
                BorderStroke(1.dp, appColor),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onCardHomeClicked(text)
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = appColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun AlphabetCircleButton(text: String) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .padding(bottom = 5.dp)
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(50.dp)
            )
            .clickable { },
        contentAlignment = Alignment.Center,

        ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun AlphabetList() {
    val alphabetList = ('A'..'Z').map { it.toString() }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .height(300.dp),

        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(alphabetList) {
            AlphabetCircleButton(it.toString())
        }
    }

}


@Composable
fun SponsorsCardsContainer(sponsors: List<SponsorTypes>) {

    Column(Modifier.padding(horizontal = 0.dp)) {
        Text(
            text = "PATROCINADORES",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(bottom = 12.dp, top = 10.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(sponsors) { sponsor ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .padding(5.dp)
                        .background(Color.White)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(sponsor.coverUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholderimage),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExpositorScreen() {
    ExpositorScreen()
}




