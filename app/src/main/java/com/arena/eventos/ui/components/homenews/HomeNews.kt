package com.arena.eventos.ui.components.homenews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.arena.eventos.News
import com.arena.eventos.R
import com.arena.eventos.currentNewsScreen
import com.arena.eventos.database.model.NewsTypes
import com.arena.eventos.ui.theme.EventosArenaTheme

@Composable
fun HomeNews(
    listOfNews: SnapshotStateList<NewsTypes>,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Notícias",
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(bottom = 12.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ver mais",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Right,
                        color = Color(0xFFFF4306)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 6.dp)
                        .clickable {
                            navController.navigate(News.route)
                        }
                )
            }
        }
        for (news in listOfNews) {
            CardNews(news, navController)
        }
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(250.dp) // Set a specific height constraint
//        ) {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(10.dp)
//            ) {
//                items(listOfNews) { news ->
//                    CardNews(news, navController)
//                }
//            }
//        }
    }
}

@Composable
fun CardNews(news: NewsTypes, navController: NavHostController) {
    Surface(
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 7.dp)
                .clickable {
                    navController.navigate("${currentNewsScreen.route}/${news.id}")
                }
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.coverUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholderimage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFDBDBDB))
            )
            Column {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(bottom = 3.dp, start = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = news.headlineTitle,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontSize = 13.sp,
                    maxLines = 3

                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun PreviewHomeNews() {
    EventosArenaTheme {
        HomeNews()
    }
}

fun HomeNews() {
    TODO("Not yet implemented")
}
