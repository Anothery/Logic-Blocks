package com.sdzyume.logicblocks.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdzyume.logicblocks.R
import com.sdzyume.logicblocks.model.Action
import com.sdzyume.logicblocks.ui.theme.LightBlue

@Composable
fun ThirdOnboarding() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.onboarding_third_1),
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
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(Action.Get().color)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.action_get),
                        color = Color.White,
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val multiplyColor = Action.Multiply(0).color
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(
                                topStart = 35.dp,
                                bottomStart = 35.dp
                            )
                        )
                        .background(multiplyColor)
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
                            1.dp, multiplyColor, RoundedCornerShape(
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
                        text = "5",
                        color = multiplyColor,
                        textAlign = TextAlign.Center,
                    )
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(Action.Send().color)
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
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.onboarding_third_2),
            textAlign = TextAlign.Center, fontSize = 18.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightBlue)
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val sqrColor = Action.Sqr().color
            Text(
                "если ≠ 0",
                color = sqrColor,
                fontSize = 12.sp,
            )
            Row(
                modifier = Modifier.height(40.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(sqrColor)
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
    }

}
