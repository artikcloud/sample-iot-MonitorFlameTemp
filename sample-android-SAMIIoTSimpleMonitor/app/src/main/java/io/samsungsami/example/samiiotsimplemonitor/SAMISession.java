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

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

public class SAMISession {
    private static final String TAG = SAMISession.class.getSimpleName();
    private final static String DEVICE_ID = "45176de99e424d98b1a3c42558bfccf4";
    private final static String DEVICE_TOKEN = "1a201a1446fa48bead5ec6a84d417633";
    private final static String DEVICE_NAME = "Combined Flame Detection and Temperature Sensor";

    private static SAMISession ourInstance = new SAMISession();
    private static Context ourContext;

    public final static String WEBSOCKET_LIVE_ONOPEN =
            "io.samsungsami.example.samiiotmonitor.WEBSOCKET_LIVE_ONOPEN";
    public final static String WEBSOCKET_LIVE_ONMSG =
            "io.samsungsami.example.samiiotmonitor.WEBSOCKET_LIVE_ONMSG";
    public final static String WEBSOCKET_LIVE_ONCLOSE =
            "io.samsungsami.example.samiiotmonitor.WEBSOCKET_LIVE_ONCLOSE";
    public final static String WEBSOCKET_LIVE_ONERROR =
            "io.samsungsami.example.samiiotmonitor.WEBSOCKET_LIVE_ONERROR";
    public final static String SDID = "sdid";
    public final static String DEVICE_DATA = "data";
    public final static String TIMESTEP = "ts";


    private Websocket mLive = null;

    public static SAMISession getInstance() {
        return ourInstance;
    }

    private SAMISession() {
        // Do nothing
    }

    public void setContext(Context context) {ourContext = context;}

    public String getDeviceID() {return DEVICE_ID;}
    public String getDeviceName() {return DEVICE_NAME;}

    public void connectLiveWebsocket() {
        if (mLive == null) {
            mLive = new Websocket();
        }

        if(!mLive.isConnected() && !mLive.isConnecting()) {
            String liveUrl = getLiveUrl();
            if (liveUrl == null || liveUrl.length() <= 0) {
                Log.w(TAG, "Cannot to connect to live websocket with empty URL");
                return;
            }
            mLive.connect(liveUrl, new WebsocketEvents() {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d(TAG, "connectLiveWebsocket: onOpen()");
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONOPEN);
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }

                @Override
                public void onMessage(String message) {
                    Log.d(TAG, "connectLiveWebsocket: onMessage(" + message + ")");
                    try {
                        JSONObject json = new JSONObject(message);
                        JSONObject dataNode = json.optJSONObject("data");
                        JSONObject errorNode = json.optJSONObject("error");
                        if (dataNode != null) {
                            final Intent intent = new Intent(WEBSOCKET_LIVE_ONMSG);
                            intent.putExtra(SDID, json.optString("sdid"));
                            intent.putExtra(DEVICE_DATA, dataNode.toString());
                            intent.putExtra(TIMESTEP,json.optString("ts"));
                            LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                            Log.d(TAG, "data: " + dataNode.toString());
                        } else if(errorNode != null) {
                            Log.w(TAG, "error on Message: " + errorNode.toString());
                        }
                    } catch (JSONException e) {
                        // This message doesn't contain data node, might be a ping.
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONCLOSE);
                    intent.putExtra("error", "mLive is closed. code: " + code + "; reason: " + reason);
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }

                @Override
                public void onError(Exception ex) {
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONERROR);
                    intent.putExtra("error", "mLive error: " + ex.getMessage());
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }
            });
        }
    }
    /**
     * Closes a websocket connection
     */
    public void disconnectLiveWebsocket(){
        if (mLive != null && mLive.isConnected()){
            mLive.disconnect();
            mLive = null;
        }
    }

    private String getLiveUrl() {
        String LIVE_BASE_URL = "wss://api.samsungsami.io/v1.1/live?sdid=";
        String liveUrl = LIVE_BASE_URL + DEVICE_ID + "&Authorization=bearer+" + DEVICE_TOKEN;
        Log.d(TAG, liveUrl);
        return liveUrl;
    }



}
