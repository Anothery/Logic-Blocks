package com.sdzyume.logicblocks.ui.level.fifth

import com.sdzyume.logicblocks.ui.base.BaseGameViewModel

class FifthLevelViewModel :
    BaseGameViewModel(
        title = "Уровень 5",
        mainCondition = "Если положительное - взять квадрат\nЕсли отрицательное - вернуть 0",
        entryData = listOf(-1,2,-3,4,-5,6),
        successData = listOf(0,4,0,16,0,36)
    ) {
}