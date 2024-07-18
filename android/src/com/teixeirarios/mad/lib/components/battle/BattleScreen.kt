package com.teixeirarios.mad.lib.components.battle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teixeirarios.mad.lib.analytics.AnalyticsService
import com.teixeirarios.mad.lib.store.userstate.UserState

@Composable
fun BattleScreen(onButtonClick: (Int) -> Unit, stageList: List<StageModel>) {

    LaunchedEffect(Unit) {
        AnalyticsService.logScreenView("Battle Screen", "BattleScreen.kt")
    }

    val userState by UserState.state.collectAsState()
    val availableStages = stageList.filter { it.id <= userState.currentStage }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            StageSelector(
                availableStages,
                onButtonClick
            )
        }
    }
}