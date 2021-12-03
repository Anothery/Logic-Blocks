package com.sdzyume.logicblocks.ui


sealed class Route(val route: String) {
    object Main : Route("MainScreen")
    object Menu : Route("MenuScreen")
    object FirstLevel : Route("FirstLevel")
    object SecondLevel : Route("SecondLevel")
    object ThirdLevel : Route("ThirdLevel")
    object FourthLevel : Route("FourthLevel")
    object FifthLevel : Route("FifthLevel")
}
