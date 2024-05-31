package com.arena.eventos.ui.components.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HeaderApp(
    headerTitle: String,
    navController: NavController,
    lastRouteVisited:  String
) {

    val appColor = Color(0xFFFF4306)
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {

            if(headerTitle != "INICIO"){

                IconButton(
                    onClick = {navController.popBackStack()},
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
                    text = "$headerTitle",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(start = 16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = appColor,
                )
            }else{
                Text(
                    text = "$headerTitle",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = appColor,
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewHeaderApp() {
    HeaderApp("headerTitle")
}

fun HeaderApp(headerTitle: String) {

}
