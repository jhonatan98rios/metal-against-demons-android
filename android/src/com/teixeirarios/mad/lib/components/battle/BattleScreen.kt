package com.teixeirarios.mad.lib.components.battle

import VideoBackground
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teixeirarios.mad.R
import com.teixeirarios.mad.lib.components.home.StageModel
import com.teixeirarios.mad.lib.components.home.StageSelector
import com.teixeirarios.mad.lib.store.userstate.UserState

@Composable
fun BattleScreen(onButtonClick: (Int) -> Unit, stageList: List<StageModel>) {

    val userState by UserState.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // VideoBackground()

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

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(96.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Metal Against Demons",
                    modifier = Modifier
                        .height(96.dp)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            StageSelector(stageList, onButtonClick)
        }
    }
}