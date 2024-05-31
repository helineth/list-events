package com.arena.eventos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.R
import com.arena.eventos.currentNewsScreen
import com.arena.eventos.database.model.NewsTypes
import com.arena.eventos.repository.NewRepositoryImpl
import com.arena.eventos.ui.effects.ShimmerEffect

@Composable
fun AllNewsScreen(
    navController: NavHostController,
) {
    val isLoading = remember { mutableStateOf(true) }
    val listOfNews = remember { mutableStateListOf<NewsTypes>() }
    LaunchedEffect(true) {
        val newsData = NewRepositoryImpl()

        newsData.getAllNews(
            onSuccess = { result ->
                //listOfNews.clear()
                listOfNews.addAll(result.reversed())
                isLoading.value = false
            },
            onFailure = {
                isLoading.value = false
            }
        )
    }
    if (isLoading.value) {
        ShimmerEffect()
    } else {
        Spacer(Modifier.height(40.dp))
        ContainerOfNews(listOfNews, navController)
    }
}


@Composable
fun ContainerOfNews(
    listOfNews: SnapshotStateList<NewsTypes>,
    navController: NavHostController
) {
    val totalNews: List<NewsTypes> = listOfNews.map { it }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxHeight(Float.POSITIVE_INFINITY),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(totalNews) { news ->
                Card(news, navController)
            }
        }
    }
}

@Composable
fun Card(news: NewsTypes, navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable {
                navController.navigate("${currentNewsScreen.route}/${news.id}")
            }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.coverUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholderimage),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Column() {
                Text(
                    text = news.title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 3,
                    color = Color(0xFFFF4306)
                )
                Text(
                    text = news.headlineTitle,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Left,
                        color = Color(0xFF1F1F1F)
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                    maxLines = 3
                )
                Spacer(Modifier.height(16.dp))
            }
        }

    }
}


@Preview
@Composable
fun PreviewIngressScreen() {
    AllNewsScreen()
}

fun AllNewsScreen() {
    TODO("Not yet implemented")
}
