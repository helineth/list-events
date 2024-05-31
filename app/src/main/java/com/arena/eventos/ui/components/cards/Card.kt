package com.arena.eventos.ui.components.cards
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EventCard(
    modifier: Modifier,
    text: String,
    iconImage: ImageVector,
    onClick: () -> Unit = {},
) {
    val appColor = Color(0xFFFF4306)
    Box(
        modifier = modifier
            .padding(5.dp)
            .height(100.dp)
            .fillMaxWidth(0.5f)
            .clickable { onClick() }
            .border(
                BorderStroke(1.dp, appColor),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector =iconImage,
                contentDescription = null,
                tint = appColor,
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = appColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold

            )

        }
    }
}

