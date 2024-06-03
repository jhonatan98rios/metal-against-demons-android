import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoBackground() {
    val context = LocalContext.current
    var exoPlayer by remember { mutableStateOf<ExoPlayer?>(null) }

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
        modifier = Modifier.fillMaxHeight()
    )
}








