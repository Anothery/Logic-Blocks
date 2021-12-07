package com.sdzyume.logicblocks.ui.base

import android.widget.NumberPicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sdzyume.logicblocks.R
import com.sdzyume.logicblocks.ui.Route
import com.sdzyume.logicblocks.model.Action
import com.sdzyume.logicblocks.model.Condition
import com.sdzyume.logicblocks.ui.theme.*
import com.sdzyume.logicblocks.utils.shortToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BaseLevelScreen(
    navController: NavController,
    state: LevelContract.State,
    effectFlow: Flow<LevelContract.Effect>,
    onEvent: (LevelContract.Event) -> Unit
) {
    rememberSystemUiController().apply {
        setStatusBarColor(DarkBlue, darkIcons = false)
        setNavigationBarColor(DarkBlue, darkIcons = false)
    }

    var drawerOffset by remember { mutableStateOf(0f) }
    val confetti by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val chalice by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.winner))

    Box(modifier = Modifier.fillMaxSize()) {
        ModalDrawer(drawerContent = {
            if (!state.isChecking) {
                FirstLevelCommands(
                    state,
                    onEvent = onEvent
                )
            }
        },
            drawerShape = object : Shape {
                override fun createOutline(
                    size: Size,
                    layoutDirection: LayoutDirection,
                    density: Density
                ): Outline {
                    return Outline.Rectangle(with(density) {
                        Rect(
                            0f,
                            0f,
                            200.dp.toPx(),
                            size.height
                        )
                    })
                }

            }, drawerBackgroundColor = Color.Transparent, drawerElevation = 0.dp,
            modifier = Modifier.onGloballyPositioned { drawerOffset = it.positionInWindow().x }) {
            BaseLevelContent(navController, drawerOffset, state, effectFlow, onEvent)
        }
        AnimatedVisibility(
            visible = state.levelCompleted,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            )
            LottieAnimation(
                confetti,
                iterations = IterateForever,
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillWidth
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LottieAnimation(
                    chalice,
                    modifier = Modifier.align(Alignment.Center),
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    stringResource(R.string.level_win),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(32.dp),
                    color = Color.White
                )
                OutlinedButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(32.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(width = 4.dp, color = Color.White)
                ) {
                    Text(
                        stringResource(R.string.level_win_to_menu).uppercase(),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseLevelContent(
    navController: NavController,
    offsetX: Float,
    state: LevelContract.State,
    effectFlow: Flow<LevelContract.Effect>,
    onEvent: (LevelContract.Event) -> Unit
) {
    val density = LocalDensity.current
    val swipeRightAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.swiperight))
    val errorAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    var showCheckButton by remember { mutableStateOf(true) }
    var showErrorAnimation by remember { mutableStateOf(false) }
    val commandsListState = rememberLazyListState()
    var showConditionPicker by remember { mutableStateOf(false) }
    var selectedCommandIndex by remember { mutableStateOf(0) }


    LaunchedEffect(Unit) {
        effectFlow.collect {
            when (it) {
                is LevelContract.Effect.LevelFailed -> {
                    commandsListState.animateScrollToItem(0)
                    showCheckButton = false
                    showErrorAnimation = true
                    delay(2000)
                    showCheckButton = true
                    showErrorAnimation = false
                }
                is LevelContract.Effect.ScrollToCommand -> {
                    if (it.index > commandsListState.layoutInfo.visibleItemsInfo.size - 2 || it.index == 0) {
                        commandsListState.animateScrollToItem(it.index)
                    }
                }
            }
        }
    }

    Scaffold(topBar = {
        Column(modifier = Modifier.background(DarkBlue)) {
            TopAppBar(elevation = 0.dp) {
                Box(
                    Modifier.fillMaxWidth(),
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Outlined.Logout,
                            stringResource(R.string.icon_back_to_menu),
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(16.dp)
                        )
                    }

                    Text(
                        text = state.levelTitle,
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            if (state.startData.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(state.startData) { i, it ->
                        Column(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .animateItemPlacement()
                                .clickable { },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$it",
                                color = DarkBlue
                            )
                        }
                    }
                }
            } else {
                Spacer(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(40.dp)
                )
            }

        }
    },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = showCheckButton,
                        enter = fadeIn(animationSpec = tween(1000)),
                        exit = fadeOut(
                            animationSpec = tween(500)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedButton(
                                onClick = {
                                    if (state.isChecking) onEvent(LevelContract.Event.StopCheck) else onEvent(
                                        LevelContract.Event.Check
                                    )
                                },
                                shape = CircleShape,
                                border = BorderStroke(2.dp, Green),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Green),
                            ) {

                                if (state.isChecking) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .size(20.dp),
                                        color = Green
                                    )
                                } else {
                                    Text(stringResource(R.string.level_check), fontSize = 20.sp)
                                }
                            }
                        }
                    }
                    androidx.compose.animation.AnimatedVisibility(
                        visible = !showCheckButton,
                        enter = fadeIn(animationSpec = tween(1000)),
                        exit = fadeOut(animationSpec = tween(500))
                    ) {
                        LottieAnimation(
                            errorAnimation,
                            iterations = IterateForever,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxHeight()
                        )
                    }
                }

                Column(modifier = Modifier.background(DarkBlue)) {
                    if (state.resultData.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            itemsIndexed(state.resultData) { i, it ->
                                val isItemCorrect =
                                    state.resultData[i] == state.successDataToCompare.getOrNull(i)
                                Column(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .height(40.dp)
                                        .clip(CircleShape)
                                        .background(if (isItemCorrect) Green else Red)
                                        .border(1.dp, Color.White, CircleShape)
                                        .padding(horizontal = 16.dp)
                                        .animateItemPlacement()
                                        .clickable { },
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$it",
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .clickable { },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "?", color = DarkBlue)
                            }
                        }
                    }
                }
            }
        }, modifier = Modifier.offset(x = with(density) { offsetX.toDp() })
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.mainCondition,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center, color = Color.DarkGray
            )
            if (state.commands.isNotEmpty()) {
                LazyColumn(
                    state = commandsListState,
                    modifier = Modifier
                        .indication(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        )
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(state.commands) { index, command ->
                        ConstraintLayout(modifier = Modifier.height(65.dp)) {
                            val (cnd, nmb, btn, cross, current) = createRefs()

                            command.condition?.let { condition ->
                                val equalSymbol = when (condition) {
                                    is Condition.LesserOrEqual -> Condition.LESSER_OR_EQUAL
                                    is Condition.Lesser -> Condition.LESSER
                                    is Condition.Equals -> Condition.EQUALS
                                    is Condition.NotEquals -> Condition.NOT_EQUALS
                                    is Condition.Greater -> Condition.GREATER
                                    is Condition.GreaterOrEqual -> Condition.GREATER_OR_EQUAL
                                }

                                Text(
                                    "если $equalSymbol ${condition.numberToCompare}",
                                    color = command.color,
                                    fontSize = 12.sp,
                                    modifier = Modifier.constrainAs(cnd) {
                                        bottom.linkTo(btn.top)
                                        start.linkTo(btn.start)
                                        end.linkTo(btn.end)
                                    })
                            }


                            Text(
                                text = "${index + 1}",
                                color = Color.LightGray,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .constrainAs(nmb) {
                                        end.linkTo(btn.start)
                                        top.linkTo(btn.top)
                                        bottom.linkTo(btn.bottom)
                                    }
                                    .padding(end = 8.dp)
                            )

                            Column(modifier = Modifier
                                .constrainAs(btn) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .pointerInput(Unit) {
                                    detectTapGestures(onLongPress = {
                                        showConditionPicker = true
                                        selectedCommandIndex = index
                                    })
                                }) {
                                when (command) {
                                    is Action.Get -> {
                                        Row(
                                            modifier = Modifier.height(40.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(CircleShape)
                                                    .background(command.color)
                                                    .padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.action_get),
                                                    color = Color.White,
                                                )
                                            }

                                        }
                                    }
                                    is Action.Sqr -> {
                                        Row(
                                            modifier = Modifier.height(40.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(CircleShape)
                                                    .background(command.color)
                                                    .padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.action_sqr),
                                                    color = Color.White,
                                                )
                                            }

                                        }
                                    }
                                    is Action.Divide -> {
                                        Row(
                                            modifier = Modifier.height(40.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 35.dp,
                                                            bottomStart = 35.dp
                                                        )
                                                    )
                                                    .background(command.color)
                                                    .padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.action_divide),
                                                    color = Color.White,
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .border(
                                                        1.dp, command.color, RoundedCornerShape(
                                                            topEnd = 35.dp,
                                                            bottomEnd = 35.dp
                                                        )
                                                    )
                                                    .padding(start = 12.dp, end = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${command.to}",
                                                    color = command.color,
                                                    textAlign = TextAlign.Center,
                                                )
                                            }

                                        }
                                    }
                                    is Action.BackTo -> {
                                        Row(
                                            modifier = Modifier.height(40.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 35.dp,
                                                            bottomStart = 35.dp
                                                        )
                                                    )
                                                    .background(command.color)
                                                    .padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.action_back_to),
                                                    color = Color.White,
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .border(
                                                        1.dp, command.color, RoundedCornerShape(
                                                            topEnd = 35.dp,
                                                            bottomEnd = 35.dp
                                                        )
                                                    )
                                                    .padding(start = 12.dp, end = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${command.to}",
                                                    color = command.color,
                                                    textAlign = TextAlign.Center,
                                                )
                                            }

                                        }
                                    }
                                    is Action.Multiply -> {
                                        Row(
                                            modifier = Modifier.height(40.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(
                                                        RoundedCornerShape(
                                                            topStart = 35.dp,
                                                            bottomStart = 35.dp
                                                        )
                                                    )
                                                    .background(command.color)
                                                    .padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(id = R.string.action_multiply),
                                                    color = Color.White,
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .border(
                                                        1.dp, command.color, RoundedCornerShape(
                                                            topEnd = 35.dp,
                                                            bottomEnd = 35.dp
                                                        )
                                                    )
                                                    .padding(start = 12.dp, end = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = "${command.to}",
                                                    color = command.color,
                                                    textAlign = TextAlign.Center,
                                                )
                                            }

                                        }
                                    }
                                    is Action.Send -> {
                                        Row(
                                            modifier = Modifier.height(40.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxHeight()
                                                    .clip(CircleShape)
                                                    .background(command.color)
                                                    .padding(horizontal = 16.dp),
                                                verticalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.action_send),
                                                    color = Color.White,
                                                )
                                            }

                                        }
                                    }
                                }
                            }

                            if (!state.isChecking) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    "",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .constrainAs(cross) {
                                            start.linkTo(btn.end)
                                            top.linkTo(btn.top)
                                            bottom.linkTo(btn.bottom)
                                        }
                                        .padding(4.dp)
                                        .size(20.dp)
                                        .clickable(
                                            onClick = {
                                                onEvent(
                                                    LevelContract.Event.RemoveCommand(
                                                        index
                                                    )
                                                )
                                            },
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(bounded = false)
                                        )
                                )
                            } else {
                                state.currentValue?.let { cv ->
                                    if (cv.actionIndex == index) {
                                        Text(
                                            cv.currentValue.toString(),
                                            color = command.color,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            modifier = Modifier
                                                .constrainAs(current) {
                                                    start.linkTo(btn.end)
                                                    top.linkTo(btn.top)
                                                    bottom.linkTo(btn.bottom)
                                                }
                                                .padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.no_commans),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    LottieAnimation(
                        composition = swipeRightAnimation,
                        iterations = IterateForever,
                        modifier = Modifier.size(120.dp), speed = 0.8f
                    )

                    Text(
                        stringResource(R.string.hint_swipe_to_create_command),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        stringResource(R.string.hint_long_press_to_add_condition),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (showConditionPicker) {
            ConditionPickerDialog({ condition ->
                showConditionPicker = false
                condition?.let {
                    onEvent(
                        LevelContract.Event.AddConditionToItem(
                            selectedCommandIndex,
                            it
                        )
                    )
                }
            }, {
                showConditionPicker = false
                onEvent(LevelContract.Event.RemoveConditionFromItem(selectedCommandIndex))
            }, { showConditionPicker = false })
        }
    }

}

@Composable
fun FirstLevelCommands(state: LevelContract.State, onEvent: (LevelContract.Event) -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    var selectedCommand by remember { mutableStateOf<Action?>(null) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.commands),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            color = Color.White, style = MaterialTheme.typography.subtitle1.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 8f
                )
            )
        )
        CommandItem(
            text = stringResource(R.string.action_get),
            color = Action.Get().color,
            onClick = { onEvent(LevelContract.Event.CommandClicked(Action.Get())) })
        CommandItem(
            text = stringResource(R.string.action_multiply),
            color = Action.Multiply(0).color,
            onClick = {
                selectedCommand = Action.Multiply(0)
                openDialog = true
            })
        CommandItem(
            text = stringResource(R.string.action_divide),
            color = Action.Divide(1).color,
            onClick = {
                selectedCommand = Action.Divide(1)
                openDialog = true
            })
        CommandItem(
            text = stringResource(R.string.action_sqr),
            color = Action.Sqr().color,
            onClick = { onEvent(LevelContract.Event.CommandClicked(Action.Sqr())) })
        CommandItem(
            text = stringResource(R.string.action_back_to),
            color = Action.BackTo(0).color,
            onClick = {
                selectedCommand = Action.BackTo(0)
                openDialog = true
            })
        CommandItem(
            text = stringResource(id = R.string.action_send),
            color = Action.Send().color,
            onClick = { onEvent(LevelContract.Event.CommandClicked(Action.Send())) })
    }
    if (openDialog) {
        when (selectedCommand) {
            is Action.Multiply -> NumberPickerDialog(
                stringResource(R.string.number_dialog_enter_number),
                {
                    openDialog = false; onEvent(
                    LevelContract.Event.CommandClicked(
                        Action.Multiply(
                            it
                        )
                    )
                )
                },
                { openDialog = false }
            )
            is Action.Divide -> NumberPickerDialog(
                stringResource(R.string.number_dialog_enter_number),
                {
                    openDialog =
                        false; onEvent(LevelContract.Event.CommandClicked(Action.Divide(it)))
                },
                { openDialog = false }
            )
            is Action.BackTo -> {
                if (state.commands.isEmpty()) {
                    LocalContext.current.shortToast(stringResource(R.string.message_add_at_least_one_command))
                    openDialog = false
                } else {
                    NumberPickerDialog(
                        stringResource(R.string.write_command_number_to_back),
                        {
                            openDialog =
                                false; onEvent(LevelContract.Event.CommandClicked(Action.BackTo(it)))
                        },
                        { openDialog = false }, 1, state.commands.size
                    )
                }
            }
            else -> {}
        }
    }
}


@Composable
fun CommandItem(text: String, color: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp))
                    .background(color)
                    .clickable { onClick() }
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    color = Color.White,
                )
            }

        }
    }
}

@Composable
fun NumberPickerDialog(
    title: String = "",
    onPicked: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    min: Int? = null,
    max: Int? = null
) {
    val minNegate = -100
    val maxNegate = 100 - minNegate

    val useNegate = min == null || max == null

    var number by remember { mutableStateOf(if (useNegate) 0 else min!!) }


    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    NumberPicker(context).apply {
                        descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                        setOnValueChangedListener { numberPicker, _, _ ->
                            number =
                                if (useNegate) numberPicker.value + minNegate else numberPicker.value
                        }
                        if (useNegate) setFormatter { index -> (index + minNegate).toString() }

                        minValue = if (useNegate) 0 else min!!
                        maxValue = if (useNegate) maxNegate else max!!
                        value = if (useNegate) number - minNegate else number
                    }
                }
            )
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    onClick = { onPicked.invoke(number) },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, DarkBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlue),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.command_accept),
                        Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ConditionPickerDialog(
    onPicked: (Condition?) -> Unit,
    onClear: () -> Unit,
    onDismissRequest: () -> Unit,
    defaultCondition: String = Condition.EQUALS,
    defaultNumber: Int = 0
) {
    var selectedCondition by remember { mutableStateOf(defaultCondition) }
    var number by remember { mutableStateOf(defaultNumber) }

    val min = -1000
    val max = 2000

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.text_if_condition_title),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )

                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    EqualityButton(
                        conditionType = Condition.LESSER,
                        onClick = { selectedCondition = Condition.LESSER },
                        selectedCondition == Condition.LESSER
                    )
                    EqualityButton(
                        conditionType = Condition.EQUALS,
                        onClick = { selectedCondition = Condition.EQUALS },
                        selectedCondition == Condition.EQUALS
                    )
                    EqualityButton(
                        conditionType = Condition.GREATER,
                        onClick = { selectedCondition = Condition.GREATER },
                        selectedCondition == Condition.GREATER
                    )

                }
                Row {
                    EqualityButton(
                        conditionType = Condition.LESSER_OR_EQUAL,
                        onClick = { selectedCondition = Condition.LESSER_OR_EQUAL },
                        selectedCondition == Condition.LESSER_OR_EQUAL
                    )
                    EqualityButton(
                        conditionType = Condition.NOT_EQUALS,
                        onClick = { selectedCondition = Condition.NOT_EQUALS },
                        selectedCondition == Condition.NOT_EQUALS
                    )
                    EqualityButton(
                        conditionType = Condition.GREATER_OR_EQUAL,
                        onClick = { selectedCondition = Condition.GREATER_OR_EQUAL },
                        selectedCondition == Condition.GREATER_OR_EQUAL
                    )
                }


                Text(
                    text = when (selectedCondition) {
                        Condition.EQUALS -> stringResource(R.string.text_equals)
                        Condition.NOT_EQUALS -> stringResource(R.string.text_not_equals)
                        Condition.LESSER -> stringResource(R.string.text_lesser)
                        Condition.LESSER_OR_EQUAL -> stringResource(R.string.test_lesser_or_equal)
                        Condition.GREATER -> stringResource(R.string.text_greater)
                        Condition.GREATER_OR_EQUAL -> stringResource(R.string.text_greater_or_equal)
                        else -> stringResource(R.string.test_default)
                    },
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
                AndroidView(
                    modifier = Modifier.width(200.dp),
                    factory = { context ->
                        NumberPicker(context).apply {
                            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                            setOnValueChangedListener { numberPicker, _, _ ->
                                number = numberPicker.value + min
                            }
                            setFormatter { index -> (index + min).toString() }
                            minValue = 0
                            maxValue = max
                            value = number - min
                        }
                    }
                )

            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    onClick = { onClear() },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Red),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Red),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.condition_clear),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onPicked(
                            when (selectedCondition) {
                                Condition.EQUALS -> Condition.Equals(number)
                                Condition.NOT_EQUALS -> Condition.NotEquals(number)
                                Condition.GREATER -> Condition.Greater(number)
                                Condition.GREATER_OR_EQUAL -> Condition.GreaterOrEqual(number)
                                Condition.LESSER -> Condition.Lesser(number)
                                Condition.LESSER_OR_EQUAL -> Condition.LesserOrEqual(number)
                                else -> null
                            }
                        )
                    },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, DarkBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlue),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.condition_accept),
                        Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun EqualityButton(conditionType: String, onClick: () -> Unit, selected: Boolean = false) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(40.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, DarkBlue),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (selected) Color.White else DarkBlue,
            backgroundColor = if (selected) DarkBlue else Color.Transparent
        ), contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = conditionType, fontSize = 20.sp)
    }
}

