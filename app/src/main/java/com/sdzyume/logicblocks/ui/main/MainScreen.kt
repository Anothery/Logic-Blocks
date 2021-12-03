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
import com.sdzyume.logicblocks.ui.theme.DarkBlue
import com.sdzyume.logicblocks.ui.theme.DeepBlue
import com.sdzyume.logicblocks.ui.theme.LightBlue

@Composable
fun MainScreen(navController: NavController) {
    val fontFamily = FontFamily(Font(R.font.fontt))
    val backgr by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.back))
    LottieAnimation(
        backgr, iterations = LottieConstants.IterateForever, modifier = Modifier
            .fillMaxSize()
            .scale(1.2f)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 16.dp).fillMaxWidth()
        )
        Button(
            onClick = {
                navController.navigate(Route.Menu.route)
            },
            shape = CircleShape,
            border = BorderStroke(3.dp, DeepBlue),
            colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(width = 220.dp, height = 90.dp)
        ) {
            Text(
                text = stringResource(R.string.btn_play),
                color = DarkBlue,
                fontSize = 40.sp,
                fontFamily = fontFamily,
            )
        }

    }

}