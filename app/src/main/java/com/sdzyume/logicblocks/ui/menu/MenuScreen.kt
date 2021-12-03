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
import com.sdzyume.logicblocks.R
import com.sdzyume.logicblocks.ui.Route
import com.sdzyume.logicblocks.ui.theme.AverBlue
import com.sdzyume.logicblocks.ui.theme.DarkBlue
import com.sdzyume.logicblocks.ui.theme.LightBlue


@Composable
fun MenuScreen(navController: NavController) {
    val fontFamily = FontFamily(Font(R.font.fontt))
    val bottomline by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.bottomline))
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AverBlue)
        )
        {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_dots12
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 420.dp)
                    .padding(start = 120.dp)
                    .scale(1.90f)
            )
            Image(
                painter = painterResource(
                    id = R.drawable.ic_dots23
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 405.dp)
                    .padding(start = 280.dp)
                    .scale(2f)
            )
            Image(
                painter = painterResource(
                    id = R.drawable.ic_dots34
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 396.dp)
                    .padding(start = 420.dp)
                    .scale(2.1f)
            )
            Image(
                painter = painterResource(
                    id = R.drawable.ic_dots45
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 427.dp)
                    .padding(start = 540.dp)
                    .scale(2.1f)
            )
            Text(
                text = stringResource(R.string.menu_levels),
                color = LightBlue,
                fontSize = 68.sp,
                fontFamily = fontFamily,
                modifier = Modifier
                    .padding(top = 100.dp)
                    .padding(start = 16.dp)
            )

            Row(
                modifier = Modifier
                    .padding(top = 320.dp)
                    .height(100.dp)
                    .fillMaxWidth()

            ) {
                Button(
                    onClick = { navController.navigate(Route.FirstLevel.route) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(top = 20.dp)
                        .size(width = 85.dp, height = 70.dp)

                ) {
                    Text(
                        text = stringResource(R.string.menu_level_1),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = fontFamily

                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 420.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ) {
                Button(
                    onClick = {navController.navigate(Route.SecondLevel.route)},
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(start = 180.dp)
                        .padding(top = 20.dp)
                        .size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_2),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = fontFamily
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 300.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ) {
                Button(
                    onClick = {navController.navigate(Route.ThirdLevel.route)},
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(start = 320.dp)
                        .padding(top = 20.dp)
                        .size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_3),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = fontFamily
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 440.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ) {
                Button(
                    onClick = {navController.navigate(Route.FourthLevel.route)},
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(start = 450.dp)
                        .padding(top = 20.dp)
                        .size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_4),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = fontFamily
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 330.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ) {
                Button(
                    onClick = {navController.navigate(Route.FifthLevel.route)},
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue),
                    modifier = Modifier
                        .padding(start = 590.dp)
                        .padding(top = 20.dp)
                        .size(width = 85.dp, height = 70.dp)
                ) {
                    Text(
                        text = stringResource(R.string.menu_level_5),
                        color = LightBlue,
                        fontSize = 45.sp,
                        fontFamily = fontFamily
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(start = 700.dp)
                    .width(60.dp)
                    .fillMaxHeight()
                    .background(AverBlue)
            ) {

            }
        }
    }
    LottieAnimation(
        bottomline,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier
            .padding(top = 130.dp)
            .width(730.dp)
            .scale(3.8f)
    )

}

