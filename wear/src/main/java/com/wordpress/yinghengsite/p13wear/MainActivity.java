package com.wordpress.yinghengsite.p13wear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView tvM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvM = (TextView) findViewById(R.id.text);

        // Always-on
        setAmbientEnabled();
    }
}

