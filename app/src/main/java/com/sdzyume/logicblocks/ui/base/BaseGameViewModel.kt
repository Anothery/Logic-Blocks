package com.sdzyume.logicblocks.ui.base

import androidx.lifecycle.viewModelScope
import com.sdzyume.logicblocks.model.Action
import com.sdzyume.logicblocks.model.Condition
import com.sdzyume.logicblocks.model.CurrentValue
import kotlinx.coroutines.*

open class BaseGameViewModel(title: String, mainCondition: String, private val entryData: List<Int>, private val successData: List<Int>) :
    BaseViewModel<LevelContract.State, LevelContract.Event, LevelContract.Effect>(
        LevelContract.State(
            levelTitle = title,
            mainCondition = mainCondition,
            successDataToCompare = successData,
            startData = entryData
        )
    ) {

    private var currentGameCycle : Job? = null


    override fun handleEvents(event: LevelContract.Event) {
        when (event) {
            is LevelContract.Event.CommandClicked -> viewModelScope.launch {
                setState {
                    copy(commands = mutableListOf<Action>().apply {
                        addAll(commands); add(event.action)
                    })
                }
            }
            is LevelContract.Event.RemoveCommand -> viewModelScope.launch {
                setState {
                    copy(commands = mutableListOf<Action>().apply {
                        addAll(commands);removeAt(
                        event.index
                    )
                    })
                }
            }
            is LevelContract.Event.AddConditionToItem -> viewModelScope.launch {
                state.value.commands.getOrNull(event.index)?.let {
                    val updatedCommands = state.value.commands.toMutableList().also { list ->
                        with(list[event.index]) {
                            when (this) {
                                is Action.BackTo -> {
                                    list[event.index] = Action.BackTo(this.to, event.condition)
                                }
                                is Action.Divide -> {
                                    list[event.index] = Action.Divide(this.to, event.condition)
                                }
                                is Action.Multiply -> {
                                    list[event.index] = Action.Multiply(this.to, event.condition)
                                }
                                is Action.Sqr -> {
                                    list[event.index] = Action.Sqr(event.condition)
                                }
                                is Action.Send -> {
                                    list[event.index] = Action.Send(event.condition)
                                }
                                is Action.Get -> {
                                    list[event.index] = Action.Get(event.condition)
                                }
                            }
                        }
                    }
                    setState { copy(commands = updatedCommands) }
                }
            }
            is LevelContract.Event.RemoveConditionFromItem -> viewModelScope.launch {
                state.value.commands.getOrNull(event.index)?.let {
                    val updatedCommands = state.value.commands.toMutableList().also { list ->
                        with(list[event.index]) {
                            when (this) {
                                is Action.BackTo -> {
                                    list[event.index] = Action.BackTo(this.to, null)
                                }
                                is Action.Divide -> {
                                    list[event.index] = Action.Divide(this.to, null)
                                }
                                is Action.Multiply -> {
                                    list[event.index] = Action.Multiply(this.to, null)
                                }
                                is Action.Sqr -> {
                                    list[event.index] = Action.Sqr(null)
                                }
                                is Action.Send -> {
                                    list[event.index] = Action.Send(null)
                                }
                                is Action.Get -> {
                                    list[event.index] = Action.Get(null)
                                }
                            }
                        }
                    }
                    setState { copy(commands = updatedCommands) }
                }
            }
            is LevelContract.Event.StopCheck -> viewModelScope.launch {
                if (state.value.isChecking) {
                        currentGameCycle?.cancelAndJoin()
                        currentGameCycle = null
                        setState { copy(isChecking = false, startData = entryData, resultData = listOf()) }
                        return@launch
                }
            }
            is LevelContract.Event.Check -> {
                currentGameCycle = viewModelScope.launch {

                try {
                    setState { copy(isChecking = true) }

                    var currentValue: Int? = null
                    var indexToJump = -1
                    while (true) {
                        if (state.value.startData.isEmpty() && currentValue == null ||
                            state.value.commands.isEmpty()) break

                        if (indexToJump == -1) currentValue = null

                        run toBreak@{
                            state.value.commands.forEachIndexed { i, action ->
                                if (indexToJump != -1 && indexToJump != i) return@forEachIndexed
                                if(indexToJump == i) indexToJump = -1
                                setEffect { LevelContract.Effect.ScrollToCommand(i) }

                                action.condition?.let { condition ->
                                    currentValue?.let {
                                        when (condition) {
                                            is Condition.Equals -> if (it != condition.numberToCompare) return@forEachIndexed
                                            is Condition.NotEquals -> if (it == condition.numberToCompare) return@forEachIndexed
                                            is Condition.LesserOrEqual -> if (it > condition.numberToCompare) return@forEachIndexed
                                            is Condition.Lesser -> if (it >= condition.numberToCompare) return@forEachIndexed
                                            is Condition.GreaterOrEqual -> if (it < condition.numberToCompare) return@forEachIndexed
                                            is Condition.Greater -> if (it <= condition.numberToCompare) return@forEachIndexed
                                        }
                                    }
                                }

                                when (action) {
                                    is Action.Get -> {
                                        currentValue = state.value.startData.firstOrNull()
                                        currentValue?.let {
                                            setState {
                                                copy(currentValue = CurrentValue(i, it))
                                            }
                                        }

                                        setState {
                                            copy(
                                                startData = state.value.startData.toMutableList()
                                                    .apply { removeFirst() })
                                        }
                                    }

                                    is Action.Multiply -> {
                                        if (currentValue != null) {
                                            currentValue = currentValue!! * action.to
                                        }
                                    }
                                    is Action.Divide -> {
                                        if (currentValue != null) {
                                            currentValue = currentValue!! / action.to
                                        }
                                    }
                                    is Action.Sqr -> {
                                        if (currentValue != null) {
                                            currentValue = currentValue!! * currentValue!!
                                        }
                                    }
                                    is Action.BackTo -> {
                                        val correctAction = action.to - 1
                                        if (correctAction >= i) throw Exception("Recursive")
                                        indexToJump = correctAction
                                        return@toBreak
                                    }
                                    is Action.Send -> {
                                        currentValue?.let {
                                            setState {
                                                copy(currentValue = CurrentValue(i, it),
                                                    resultData = state.value.resultData.toMutableList()
                                                        .apply { add(it) })
                                            }
                                        }
                                        currentValue = null
                                    }
                                }

                                currentValue?.let {
                                    setState { copy(currentValue = CurrentValue(i, it)) }
                                }
                                delay(1800)
                            }
                        }
                    }
                    if (state.value.resultData == successData) {
                        setState { copy(levelCompleted = true) }
                    } else {
                        setEffect { LevelContract.Effect.LevelFailed }
                        delay(1000)
                        setState {
                            copy(
                                isChecking = false,
                                startData = entryData,
                                resultData = listOf(),
                                currentValue = null
                            )
                        }
                    }

                } catch (ex: Exception) {
                    ex.printStackTrace()
                    setEffect { LevelContract.Effect.LevelFailed }
                    delay(1000)
                    setState { copy(isChecking = false, startData = entryData) }
                }
            } }
        }
    }
}