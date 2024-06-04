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

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import VideoBackground
import com.teixeirarios.mad.lib.domain.entities.stage.StageManager
import com.teixeirarios.mad.lib.domain.entities.stage.StageModel


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreen()

        val stageManager = StageManager.getInstance()
        //val stageList = stageManager.allStagesData.values.toList()

        val stageData = stageManager.getStageData("1")

        setContent {
            MenuScreen(
                onButtonClick = {
                    val intent = Intent(this, AndroidLauncher::class.java)
                    intent.putExtra("stageId", stageData.id)
                    startActivity(intent)
                    finish()
                },
                stageData.id
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
fun MenuScreen(onButtonClick: () -> Unit, stageId: String) {

    Box(modifier = Modifier.fillMaxSize()) {
        VideoBackground()

        // Foreground content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Metal Against Demons logo",
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "Stage $stageId",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
            Button(onClick = { onButtonClick() }) {
                Text(text = "Start")
            }
        }
    }
}



