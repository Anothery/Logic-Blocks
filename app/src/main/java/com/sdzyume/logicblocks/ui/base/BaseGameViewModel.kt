package com.sdzyume.logicblocks.ui.base

import androidx.lifecycle.viewModelScope
import com.sdzyume.logicblocks.model.Action
import com.sdzyume.logicblocks.model.Condition
import com.sdzyume.logicblocks.model.CurrentValue
import kotlinx.coroutines.*

open class BaseGameViewModel(
    title: String,
    mainCondition: String,
    private val entryData: List<Int>,
    private val successData: List<Int>
) :
    BaseViewModel<LevelContract.State, LevelContract.Event, LevelContract.Effect>(
        LevelContract.State(
            levelTitle = title,
            mainCondition = mainCondition,
            successDataToCompare = successData,
            startData = entryData
        )
    ) {

    private var currentGameCycle: Job? = null

    companion object {
        private const val FAILED_DELAY_MILLIS = 1000L
        private const val MAIN_DELAY_MILLIS = 1800L
    }


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
                    setState {
                        copy(
                            isChecking = false,
                            startData = entryData,
                            resultData = listOf()
                        )
                    }
                    return@launch
                }
            }
            is LevelContract.Event.Check -> {
                currentGameCycle = viewModelScope.launch {
                    var finished = false

                    try {
                        setState { copy(isChecking = true) }

                        var currentValue: Int? = null
                        var indexToJump = -1
                        while (!finished) {
                            if (state.value.startData.isEmpty() && currentValue == null || state.value.commands.isEmpty()) break

                            if (indexToJump == -1) currentValue = null

                            run breakForEach@{
                                state.value.commands.forEachIndexed { i, action ->
                                    if (indexToJump != -1 && indexToJump != i) return@forEachIndexed
                                    if (indexToJump == i) indexToJump = -1

                                    setEffect { LevelContract.Effect.ScrollToCommand(i) }

                                    if (action !is Action.Get) {
                                        action.condition?.let { condition ->
                                            currentValue?.let {
                                                if (!condition.checkMatchCondition(it)) return@forEachIndexed
                                            }
                                        }
                                    }

                                    when (action) {
                                        is Action.Get -> {
                                            action.condition?.let { condition ->
                                                if (state.value.startData.isEmpty()) {
                                                    finished = true
                                                    return@breakForEach
                                                }

                                                if (!condition.checkMatchCondition(state.value.startData[0])) {
                                                    setState {
                                                        copy(
                                                            startData = state.value.startData.toMutableList()
                                                                .apply { removeFirst() })
                                                    }
                                                    indexToJump = i
                                                    delay(MAIN_DELAY_MILLIS)
                                                    return@breakForEach
                                                }
                                            }
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
                                            delay(100)
                                            val correctAction = action.to - 1
                                            if (correctAction >= i) throw Exception("Recursive")
                                            indexToJump = correctAction
                                            return@breakForEach
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
                                    if (i == state.value.commands.size - 1) finished = true

                                    delay(MAIN_DELAY_MILLIS)
                                }
                            }
                        }
                        if (state.value.resultData == successData) {
                            setState { copy(levelCompleted = true) }
                        } else {
                            setEffect { LevelContract.Effect.LevelFailed }
                            delay(FAILED_DELAY_MILLIS)
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
                        delay(FAILED_DELAY_MILLIS)
                        setState { copy(isChecking = false, startData = entryData) }
                    }
                }
            }
        }
    }
}