package com.arena.eventos.ui.effects

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
@Composable
fun ShimmerEffect() {

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(alpha)
                .background(Color.LightGray)
                .align(Alignment.CenterHorizontally)
        ) {
//            Text(
//                text = "",
//                fontWeight = FontWeight.ExtraBold,
//                fontSize = 20.sp,
//                maxLines = 2,
//
//
//            )
        }
        // Your content here (e.g., text, images, etc.)
    }
}