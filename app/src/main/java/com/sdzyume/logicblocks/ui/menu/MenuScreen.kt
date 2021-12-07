package com.sdzyume.logicblocks.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sdzyume.logicblocks.R
import com.sdzyume.logicblocks.ui.Route
import com.sdzyume.logicblocks.ui.theme.AverBlue
import com.sdzyume.logicblocks.ui.theme.DarkAnimBlue
import com.sdzyume.logicblocks.ui.theme.DarkBlue
import com.sdzyume.logicblocks.ui.theme.LightBlue


@Composable
fun MenuScreen(navController: NavController) {
    rememberSystemUiController().apply {
        setStatusBarColor(AverBlue, darkIcons = false)
        setNavigationBarColor(DarkAnimBlue, darkIcons = false)
    }

    val gameFont = FontFamily(Font(R.font.game_font))
    val bottomLine by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bottomline))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AverBlue)
    ) {
        LottieAnimation(
            bottomLine,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth()
                .scale(4f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.menu_levels),
                    color = LightBlue,
                    fontSize = 72.sp,
                    fontFamily = gameFont,
                    modifier = Modifier.padding(top = 100.dp, start = 16.dp)
                )
            }
            Row(modifier = Modifier.padding(top = 100.dp, start = 16.dp, end = 16.dp)) {
                Button(
                    onClick = { navController.navigate(Route.FirstLevel.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier.size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_1),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = gameFont
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_dots12),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .size(80.dp, 70.dp)
                )
                Button(
                    onClick = { navController.navigate(Route.SecondLevel.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(top = 115.dp)
                        .size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_2),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = gameFont
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_dots23),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .size(80.dp, 70.dp)
                )

                Button(
                    onClick = { navController.navigate(Route.ThirdLevel.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier.size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_3),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = gameFont
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_dots34),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .size(80.dp, 70.dp)
                )

                Button(
                    onClick = { navController.navigate(Route.FourthLevel.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(top = 115.dp)
                        .size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_4),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = gameFont
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_dots45),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 55.dp)
                        .size(80.dp, 70.dp)
                )

                Button(
                    onClick = { navController.navigate(Route.FifthLevel.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier.size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_5),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = gameFont
                    )
                }
            }
        }
    }
}

