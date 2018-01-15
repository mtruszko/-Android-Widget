package com.example.maro.proj5and

import android.app.IntentService
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder


/**
 * Created by maro on 14.01.2018.
 */

var isServiceRunning = false


class BackgroundSoundService
    : IntentService("a"),
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        EventCallback {

    var player: MediaPlayer? = null
    val music = listOf(
            R.raw.nocturne,
            R.raw.outontheroad,
            R.raw.rimeyatches,
            R.raw.ring38470)
    var currentItem: Int = -1
    var isStopped: Boolean = false

    override fun onEventReceived(event: Event) {
        when (event) {
            Event.PLAY -> {
                if (player?.isPlaying == true) pause()
                else if(isStopped) {
                    init(music[currentItem])
                    play()
                } else play()
            }
            Event.NEXT -> next()
            Event.STOP -> stop()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        play()
        return START_STICKY
    }

    override fun onHandleIntent(intent: Intent?) {

    }

    override fun onPrepared(mp: MediaPlayer?) {
    }


    override fun onCompletion(mp: MediaPlayer?) {
        next()
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.register(this)
        init(getNextMusic())
        isServiceRunning = true
    }

    override fun onDestroy() {
        isServiceRunning = false
        EventBus.unregister(this)
        player?.stop()
        player?.release()
    }

    private fun init(resId: Int) {
        player?.stop()
        player?.release()
        isStopped = false
        player = MediaPlayer.create(this, resId)
    }

    private fun play() = player?.start()

    private fun pause() = player?.pause()

    private fun stop() {
        player?.stop()
        isStopped = true
    }

    private fun next() {
        init(getNextMusic())
        play()
    }

    private fun getNextMusic(): Int {
        if (currentItem == music.lastIndex) currentItem = 0
        else currentItem++
        return music[currentItem]
    }

    companion object {
        private val TAG: String? = null
    }
}