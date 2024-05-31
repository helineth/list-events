package com.arena.eventos.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun VideoCard(title:String){
    val color = Color(0xFFD9D9D9)
   Column(
       modifier = Modifier
           .width(200.dp)
           .height(200.dp)
           .padding(10.dp)
   ) {
       Text(
           text=title,
           fontWeight = FontWeight(700)
       )
       Spacer(modifier = Modifier.height(10.dp))
       Box(
           modifier=Modifier.background(color)
               .fillMaxWidth()
               .height(200.dp)
       )
   }

}
@Preview(showBackground = true)
@Composable
fun videoPreview(){
    VideoCard("title")
}
