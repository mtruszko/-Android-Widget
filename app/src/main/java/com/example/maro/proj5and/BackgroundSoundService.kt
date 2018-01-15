package com.example.maro.proj5and

import android.app.IntentService
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder


/**
 * Created by maro on 14.01.2018.
 */


class BackgroundSoundService
    : IntentService("a"),
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        EventCallback {

    override fun onEventReceived(event: Event) {
        when(event) {
            Event.NEXT -> player!!.pause()
        }
    }

    override fun onHandleIntent(intent: Intent?) {

    }

    override fun onPrepared(mp: MediaPlayer?) {
    }


    override fun onCompletion(mp: MediaPlayer?) {
    }


    var player: MediaPlayer? = null
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.register(this)
        player = MediaPlayer.create(this, R.raw.nocturne)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player!!.start()
        return START_STICKY
    }

    override fun onDestroy() {
        EventBus.unregister(this)
        player!!.stop()
        player!!.release()
    }

    companion object {
        private val TAG: String? = null
    }
}