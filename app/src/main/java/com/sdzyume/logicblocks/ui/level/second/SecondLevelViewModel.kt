package com.sdzyume.logicblocks.ui.level.second

import com.sdzyume.logicblocks.ui.base.BaseGameViewModel

class SecondLevelViewModel :
    BaseGameViewModel(
        title = "Уровень 2",
        mainCondition = "Выведите модули чисел",
        entryData = listOf(-1, -2, 3, 4),
        successData = listOf(1, 2, 3, 4)
    ) {
}