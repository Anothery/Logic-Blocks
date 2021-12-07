package com.sdzyume.logicblocks.model

import androidx.compose.ui.graphics.Color
import com.sdzyume.logicblocks.ui.theme.*

sealed class Action(open val color: Color, open val condition: Condition?) {
    data class Multiply(val to: Int, val cond: Condition? = null) : Action(Blue, cond)
    data class Divide(val to: Int, val cond: Condition? = null) : Action(GrayedGreen, cond)
    data class BackTo(val to: Int, val cond: Condition? = null) : Action(GrayedPink, cond)
    data class Sqr(val cond: Condition? = null) : Action(Violet, cond)
    data class Get(val cond: Condition? = null) : Action(DarkBlue, cond)
    data class Send(val cond: Condition? = null) : Action(DarkBlue, cond)
}

sealed class Condition(open val numberToCompare: Int) {
    data class Greater(override val numberToCompare: Int) : Condition(numberToCompare)
    data class Lesser(override val numberToCompare: Int) : Condition(numberToCompare)
    data class GreaterOrEqual(override val numberToCompare: Int) : Condition(numberToCompare)
    data class LesserOrEqual(override val numberToCompare: Int) : Condition(numberToCompare)
    data class Equals(override val numberToCompare: Int) : Condition(numberToCompare)
    data class NotEquals(override val numberToCompare: Int) : Condition(numberToCompare)

    fun checkMatchCondition(currentNumber: Int): Boolean {
        return when (this) {
            is Greater -> currentNumber > numberToCompare
            is GreaterOrEqual -> currentNumber >= numberToCompare
            is Equals -> currentNumber == numberToCompare
            is NotEquals -> currentNumber != numberToCompare
            is Lesser -> currentNumber < numberToCompare
            is LesserOrEqual -> currentNumber <= numberToCompare
        }
    }

    companion object {
        const val EQUALS = "="
        const val NOT_EQUALS = "≠"
        const val GREATER_OR_EQUAL = "≥"
        const val LESSER_OR_EQUAL = "≤"
        const val GREATER = ">"
        const val LESSER = "<"
    }
}
