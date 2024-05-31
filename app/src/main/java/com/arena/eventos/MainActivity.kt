package com.arena.eventos

import com.arena.eventos.ui.screens.HomeScreen
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.arena.eventos.ui.components.bottomnavigation.AppBottomNavigation
import com.arena.eventos.ui.components.header.HeaderApp
import com.arena.eventos.ui.screens.AllNewsScreen
import com.arena.eventos.ui.screens.CurrentEventDiary
import com.arena.eventos.ui.screens.CurrentEventScreen
import com.arena.eventos.ui.screens.CurrentExpositorScreen
import com.arena.eventos.ui.screens.CurrentNewsScreen
import com.arena.eventos.ui.screens.ListOfExpositorsInEvent
import com.arena.eventos.ui.screens.OpenWebViewScreen
import com.arena.eventos.ui.theme.EventosArenaTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //checkPermission()
            EventosArenaTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStack?.destination
                val currentScreen =
                    bottomBarNavigationScreens.find { it.route == currentDestination?.route }
                        ?: Home
                var headerTitle = currentScreen.route.uppercase(Locale.getDefault())


                LaunchedEffect(Unit) {
                    navController.addOnDestinationChangedListener { _, _, _ ->
                        val routes = navController.backQueue.map {
                            it.destination.route
                        }
                        Log.i(ContentValues.TAG, "onCreate: back stack - $routes")
                    }
                }

                Scaffold(
                    topBar = {
                        HeaderApp(
                            headerTitle,
                            navController = navController,
                            lastRouteVisited =  "Home"
                        )
                    },
                    bottomBar = {
                        AppBottomNavigation(
                            allScreens = bottomBarNavigationScreens,
                            onTabSelected = { newScreen ->
                                navController.navigateSingleTopTo(newScreen.route)
                            },
                            currentScreen = currentScreen
                        )
                    }
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Home.route) {
                            HomeScreen(
                                onClickSeeEvent = {
                                    navController.navigateSingleTopTo(CurrentEventScreen.route)
                                },
                                navController = navController,
                            )
                        }

                        composable(route = News.route) {
                            AllNewsScreen(
                                navController = navController,
                            )
                        }
                        composable(
                            route = "${CurrentEventScreen.route}/{eventId}",
                            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            headerTitle = CurrentEventScreen.headerInfo

                            var argument = backStackEntry.arguments?.getString("eventId")

                            if (argument != null) {
                                CurrentEventScreen(
                                    navController = navController,
                                    eventId = argument
                                )
                            }
                        }
                        composable(
                            route = "${ListOfExpositores.route}/{eventId}",
                            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            headerTitle = ListOfExpositores.route

                            var argument = backStackEntry.arguments?.getString("eventId")

                            if (argument != null) {
                                ListOfExpositorsInEvent(
                                    eventId = argument,
                                    navController = navController,
                                )
                            }
                        }

                        composable(
                            route = "${CurrentEventDiaryScreen.route}/{eventId}",
                            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            headerTitle = CurrentEventDiaryScreen.headerInfo

                            var argument = backStackEntry.arguments?.getString("eventId")

                            if (argument != null) {

                                CurrentEventDiary(
                                    eventId = argument
                                )
                            }
                        }

                        composable(
                            route = "${currentNewsScreen.route}/{newsId}",
                            arguments = listOf(navArgument("newsId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            headerTitle = currentNewsScreen.headerInfo

                            var argument = backStackEntry.arguments?.getString("newsId")
                            if (argument != null) {
                                CurrentNewsScreen(
                                    newsId = argument
                                )
                            }
                        }

                        composable(
                            route = "${currentExpositorProfileScreen.route}/{expositorId}",
                            arguments = listOf(navArgument("expositorId") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            headerTitle = currentExpositorProfileScreen.headerInfo

                            var argument = backStackEntry.arguments?.getString("expositorId")
                            if (argument != null) {
                                CurrentExpositorScreen(
                                    expositorId = argument,
                                    navController = navController,
                                )
                            }
                        }

                        composable(
                            route = "${currentExpositorWebViewScreen.route}/{expositorId}",
                            arguments = listOf(navArgument("expositorId") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            headerTitle = currentExpositorWebViewScreen.headerInfo

                            var expositorId = backStackEntry.arguments?.getString("expositorId")
                            if (expositorId != null) {
                                OpenWebViewScreen(
                                    expositorId = expositorId,
                                    navController = navController,
                                )
                            }
                        }

                        composable(
                            route = "${currentNewsScreen.deepLinkRoure}/{id}",
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern = "https://arena-eventos.firebaseapp.com/{id}"
                                    action = Intent.ACTION_VIEW
                                }
                            ),
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.StringType
                                    defaultValue = "empty"
                                }
                            )
                        ) { entry ->
                            val argument = entry.arguments?.getString("id")
                            if (argument != null) {
                                CurrentNewsScreen(
                                    newsId = argument
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    private fun checkPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }


//private fun NavHostController.navigateToEventScreen(route: String) {
//    this.navigate(route) {
//        popUpTo(
//            this@navigateToEventScreen.graph.findStartDestination().id
//        ) {
//            saveState = true
//        }
//        launchSingleTop = true
//        restoreState = true
//    }
//}
private fun NavHostController.navigateToScreens(route: String) {
    this.navigateSingleTopTo(route)
}







