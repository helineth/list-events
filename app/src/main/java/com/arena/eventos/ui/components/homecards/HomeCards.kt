package com.arena.eventos.ui.components.homecards

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arena.eventos.CurrentEventScreen
import com.arena.eventos.RouteDestination

@Composable
fun HomeCards(
    onCardEventClick:  (RouteDestination) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.weight(1f),
                text = "EVENTOS", onCardEventClick
            )
            Card(
                modifier = Modifier.weight(1f),
                text = "INGRESSOS", onCardEventClick
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.weight(1f),
                text = "CREDENCIAMENTO", onCardEventClick
            )
            Card(
                modifier = Modifier.weight(1f),
                text = "INSCRIÇÕES", onCardEventClick
            )
        }
    }
}


@Composable
fun Card(
    modifier: Modifier,
    text: String,
    onCardEventClick: (RouteDestination) -> Unit,
) {
    val appColor = Color(0xFFFF4306)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(130.dp)
            .border(
                BorderStroke(1.dp, appColor),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onCardEventClick(CurrentEventScreen)
                //onCardHomeClicked(text)
            },
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Icon(
                imageVector = iconImage,
                contentDescription = null,
                tint = appColor
            )*/
            Spacer(modifier = Modifier.height(16.dp))

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

fun onCardHomeClicked(text: String) {

    if (text === "EVENTOS") {
        Log.d("EVENTOS", text.toString())
    } else {
        Log.d("NOT", text.toString())
    }

}
/*
@Preview
@Composable
fun PreviewHomeCards() {
    HomeCards()
}*/