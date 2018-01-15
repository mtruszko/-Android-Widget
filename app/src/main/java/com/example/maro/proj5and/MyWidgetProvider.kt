package com.example.maro.proj5and

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import java.util.*


/**
 * Created by maro on 14.01.2018.
 */

var WIDGET_BUTTON_WWW = "com.example.maro.proj5and.WIDGET_BUTTON"
var WIDGET_BUTTON_NEXT = "com.example.maro.proj5and.WIDGET_BUTTON_NEXT"
var WIDGET_BUTTON_PLAY = "com.example.maro.proj5and.WIDGET_BUTTON_PLAY"
var WIDGET_BUTTON_STOP = "com.example.maro.proj5and.WIDGET_BUTTON_STOP"
var WIDGET_BUTTON_CHANGE_IMAGE = "com.example.maro.proj5and.WIDGET_BUTTON_CHANGE_IMAGE"

var INTENT_MSG = "com.example.maro.proj5and.INTENT_MSG"
val ID_EXTRA = "ID_EXTRA"

val imageMap = mutableMapOf<Int, Int>()

class MyWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {

        // Get all ids
        val thisWidget = ComponentName(context, MyWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
        for (widgetId in allWidgetIds) {
            // create some random data
            val number = Random().nextInt(100)
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)

            remoteViews.setTextViewText(R.id.update, "")

            Intent(context, MyWidgetProvider::class.java)
                    .apply { action = AppWidgetManager.ACTION_APPWIDGET_UPDATE }
                    .apply { putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds) }
                    .let { PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
                    .let { remoteViews.setOnClickPendingIntent(R.id.update, it) }

            Intent(context, MyWidgetProvider::class.java)
                    .apply { action = WIDGET_BUTTON_WWW }
                    .let { PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
                    .let { remoteViews.setOnClickPendingIntent(R.id.btn_www, it) }

//            Intent(context, BackgroundSoundService::class.java)
//                    .let { PendingIntent.getService(context, 0, it, 0) }
//                    .let { remoteViews.setOnClickPendingIntent(R.id.btn_play, it) }

            Intent(context, MyWidgetProvider::class.java)
                    .apply { action = WIDGET_BUTTON_PLAY }
                    .let { PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
                    .let { remoteViews.setOnClickPendingIntent(R.id.btn_play, it) }

            Intent(context, MyWidgetProvider::class.java)
                    .apply { action = WIDGET_BUTTON_NEXT }
                    .let { PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
                    .let { remoteViews.setOnClickPendingIntent(R.id.btn_next, it) }

            Intent(context, MyWidgetProvider::class.java)
                    .apply { action = WIDGET_BUTTON_STOP }
                    .let { PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
                    .let { remoteViews.setOnClickPendingIntent(R.id.btn_stop, it) }

            Intent(context, MyWidgetProvider::class.java)
                    .apply { action = WIDGET_BUTTON_CHANGE_IMAGE }
                    .apply { putExtra(ID_EXTRA, widgetId) }
                    .let { PendingIntent.getBroadcast(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT) }
                    .let { remoteViews.setOnClickPendingIntent(R.id.btn_changeImage, it) }

            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when (intent.action) {
            WIDGET_BUTTON_WWW -> {
                Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                        .apply { flags = FLAG_ACTIVITY_NEW_TASK }
                        .let { context.startActivity(it) }
            }
            WIDGET_BUTTON_PLAY -> {
                if(isServiceRunning) EventBus.send(Event.PLAY)
                else Intent(context, BackgroundSoundService::class.java).let { context.startService(it) }
            }
            WIDGET_BUTTON_NEXT -> {
                EventBus.send(Event.NEXT)
            }
            WIDGET_BUTTON_STOP -> {
                EventBus.send(Event.STOP)
            }
            WIDGET_BUTTON_CHANGE_IMAGE -> {
                val id = intent.getIntExtra(ID_EXTRA, -1)
                when (imageMap[id]) {
                    R.drawable.ic_tukan -> setImage(context, id, R.drawable.ic_weekend_black_24px)
                    R.drawable.ic_weekend_black_24px -> setImage(context, id, R.drawable.ic_tukan)
                    else -> setImage(context, id, R.drawable.ic_weekend_black_24px)
                }
            }
        }
    }

    private fun setImage(context: Context, widgetId: Int, imageRes: Int) {
        imageMap.put(widgetId, imageRes)
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)
        remoteViews.setImageViewResource(R.id.iv_Widget, imageRes)
        pushWidgetUpdate(context.applicationContext, remoteViews)
    }
}

fun pushWidgetUpdate(context: Context, remoteViews: RemoteViews) {
    val myWidget = ComponentName(context,
            MyWidgetProvider::class.java)
    val manager = AppWidgetManager.getInstance(context)
    manager.updateAppWidget(myWidget, remoteViews)
}