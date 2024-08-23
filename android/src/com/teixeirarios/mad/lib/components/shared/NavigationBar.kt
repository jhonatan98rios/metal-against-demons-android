package com.teixeirarios.mad.lib.components.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.teixeirarios.mad.R
import com.teixeirarios.mad.lib.analytics.AnalyticsService
import com.teixeirarios.mad.lib.store.tokenState.TokenState

@Composable
fun NavigationBar (navController: NavHostController) {

    val context = LocalContext.current
    val token by TokenState.tokenFlow.collectAsState()
    val isLogged = token != null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp, 0.dp, 8.dp, 16.dp),
    ) {

        Row (
            Modifier
                .fillMaxWidth()
                .border(shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color.DarkGray)
                .background(Color(0x99000000)),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    //.border(shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color.White)
                    .padding(8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.locked),
                        contentDescription = "Quests Screen Navigation",
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .clickable {
//                                val currentRoute = navController.currentBackStackEntry?.destination?.route
//                                if (currentRoute != "Quests") {
//                                    navController.navigate("Quests")
//                                }
                            }
                    )
                    Text(text = "Quests")
                }
            }

            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.upgrade),
                        contentDescription = "Upgrade Screen Navigation",
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .clickable {
                                val currentRoute = navController.currentBackStackEntry?.destination?.route
                                if (currentRoute != "Upgrade") {
                                    navController.navigate("Upgrade")
                                    AnalyticsService.logCustomEvent("click", mapOf(
                                        "category" to "Navigation Bar",
                                        "action" to "Navigate",
                                        "label" to "UpgradeScreen",
                                    ))
                                }
                            }
                    )
                    Text(text = "Upgrade")
                }
            }

            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    //.border(shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color.White)
                    .padding(8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.battle),
                        contentDescription = "Battle Screen Navigation",
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .clickable {
                                val currentRoute = navController.currentBackStackEntry?.destination?.route
                                if (currentRoute != "Battle") {
                                    navController.navigate("Battle")
                                    AnalyticsService.logCustomEvent("click", mapOf(
                                        "category" to "Navigation Bar",
                                        "action" to "Navigate",
                                        "label" to "BattleScreen",
                                    ))
                                }
                            }
                    )
                    Text(text = "Battle")
                }
            }

            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    //.border(shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color.White)
                    .padding(8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.locked),
                        contentDescription = "Quests Screen Navigation",
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .clickable {
//                                val currentRoute = navController.currentBackStackEntry?.destination?.route
//                                if (currentRoute != "Quests") {
//                                    navController.navigate("Quests")
//                                }
                            }
                    )
                    Text(text = "Quests")
                }
            }

            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    //.border(shape = RoundedCornerShape(8.dp), width = 1.dp, color = Color.White)
                    .padding(8.dp)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    val profileImage = if (isLogged) R.drawable.profile_light else R.drawable.profile_dark

                    Image(
                        painter = painterResource(id = profileImage),
                        contentDescription = "Quests Screen Navigation",
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .clickable {
                                val currentRoute = navController.currentBackStackEntry?.destination?.route
                                if (currentRoute != "Profile") {
                                    navController.navigate("Profile")
                                }
                            }
                    )
                    Text(text = "Profile")
                }
            }
        }
    }
}