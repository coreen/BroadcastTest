package com.example.broadcasttest;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import static com.example.broadcasttest.MainActivity.BROADCAST_ACTION;

public class Widget extends AppWidgetProvider {
    private static final String TAG = Widget.class.getSimpleName();

    private BroadcastReceiver mMessageReceiver;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient);

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "intent.getAction() = " + intent.getAction());
                Log.d(TAG, "intent extras: " + intent.getExtras());
                views.setCharSequence(R.id.tv_title, "setText", intent.getStringExtra("songTitle"));
                views.setCharSequence(R.id.tv_artist, "setText", intent.getStringExtra("songArtist"));

                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        };
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(mMessageReceiver, new IntentFilter(BROADCAST_ACTION));
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // TODO => Resource: https://stackoverflow.com/questions/10921451/start-activity-using-custom-action
//    }
}