package com.sdzyume.logicblocks.ui.level.fifth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sdzyume.logicblocks.ui.base.BaseLevelScreen

@Composable
fun FifthLevel(navController: NavController) {
    val viewModel: FifthLevelViewModel = viewModel()
    BaseLevelScreen(
        navController = navController,
        state = viewModel.state.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent
    )
}