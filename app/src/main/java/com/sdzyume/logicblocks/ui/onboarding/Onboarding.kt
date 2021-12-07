package com.sdzyume.logicblocks.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sdzyume.logicblocks.R
import com.sdzyume.logicblocks.ui.theme.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Onboarding(navController: NavController) {
    rememberSystemUiController().apply {
        setStatusBarColor(AverBlue, darkIcons = false)
        setNavigationBarColor(AverBlue, darkIcons = false)
    }

    val pagerState = rememberPagerState(pageCount = 4)

    Column(
        modifier = Modifier.background(AverBlue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.onboarding_skip), modifier = Modifier
                    .padding(16.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { navController.popBackStack() },
                color = Color.White
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {
                0 -> FirstOnboarding()
                1 -> SecondOnboarding()
                2 -> ThirdOnboarding()
                3 -> FourthOnboarding()
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = LightBlue
        )

        AnimatedVisibility(visible = pagerState.currentPage == 3) {
            Button(
                onClick = { navController.popBackStack() },
                shape = CircleShape,
                border = BorderStroke(3.dp, DeepBlue),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightBlue),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.onboarding_get_started),
                    color = DarkBlue,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.game_font)),
                )
            }
        }

    }
}