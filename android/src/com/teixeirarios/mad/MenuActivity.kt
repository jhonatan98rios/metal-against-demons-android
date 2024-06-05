package com.teixeirarios.mad

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import VideoBackground
import androidx.compose.foundation.layout.Arrangement
import com.teixeirarios.mad.lib.components.home.StageManager
import com.teixeirarios.mad.lib.components.home.StageModel
import com.teixeirarios.mad.lib.components.home.StageSelector


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreen()

        val stageList = StageManager.getStageList()

        setContent {
            MenuScreen(
                onButtonClick = { stageId ->
                    val intent = Intent(this, AndroidLauncher::class.java)
                    intent.putExtra("stageId", stageId)
                    startActivity(intent)
                    finish()
                },
                stageList
            )
        }
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
fun MenuScreen(onButtonClick: (Int) -> Unit,  stageList: List<StageModel>) {

    Box(modifier = Modifier.fillMaxSize()) {
        VideoBackground()

        // Foreground content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Metal Against Demons",
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))

            StageSelector(stageList, onButtonClick)
        }
    }
}



