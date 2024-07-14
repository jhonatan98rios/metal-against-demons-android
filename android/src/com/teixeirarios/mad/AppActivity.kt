package com.teixeirarios.mad

import VideoBackground
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.teixeirarios.mad.lib.admob.AdmobService
import com.teixeirarios.mad.lib.analytics.AnalyticsService
import com.teixeirarios.mad.lib.components.battle.BattleScreen
import com.teixeirarios.mad.lib.components.battle.StageManager
import com.teixeirarios.mad.lib.components.shared.NavigationBar
import com.teixeirarios.mad.lib.components.shared.TopBar
import com.teixeirarios.mad.lib.components.upgrade.UpgradeScreen
import com.teixeirarios.mad.lib.infra.database.repository.AppContext
import com.teixeirarios.mad.lib.store.userstate.UserState


class AppActivity : AppCompatActivity(), AppContext {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreen()
        UserState.init(this)

        setContent {
            MainScreen()
        }

        AnalyticsService.init(this)
        AdmobService.init(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            setFullscreen()
        }
    }

    private fun setFullscreen() {
        val isAndroid11OrHigher = false // Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

        if (isAndroid11OrHigher) {
            // Para Android 11 (API 30) e acima
//            window.setDecorFitsSystemWindows(false)
//            window.insetsController?.let { controller ->
//                controller.hide(WindowInsets.Type.systemBars())
//                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
        } else {
            // Para versões anteriores do Android
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            )
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val intent = Intent(context, AndroidLauncher::class.java)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val (scale, offsetX, offsetY) = when (currentRoute) {
        "Upgrade" -> Triple(1.7f, 240f, -300f) // Zoom no player
        "Battle" -> Triple(1.0f, 0f, 0f) // Sem zoom
        "Quests" -> Triple(2.5f, 0f, -400f) // Zoom na fogueira
        else -> Triple(1.0f, 0f, 0f)
    }

    VideoBackground(scale, offsetX, offsetY)

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { NavigationBar(navController = navController) },
        backgroundColor = Color.Transparent
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Battle",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable("Upgrade") {
                UpgradeScreen()
            }
            composable("Battle") {
                BattleScreen(
                    onButtonClick = { stageId ->
                        intent.putExtra("stageId", stageId)
                        context.startActivity(intent)
                    },
                    StageManager.getStageList()
                )
            }
//            composable("Quests") {
//                QuestScreen()
//            }
        }
    }
}



// adb shell setprop debug.firebase.analytics.app com.teixeirarios.mad
//
//



