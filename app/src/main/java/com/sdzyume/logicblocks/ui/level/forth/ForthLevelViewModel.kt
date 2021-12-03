package com.sdzyume.logicblocks.ui.level.forth

import com.sdzyume.logicblocks.ui.base.BaseGameViewModel

class ForthLevelViewModel :
    BaseGameViewModel(
        title = "Уровень 4",
        mainCondition = "Вернуть только нули",
        entryData = listOf(4,0,8,0,15,0,16,0),
        successData = listOf(0,0,0,0)
    ) {
}