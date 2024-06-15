package com.teixeirarios.mad.lib.components.upgrade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teixeirarios.mad.lib.store.userstate.UserState

@Composable
fun UpgradeScreen() {

    val userState by UserState.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "R$${userState.money}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 6f
                    )
                )
            )
        }

        Box(
           modifier = Modifier
               .fillMaxWidth()
               .height(700.dp)
               .padding(24.dp, 80.dp, 24.dp, 0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(480.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF8F0101),
                                Color(0xFF4D0000)
                            )
                        )
                    )
                ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(300.dp)
                        .height(448.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black)
                ) {


                }
            }
        }
    }
}