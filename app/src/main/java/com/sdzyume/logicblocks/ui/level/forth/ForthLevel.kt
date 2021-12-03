package com.sdzyume.logicblocks.ui.level.forth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sdzyume.logicblocks.ui.base.BaseLevelScreen

@Composable
fun ForthLevel(navController: NavController) {
    val viewModel: ForthLevelViewModel = viewModel()
    BaseLevelScreen(
        navController = navController,
        state = viewModel.state.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent
    )
}