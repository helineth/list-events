package com.arena.eventos.ui.components.slidehome
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.arena.eventos.R
import com.arena.eventos.database.model.EventType

@Composable
fun CurrentActiveEventBanner(
    onClickSeeCurrentEvent: (event: EventType) -> Unit = {},
    highlightedEvent: SnapshotStateList<EventType>
) {

    Row(
        Modifier
            .fillMaxWidth()
            //.padding(horizontal = 16.dp)
//            .clickable {
//                //onClickSeeCurrentEvent()
//            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.current_banner),
            contentDescription = "Filda Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                //.clip(RoundedCornerShape(16.dp))
        )
        Text(
            text =  "",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(bottom = 12.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
//@Preview
//@Composable()
//fun PreviewCurrentActiveEventBanner() {
//    CurrentActiveEventBanner()
//}
//
//fun CurrentActiveEventBanner() {
//    TODO("Not yet implemented")
//}
