package com.sdzyume.logicblocks.ui.level.third

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sdzyume.logicblocks.ui.base.BaseLevelScreen

@Composable
fun ThirdLevel(navController: NavController) {
    val viewModel: ThirdLevelViewModel = viewModel()
    BaseLevelScreen(navController = navController,
        state = viewModel.state.value,
        effectFlow = viewModel.effect,
        onEvent = viewModel::setEvent
    )
}