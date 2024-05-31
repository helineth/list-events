package com.arena.eventos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.arena.eventos.database.model.EventType

interface RouteDestination{
    val route: String
    val icon: ImageVector
}

object Home: RouteDestination{
    override val route = "Inicio"
    override val icon = Icons.Filled.Home
}

//object Calendar: RouteDestination{
//    override val route = "calendar"
//    override val icon = Icons.Filled.Notifications
//}

//object Ingress: RouteDestination{
//    override val route = "ingress"
//    override val icon = Icons.Filled.Wallet
//}

object News: RouteDestination{
    override val route = "Noticias"
    override val icon = Icons.Filled.Newspaper
}

object CurrentEventScreen: RouteDestination{
    val headerInfo = "Filda 2023"
    override val route = "profile"
    override val icon = Icons.Filled.CalendarViewMonth
}

object CurrentEventDiaryScreen: RouteDestination{
    val headerInfo = "Agenda do evento"
    override val route = "currentEvent"
    override val icon = Icons.Filled.CalendarViewMonth
}

object ListOfExpositores: RouteDestination{
    override val route = "Expositores"
    override val icon = Icons.Filled.CalendarViewMonth
}

object currentNewsScreen: RouteDestination{
    val headerInfo = "Notícia"
    override val route = "currentNews"
    val deepLinkRoure = "deeplinkroute"
    override val icon = Icons.Filled.CalendarViewMonth
}

object currentExpositorProfileScreen: RouteDestination{
    val headerInfo = "Expositor"
    override val route = "currentExpositor"
    override val icon = Icons.Filled.CalendarViewMonth
}

object currentExpositorWebViewScreen: RouteDestination{
    val headerInfo = "Site do expositor"
    override val route = "webwiew"
    override val icon = Icons.Filled.CalendarViewMonth
}
// Screens to be displayed in the bottom AppBottomNavigation
val bottomBarNavigationScreens = listOf(Home, News)


