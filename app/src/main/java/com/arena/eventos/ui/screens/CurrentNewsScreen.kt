package com.arena.eventos.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.R
import com.arena.eventos.database.model.NewsTypes
import com.arena.eventos.repository.NewRepositoryImpl


val deepLinkState = mutableStateOf<Uri?>(null)

@SuppressLint("IntentReset")
@Composable
fun CurrentNewsScreen(
    newsId: String
) {
    val isOpenModal = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val currentEvent = remember { mutableStateOf<NewsTypes?>(null) }
    LaunchedEffect(key1 = deepLinkState) {
        val eventData = NewRepositoryImpl()
        eventData.getNewsById(newsId,
            onSuccess = { news ->
                currentEvent.value = news
            },
            onFailure = {
            }
        )
//        deepLinkState.value?.let { uri ->
//            // Handle deep link here
//            if (uri.path == "/home") {
//                // Navigate to the home page
//                val intent = Intent(context, MainActivity::class.java)
//                context.startActivity(intent)
//            }
//        }
    }
    val newsData = currentEvent.value
    val title = newsData?.title
    val headline = newsData?.headlineTitle
    val text = newsData?.body
    val arrImages = newsData?.covers


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (newsData != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(newsData.coverUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholderimage),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.height(200.dp)
            )
        }
        Column(
            Modifier
                .padding(horizontal = 16.dp)
        ) {

            //===========================================
            val link = "https://arena-eventos.firebaseapp.com/${newsData?.id}"

            val imageUrl = newsData?.coverUrl
            val titleNews = "${newsData?.title}, $link"
            // This is the caption that will be shared on social media
            val headLine = "${newsData?.title}"


            Spacer(Modifier.height(8.dp))
            if (title != null) {
                Text(
                    title,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            if (headline != null) {
                Text(
                    headline,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 8.dp),
                    //fontSize = 16.sp,
                )
            }
            Spacer(Modifier.height(8.dp))
            if (text != null) {
                Text(
                    text,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
//            if (imageUrl != null) {
//                Spacer(modifier = Modifier.height(10.dp))
//                ShareButton(link, imageUrl, titleNews, headLine)
//            }
            Spacer(Modifier.height(10.dp))
            if (!arrImages.isNullOrEmpty()) {

                NewsSlideImages(arrImages = arrImages, onOpenModal = { isOpenModal.value = true })
            }
            if (isOpenModal.value) {
                if (arrImages != null) {
                    Modal(arrImages, onDismiss = { isOpenModal.value = false })
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
fun ShareButton(link: String, imageUrl: String, titleNews: String, headLine: String) {
    val context = LocalContext.current
    val appColor = Color(0xFFFF4306)
    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp)
            .fillMaxWidth(0.5f)
            .clickable {

                val uri = Uri.parse("seu_deeplink_aqui")
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/*"
                //intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_TITLE, titleNews)
                intent.putExtra(Intent.EXTRA_TEXT, imageUrl)
                //intent.putExtra(Intent.EXTRA_STREAM, imageUri)
                intent.putExtra(Intent.EXTRA_SUBJECT, headLine)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(Intent.createChooser(intent, "Compartilhar com:"))
            }
            .border(
                BorderStroke(1.dp, appColor),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center

    ) {
        Row(

        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = null,
                tint = appColor,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Partilhar ",
                textAlign = TextAlign.Center,
                color = appColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold

            )

        }
    }
}

@Composable
fun NewsSlideImages(arrImages: List<String>, onOpenModal: () -> Unit) {

    Column(Modifier.padding(horizontal = 0.dp)) {
        Text(
            text = "Fotos de destaque",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(bottom = 12.dp, top = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow {
            items(arrImages) { img ->
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(150.dp)
                        .padding(5.dp)
                        .background(Color.White)
                        .clickable {
                            onOpenModal()
                        }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(img)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.placeholderimage),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .size(200.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun Modal(arrImages: List<String>, onDismiss: () -> Unit) {
    val appColor = Color(0xFFFFFFFF)
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A0A0A))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(
                    onClick = {
                        onDismiss()
                    },
                    //modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = appColor,
                        //modifier = Modifier.size(48.dp),
                    )
                }
                Text(
                    text = "Close",
                    style = androidx.compose.material3.MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable { onDismiss() },
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = appColor,
                )
            }
            Column(Modifier.padding(horizontal = 0.dp)) {
                LazyRow {
                    items(arrImages) { img ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 16.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(img)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(R.drawable.placeholderimage),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .size(500.dp)

                            )

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCurrentNewsScreen() {
    CurrentNewsScreen(newsId = "news")
}






