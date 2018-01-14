package com.example.maro.proj5and

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract.Helpers.update
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.widget.RemoteViews
import android.content.ComponentName
import android.appwidget.AppWidgetProvider
import android.util.Log
import java.util.*


/**
 * Created by maro on 14.01.2018.
 */

class MyWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {

        // Get all ids
        val thisWidget = ComponentName(context,
                MyWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
        for (widgetId in allWidgetIds) {
            // create some random data
            val number = Random().nextInt(100)

            val remoteViews = RemoteViews(context.packageName,
                    R.layout.widget_layout)
            Log.w("WidgetExample", number.toString())
            // Set the text
            remoteViews.setTextViewText(R.id.update, number.toString())



            // Register an onClickListener
            val intent = Intent(context, MyWidgetProvider::class.java)

            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

            val pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent)
            appWidgetManager.updateAppWidget(widgetId, remoteViews)
        }
    }

    companion object {

        private val ACTION_CLICK = "ACTION_CLICK"
    }
}