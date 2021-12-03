package com.sdzyume.logicblocks.ui.level.first

import com.sdzyume.logicblocks.ui.base.BaseGameViewModel

class FirstLevelViewModel :
    BaseGameViewModel(
        title = "Уровень 1",
        mainCondition = "Каждую вводимую вещь умножайте на 3 и выведите результат",
        entryData = listOf(5, -2, 12, 6),
        successData = listOf(15, -6, 36, 18)
    ) {
}