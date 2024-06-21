package com.teixeirarios.mad.lib.components.battle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teixeirarios.mad.lib.analytics.AnalyticsService

@Composable
fun StageSelectorCard (
    stageModel: StageModel,
    onButtonClick: (Int) -> Unit
){

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(250.dp)
            .width(250.dp)
            .shadow(
                elevation = 10.dp,
                spotColor = Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
    ) {

        Image(
            painter = painterResource(id = stageModel.background),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stageModel.title,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(4f, 4f),
                            blurRadius = 6f
                        )
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Kill at least ${stageModel.goal} demons",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.White,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(4f, 4f),
                            blurRadius = 6f
                        )
                    )
                )
            }

            Button(
                onClick = {
                    onButtonClick(stageModel.id)
                    AnalyticsService.logCustomEvent("click", mapOf(
                        "category" to "Stage Selector Card",
                        "action" to "Select stage ${stageModel.id}"
                    ))
              },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFAA0000))
            ) {
                Text(
                    text = "Start",
                    color = Color.White
                )
            }
        }
    }
}