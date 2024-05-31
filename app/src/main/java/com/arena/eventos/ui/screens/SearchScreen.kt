package com.arena.eventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp, horizontal = 16.dp)
    ) {
        Row() {
            Text(
                text = "Search Screen",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 12.dp),
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}