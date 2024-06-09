package com.teixeirarios.mad.lib.components.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NavigationBar (navController: NavHostController) {
    Row (
        Modifier
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Box(){
            Button(onClick = { navController.navigate("Player") }) {
                Text(text = "Player")
            }
        }

        Box(){
            Button(onClick = { navController.navigate("Battle") }) {
                Text(text = "Battle")
            }
        }

        Box(){
            Button(onClick = { navController.navigate("Quests") }) {
                Text(text = "Quests")
            }
        }
    }
}