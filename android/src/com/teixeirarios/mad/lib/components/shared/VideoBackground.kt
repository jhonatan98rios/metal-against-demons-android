import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import kotlin.math.roundToInt

@OptIn(UnstableApi::class)
@Composable
fun VideoBackground(scale: Float, offsetX: Float, offsetY: Float) {
    val context = LocalContext.current
    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }

    // Using updateTransition to animate all properties together
    val transition = updateTransition(targetState = Triple(scale, offsetX, offsetY), label = "VideoBackgroundTransition")

    val animatedScale by transition.animateFloat(
        label = "ScaleAnimation",
        transitionSpec = { tween(durationMillis = 1000, easing = FastOutSlowInEasing) }
    ) { it.first }

    val animatedOffsetX by transition.animateFloat(
        label = "OffsetXAnimation",
        transitionSpec = { tween(durationMillis = 1000, easing = FastOutSlowInEasing) }
    ) { it.second }

    val animatedOffsetY by transition.animateFloat(
        label = "OffsetYAnimation",
        transitionSpec = { tween(durationMillis = 1000, easing = FastOutSlowInEasing) }
    ) { it.third }


    DisposableEffect(Unit) {
        val player = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(
                Uri.parse("android.resource://${context.packageName}/raw/background_video_minified")
            )
            setMediaItem(mediaItem)
            playWhenReady = true
            repeatMode = ExoPlayer.REPEAT_MODE_ONE
            prepare()
        }
        exoPlayer = player

        onDispose {
            player.release()
            exoPlayer = null
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false // Hides the controls
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                )
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Crop the video to fill the height
            }
        },
        update = { playerView ->
            playerView.player = exoPlayer
        },
        modifier = Modifier
            .fillMaxHeight()
            .scale(animatedScale)
            .offset {
                IntOffset(animatedOffsetX.roundToInt(), animatedOffsetY.roundToInt())
            }
    )
}








