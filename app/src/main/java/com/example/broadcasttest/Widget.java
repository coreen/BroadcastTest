package com.example.broadcasttest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import static com.example.broadcasttest.MainActivity.ACTIVITY_BROADCAST_ACTION;

public class Widget extends AppWidgetProvider {
    private static final String TAG = Widget.class.getSimpleName();

    public static final String WIDGET_BROADCAST_PLAY_ACTION = "com.example.broadcasttest.WIDGET_PLAY";
    public static final String WIDGET_BROADCAST_SKIP_ACTION = "com.example.broadcasttest.WIDGET_SKIP";

    private BroadcastReceiver mMessageReceiver;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient);

        // Widget => Activity broadcast
        // Using regular broadcast, since LocalBroadcastManager doesn't work with PendingIntent
        // Resource: https://stackoverflow.com/questions/15790734/can-you-use-pending-intents-with-localbroadcasts
        Intent intent1 = new Intent();
        intent1.setAction(WIDGET_BROADCAST_PLAY_ACTION);
//        intent1.putExtra("widgetAction", "play");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_play_btn, pendingIntent1);

        Intent intent2 = new Intent();
        intent2.setAction(WIDGET_BROADCAST_SKIP_ACTION);
//        intent2.putExtra("widgetAction", "skip");
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_skip_btn, pendingIntent2);

        // Activity => Widget broadcast
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
                .registerReceiver(mMessageReceiver, new IntentFilter(ACTIVITY_BROADCAST_ACTION));
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // TODO => Resource: https://stackoverflow.com/questions/10921451/start-activity-using-custom-action
//    }
}