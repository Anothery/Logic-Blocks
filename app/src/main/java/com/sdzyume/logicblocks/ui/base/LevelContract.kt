package com.sdzyume.logicblocks.ui.base

import com.sdzyume.logicblocks.model.Action
import com.sdzyume.logicblocks.model.Condition
import com.sdzyume.logicblocks.model.CurrentValue

object LevelContract {

    data class State(
        val isChecking: Boolean = false,
        val startData: List<Int> = listOf(),
        val commands: List<Action> = listOf(),
        val resultData: List<Int> = listOf(),
        val successDataToCompare: List<Int> = listOf(),
        val levelCompleted: Boolean = false,
        val currentValue: CurrentValue? = null,
        val mainCondition: String = "",
        val levelTitle: String = ""
    )

    sealed class Event {
        data class CommandClicked(val action: Action) : Event()
        data class RemoveCommand(val index: Int) : Event()
        data class AddConditionToItem(val index: Int, val condition: Condition) : Event()
        data class RemoveConditionFromItem(val index: Int) : Event()
        object Check : Event()
        object StopCheck : Event()
    }

    sealed class Effect {
        object LevelFailed : Effect()
        data class ScrollToCommand(val index: Int) : Effect()
    }

}