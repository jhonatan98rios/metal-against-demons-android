package com.teixeirarios.mad.lib.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StageSelectorCard (
    stageId: String,
    onButtonClick: (String) -> Unit
){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(150.dp)
            .width(250.dp)
            .padding(10.dp),
        backgroundColor = Color.LightGray,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Stage $stageId", fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
            Button(
                onClick = { onButtonClick(stageId) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
            ) {
                Text(
                    text = "Start",
                    color = Color.White
                )
            }
        }
    }
}