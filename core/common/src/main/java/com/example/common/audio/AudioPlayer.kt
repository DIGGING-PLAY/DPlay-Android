package com.example.common.audio

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioPlayer
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private var controllerFuture: ListenableFuture<MediaController>? = null
        private var controller: MediaController? = null

        private val _playbackState = MutableStateFlow(PlaybackState())
        val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

        private val playerListener =
            object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    updatePlaybackState()
                    if (state == Player.STATE_ENDED) {
                        stop()
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    updatePlaybackState()
                }
            }

        private fun initializeController() {
            if (controllerFuture != null) return

            val sessionToken =
                SessionToken(context, ComponentName(context, PlaybackService::class.java))
            controllerFuture =
                MediaController.Builder(context, sessionToken).buildAsync().also { future ->
                    future.addListener(
                        {
                            controller = future.get()
                            controller?.addListener(playerListener)
                        },
                        MoreExecutors.directExecutor(),
                    )
                }
        }

        fun play(
            url: String,
            trackId: String,
            title: String = "",
            artist: String = "",
        ) {
            initializeController()

            val currentController = controller
            if (currentController == null) {
                controllerFuture?.addListener(
                    {
                        playInternal(url, trackId, title, artist)
                    },
                    MoreExecutors.directExecutor(),
                )
                return
            }

            playInternal(url, trackId, title, artist)
        }

        private fun playInternal(
            url: String,
            trackId: String,
            title: String,
            artist: String,
        ) {
            val currentController = controller ?: return

            if (_playbackState.value.currentTrackId == trackId && _playbackState.value.isPlaying) {
                stop()
                return
            }

            currentController.stop()
            currentController.clearMediaItems()

            val mediaMetadata =
                MediaMetadata
                    .Builder()
                    .setTitle(title.ifEmpty { "DPlay" })
                    .setArtist(artist.ifEmpty { "미리듣기" })
                    .build()

            val mediaItem =
                MediaItem
                    .Builder()
                    .setUri(url)
                    .setMediaId(trackId)
                    .setMediaMetadata(mediaMetadata)
                    .build()

            currentController.setMediaItem(mediaItem)
            currentController.prepare()
            currentController.play()

            _playbackState.value =
                PlaybackState(
                    isPlaying = true,
                    currentTrackId = trackId,
                )
        }

        fun pause() {
            controller?.pause()
            _playbackState.value = _playbackState.value.copy(isPlaying = false)
        }

        fun stop() {
            controller?.stop()
            controller?.clearMediaItems()
            _playbackState.value = PlaybackState()
        }

        fun release() {
            controller?.removeListener(playerListener)
            controllerFuture?.let { MediaController.releaseFuture(it) }
            controllerFuture = null
            controller = null
            _playbackState.value = PlaybackState()
        }

        private fun updatePlaybackState() {
            val currentController = controller ?: return
            _playbackState.value =
                _playbackState.value.copy(
                    isPlaying = currentController.isPlaying,
                )
        }
    }

data class PlaybackState(
    val isPlaying: Boolean = false,
    val currentTrackId: String? = null,
)
