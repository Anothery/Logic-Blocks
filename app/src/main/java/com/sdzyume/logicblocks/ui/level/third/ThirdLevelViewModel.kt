package com.sdzyume.logicblocks.ui.level.third

import com.sdzyume.logicblocks.ui.base.BaseGameViewModel

class ThirdLevelViewModel :
    BaseGameViewModel(
        title = "Уровень 3",
        mainCondition = "Преобразовать каждый элемент в единицу",
        entryData = listOf(4,8,16,32),
        successData = listOf(1,1,1,1)
    ) {
}