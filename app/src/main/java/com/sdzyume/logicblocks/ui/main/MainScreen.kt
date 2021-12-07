package com.sdzyume.logicblocks.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import com.sdzyume.logicblocks.ui.theme.*

@Composable
fun MainScreen(navController: NavController) {
    rememberSystemUiController().apply {
        setStatusBarColor(LightAnimBlue, darkIcons = false)
        setNavigationBarColor(DarkAnimBlue, darkIcons = false)
    }

    val fontFamily = FontFamily(Font(R.font.game_font))
    val backgr by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.back))

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            backgr,
            modifier = Modifier.fillMaxSize(),
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 215.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate(Route.Menu.route)
                },
                shape = CircleShape,
                border = BorderStroke(3.dp, DeepBlue),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue),
            ) {
                Text(
                    text = stringResource(R.string.btn_play),
                    color = DarkBlue,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate(Route.Onboarding.route)
                },
                shape = CircleShape,
                border = BorderStroke(3.dp, DeepBlue),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue),
            ) {
                Text(
                    text = stringResource(R.string.main_help),
                    color = DarkBlue,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    fontSize = 36.sp,
                    fontFamily = fontFamily,
                )
            }
        }
    }
}