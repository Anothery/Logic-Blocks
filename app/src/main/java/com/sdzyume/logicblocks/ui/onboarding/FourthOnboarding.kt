package com.sdzyume.logicblocks.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdzyume.logicblocks.R
import com.sdzyume.logicblocks.model.Action
import com.sdzyume.logicblocks.ui.theme.LightBlue

@Composable
fun FourthOnboarding() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.onboarding_fourth_1),
            textAlign = TextAlign.Center, fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val backToColor = Action.BackTo(1).color
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = 35.dp,
                                bottomStart = 35.dp
                            )
                        )
                        .background(backToColor)
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
                            1.dp, backToColor, RoundedCornerShape(
                                topEnd = 35.dp,
                                bottomEnd = 35.dp
                            )
                        )
                        .clip(
                            RoundedCornerShape(
                                topEnd = 35.dp,
                                bottomEnd = 35.dp
                            )
                        )
                        .background(Color.White)
                        .padding(start = 12.dp, end = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "1",
                        color = backToColor,
                        textAlign = TextAlign.Center,
                    )
                }

            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.onboarding_fourth_2),
            textAlign = TextAlign.Center, fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }

}
