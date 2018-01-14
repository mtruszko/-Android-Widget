package com.example.maro.proj5and

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.media.MediaPlayer
import android.media.audiofx.BassBoost
import android.net.Uri
import android.provider.Settings


/**
 * Created by maro on 14.01.2018.
 */


class BackgroundSoundService : Service() ,MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    override fun onPrepared(mp: MediaPlayer?) {
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
    }

    override fun onCompletion(mp: MediaPlayer?) {
    }

    var player: MediaPlayer? = null
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.nocturne)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player!!.start()
        return START_STICKY
    }

     fun onStop() {
         player!!.stop()
     }

     fun onPause() {
        player!!.pause()
    }

    fun next() {

    }

    override fun onDestroy() {
        player!!.stop()
        player!!.release()
    }

    companion object {
        private val TAG: String? = null
    }
}