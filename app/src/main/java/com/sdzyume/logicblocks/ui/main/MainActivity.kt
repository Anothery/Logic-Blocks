package com.sdzyume.logicblocks.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sdzyume.logicblocks.ui.Route
import com.sdzyume.logicblocks.ui.level.fifth.FifthLevel
import com.sdzyume.logicblocks.ui.level.first.FirstLevel
import com.sdzyume.logicblocks.ui.level.forth.ForthLevel
import com.sdzyume.logicblocks.ui.level.second.SecondLevel
import com.sdzyume.logicblocks.ui.level.third.ThirdLevel
import com.sdzyume.logicblocks.ui.menu.MenuScreen
import com.sdzyume.logicblocks.ui.onboarding.Onboarding
import com.sdzyume.logicblocks.ui.theme.DarkBlue
import com.sdzyume.logicblocks.ui.theme.YumeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YumeGameTheme {
                rememberSystemUiController().apply {
                    setStatusBarColor(DarkBlue, darkIcons = false)
                    setNavigationBarColor(DarkBlue, darkIcons = false)
                }
                Navigation()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Main.route) {
        composable(route = Route.Main.route) {
            MainScreen(navController = navController)
        }
        composable(route = Route.Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(route = Route.Menu.route) {
            MenuScreen(navController = navController)
        }
        composable(route = Route.FirstLevel.route) {
            FirstLevel(navController = navController)
        }
        composable(route = Route.SecondLevel.route) {
            SecondLevel(navController = navController)
        }
        composable(route = Route.ThirdLevel.route) {
            ThirdLevel(navController = navController)
        }
        composable(route = Route.FourthLevel.route) {
            ForthLevel(navController = navController)
        }
        composable(route = Route.FifthLevel.route) {
            FifthLevel(navController = navController)
        }

    }
}
