package com.sdzyume.logicblocks.ui.level.second

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sdzyume.logicblocks.ui.base.BaseLevelScreen

@Composable
fun SecondLevel(navController: NavController) {
    val viewModel: SecondLevelViewModel = viewModel()
    BaseLevelScreen(navController = navController,
        state = viewModel.state.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent
    )
}