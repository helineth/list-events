package com.arena.eventos.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arena.eventos.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arena.eventos.currentExpositorProfileScreen
import com.arena.eventos.currentExpositorWebViewScreen
import com.arena.eventos.database.model.ExpositorType
import com.arena.eventos.database.model.ProductTypes
import com.arena.eventos.repository.EventRepositoryImpl
import com.arena.eventos.ui.effects.ShimmerEffect
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentExpositorScreen(
    expositorId: String,
    navController: NavHostController
) {
    val isLoading = remember { mutableStateOf(true) }
    val currentExpositor = remember { mutableStateOf<ExpositorType?>(null) }
    var expanded by remember { mutableStateOf(false) }

    val allProducts = remember { mutableStateListOf<ProductTypes>() }
    LaunchedEffect(true) {
        val eventData = EventRepositoryImpl()
        eventData.getExpositorById(expositorId,
            onSuccess = { event ->
                val pro = event.products
                currentExpositor.value = event
                allProducts.addAll(pro)
                isLoading.value = false

            },
            onFailure = {
            }
        )
    }

    val expositorData = currentExpositor.value
    var about = expositorData?.description
    val aboutText = if (expanded) about else about?.take(6)
    val aboutColor = Color(0xFF3A3939)
    val appColor = Color(0xFFFF4306)

    fun openExpositorSiteOnWebView() {
        if (expositorData != null) {
            navController.navigate("${currentExpositorWebViewScreen.route}/${expositorData.docId}")
        }
    }
    if (isLoading.value) {
        ShimmerEffect()
    } else {

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            if (expositorData != null) {
                ImageWithOverlayBackground(img = expositorData.logoUrl)
            }
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(80.dp))

                if (expositorData != null) {
                    Text("Expositor")
                    Spacer(modifier = Modifier.height(5.dp))
                    Title(expositorData.name)
                }
                if (about != null) {
                    if (about.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Title("Sobre")
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
                val lerMaisText = if (expanded) "Ler menos" else "Ler mais"
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
                                withStyle(
                                    style = SpanStyle(
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
                if (expositorData?.websiteUrl?.length!! > 0) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Title("WebSite")
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.clickable { openExpositorSiteOnWebView() }
                    ) {
                        Icon(imageVector = Icons.Filled.Link, contentDescription = null)
                        Spacer(modifier = Modifier.width(5.dp))
                        expositorData.websiteUrl?.let { it1 ->
                            Text(
                                text = it1,
                                color = Color.Blue
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (allProducts.size > 0) {
                    Title(title = "Produtos/Serviços")
                    Spacer(modifier = Modifier.height(10.dp))
                    ProductsAndServices(products = allProducts)
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun ImageWithOverlayBackground(img: String) {
    BoxWithConstraints(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val overlayColor = Color(0xFF000000)
        val imgBgColor = Color(0xFFFAF9F9)
        val reducedOpacityColor = overlayColor.copy(alpha = 0.5f)

        Box(
            modifier = Modifier.fillMaxSize()

        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(img)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.placeholderimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .padding(10.dp)
                    .size(50.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(reducedOpacityColor)
            ) {
                // Conteúdo adicional que deseja colocar por cima da imagem
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(img)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholderimage),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .offset(
                    y = ((140).dp),
                    x = 10.dp
                )
                .clip(RoundedCornerShape(100))
                .background(imgBgColor)
                .padding(10.dp)
                .border(
                    BorderStroke(
                        color = Color.White,
                        width = 0.dp
                    ),
                    shape = RoundedCornerShape(100)
                )
        )

    }
}

@Composable
fun ProductsAndServices(products: List<ProductTypes>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(products) { product ->
            Column() {
                Column() {

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .padding(5.dp)
                            .background(Color.White)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(product.productCover)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.placeholderimage),
                            contentDescription = product.productName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )

                    }
                }
//                Box(
//                    Modifier
//                        .background(Color.White)
//                        .fillMaxWidth(),
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Text(
//                        text = product.productName.replaceFirstChar {
//                            if (it.isLowerCase()) it.titlecase(
//                                Locale.ROOT
//                            ) else it.toString()
//                        },
//                        style = MaterialTheme.typography.h3,
//                        modifier = Modifier
//                            .padding(bottom = 12.dp, top = 10.dp)
//                            .fillMaxWidth(),
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center
//                    )
//                }
            }
        }
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrentExpositorScreen() {
    CurrentExpositorScreen()
}

fun CurrentExpositorScreen() {
    TODO("Not yet implemented")
}
