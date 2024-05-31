package com.arena.eventos.ui.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun  DescriptiveCard(image: ImageVector, title:String, description:String) {
    val appColor = Color(0xFFFF4306)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp)
    ){
        Icon(
            image ,
            contentDescription = "Menu Icon",
            tint = appColor,
            modifier = Modifier.size(40.dp)

        )

        Spacer(modifier=Modifier.width(20.dp))
        Column(){
            Text(
                text= title,
                color = appColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
            )
            Spacer(modifier=Modifier.width(10.dp))

            Text(
                text = description,
                color = Color(0xFFF717171),
            )

        }
    }
}
@Preview
@Composable
fun DescriptiveCardPreview(){
    DescriptiveCard(Icons.Rounded.Person,"Title","This is the description")
}