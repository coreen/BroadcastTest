package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.broadcasttest.Widget.WIDGET_BROADCAST_PLAY_ACTION;
import static com.example.broadcasttest.Widget.WIDGET_BROADCAST_SKIP_ACTION;

// Resource: https://developer.android.com/guide/components/broadcasts#java
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String ACTIVITY_BROADCAST_ACTION = "com.example.broadcasttest.SONG_INFO";

    private Button mButton;
    private BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click detected, sending message");
                sendMessage();
            }
        });

        // Receiving widget broadcast
        // Resource: https://stackoverflow.com/questions/14397336/how-to-register-a-receiver-in-the-main-activity
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(intent.getAction()) {
                    case WIDGET_BROADCAST_PLAY_ACTION:
                        Log.d(TAG, "widget play broadcast received");
                        break;
                    case WIDGET_BROADCAST_SKIP_ACTION:
                        Log.d(TAG, "widget skip broadcast received");
                }
//                Log.d(TAG, "widget intent.getAction() = " + intent.getAction());
//                Log.d(TAG, "widget intent extras: " + intent.getExtras()); // Parceled Data, doesn't show contents in toString
//                Log.d(TAG, "widget intent extra string: " + intent.getStringExtra("widgetAction"));
            }
        };
        // Resource: https://stackoverflow.com/questions/9128103/broadcastreceiver-with-multiple-filters-or-multiple-broadcastreceivers
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WIDGET_BROADCAST_PLAY_ACTION);
        intentFilter.addAction(WIDGET_BROADCAST_SKIP_ACTION);
        this.registerReceiver(mMessageReceiver, intentFilter);
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(ACTIVITY_BROADCAST_ACTION);
        intent.putExtra("songTitle", "All I want for Christmas is you");
        intent.putExtra("songArtist", "Mariah Carey");
        /*
         * Cannot use implicit broadcast in Android O and newer
         * Resource: https://commonsware.com/blog/2017/04/11/android-o-implicit-broadcast-ban.html
         */
//                sendBroadcast(intent);
        // Since only sending broadcast within same app
        // Resource: https://stackoverflow.com/questions/8802157/how-to-use-localbroadcastmanager
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
