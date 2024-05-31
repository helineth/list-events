package com.arena.eventos.ui.components.bottomnavigation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arena.eventos.RouteDestination
import java.util.Locale


@Composable
fun AppBottomNavigation(
    allScreens: List<RouteDestination>,
    onTabSelected: (RouteDestination) -> Unit,
    currentScreen: RouteDestination
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .selectableGroup()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            allScreens.forEach { screen ->
                BottomBarNav(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun BottomBarNav(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val tabTintColor = Color(0xFF7A7A7A)
    val selectedTintColor = Color(0xFFFF4306)
    Row(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .widthIn(max = 60.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterHorizontally),
                tint = if (selected) {
                    selectedTintColor
                } else {
                    tabTintColor
                }
            )
            Text(
                text = text,
                style = MaterialTheme.typography.caption,
                color = tabTintColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
private val TabHeight = 56.dp


