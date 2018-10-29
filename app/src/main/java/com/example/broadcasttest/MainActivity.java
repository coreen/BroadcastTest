package com.example.broadcasttest;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// Resource: https://developer.android.com/guide/components/broadcasts#java
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String BROADCAST_ACTION = "com.example.broadcasttest.SONG_INFO";

    private Button mButton;

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
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
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
