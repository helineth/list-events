package com.arena.eventos.ui.screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.arena.eventos.database.model.ExpositorType
import com.arena.eventos.repository.EventRepositoryImpl
import com.arena.eventos.ui.effects.ShimmerEffect

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun OpenWebViewScreen(
    expositorId: String,
    navController: NavHostController
) {

    val isLoading = remember { mutableStateOf(true) }
    val currentExpositor = remember { mutableStateOf<ExpositorType?>(null) }

    LaunchedEffect(true) {
        val eventData = EventRepositoryImpl()
        eventData.getExpositorById(expositorId,
            onSuccess = { event ->
                val pro = event.products
                currentExpositor.value = event
                isLoading.value = false
            },
            onFailure = {
            }
        )
    }

    if (isLoading.value) {
        ShimmerEffect()
    } else {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    currentExpositor.value?.websiteUrl?.let { loadUrl(it) }
                }
            },
            update = { view ->
                currentExpositor.value?.websiteUrl?.let { view.loadUrl(it) }
            }
        )
    }

}


