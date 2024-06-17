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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
           modifier = Modifier
               .fillMaxWidth()
               .height(700.dp)
               .padding(24.dp, 64.dp, 24.dp, 0.dp)
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
                                Color(0xBB8F0101),
                                Color(0xBB4D0000)
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
                        .background(Color(0xEE000000))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Level ${userState.level}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Next Level Up: ${userState.experience}/${userState.nextLevelUp}",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Available Points: ${userState.points}",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 8.dp)
                    )

                    UpgradeItem(
                        title = "Health",
                        description = "Increase the max health",
                        value = userState.health.toString(),
                        runnable = {
                            UserState.addHealth()
                        }
                    )

                    UpgradeItem(
                        title = "Strength",
                        description = "Increase the damage base of all attacks.",
                        value = userState.strength.toString(),
                        runnable = {
                            UserState.addStrength()
                        }
                    )

                    UpgradeItem(
                        title = "Dexterity",
                        description = "Decrease the interval between spells",
                        value = userState.dexterity.toString(),
                        runnable = {
                            UserState.addDexterity()
                        }
                    )

                    UpgradeItem(
                        title = "Luck",
                        description = "Increase the acquired when fights",
                        value = userState.luck.toString(),
                        runnable = {
                            UserState.addLuck()
                        }
                    )
                }
            }
        }
    }
}