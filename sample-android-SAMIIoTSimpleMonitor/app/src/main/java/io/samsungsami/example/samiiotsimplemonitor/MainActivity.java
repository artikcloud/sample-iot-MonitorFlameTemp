/*
 * Copyright (C) 2015 Samsung Electronics Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.samsungsami.example.samiiotsimplemonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity  extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mLiveStatus;
    private TextView mDeviceName;
    private TextView mDeviceID;
    private TextView mDeviceStatus;
    private TextView mStatusUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLiveStatus = (TextView)findViewById(R.id.live_status);
        mDeviceName = (TextView)findViewById(R.id.device_name);
        mDeviceID = (TextView)findViewById(R.id.device_id);
        mDeviceStatus = (TextView)findViewById(R.id.device_status);
        mStatusUpdateTime = (TextView)findViewById(R.id.status_update_time);

        setTitle(R.string.device_monitor_title);

        SAMISession.getInstance().setContext(this);
        mDeviceID.setText("Device ID: " + SAMISession.getInstance().getDeviceID());
        mDeviceName.setText("Device Name: " + SAMISession.getInstance().getDeviceName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mWSUpdateReceiver,
                makeWebsocketUpdateIntentFilter());
        mLiveStatus.setText("Start connecting to Live");
        SAMISession.getInstance().connectLiveWebsocket();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SAMISession.getInstance().disconnectLiveWebsocket();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mWSUpdateReceiver);
    }


    private static IntentFilter makeWebsocketUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SAMISession.WEBSOCKET_LIVE_ONOPEN);
        intentFilter.addAction(SAMISession.WEBSOCKET_LIVE_ONMSG);
        intentFilter.addAction(SAMISession.WEBSOCKET_LIVE_ONCLOSE);
        intentFilter.addAction(SAMISession.WEBSOCKET_LIVE_ONERROR);
        return intentFilter;
    }

    private final BroadcastReceiver mWSUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (SAMISession.WEBSOCKET_LIVE_ONOPEN.equals(action)) {
                displayLiveStatus("Websocket live connected");
            } else if (SAMISession.WEBSOCKET_LIVE_ONMSG.equals(action)) {
                String status = intent.getStringExtra(SAMISession.DEVICE_DATA);
                String updateTime = intent.getStringExtra(SAMISession.TIMESTEP);
                displayDeviceStatus(status, updateTime);
            } else if (SAMISession.WEBSOCKET_LIVE_ONCLOSE.equals(action) ||
                    SAMISession.WEBSOCKET_LIVE_ONERROR.equals(action)) {
                displayLiveStatus(intent.getStringExtra("error"));
            }
        }
    };

    private void displayLiveStatus(String status) {
        Log.d(TAG, status);
        mLiveStatus.setText(status);
    }

    private void displayDeviceStatus(String status, String updateTimems) {
        mDeviceStatus.setText(status);
        long time_ms = Long.parseLong(updateTimems);
        mStatusUpdateTime.setText(DateFormat.getDateTimeInstance().format(new Date(time_ms)));
    }
}
