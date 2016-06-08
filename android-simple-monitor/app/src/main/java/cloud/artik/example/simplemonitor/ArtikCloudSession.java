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

package cloud.artik.example.simplemonitor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.net.URISyntaxException;

import cloud.artik.model.Acknowledgement;
import cloud.artik.model.ActionOut;
import cloud.artik.model.MessageOut;
import cloud.artik.model.WebSocketError;
import cloud.artik.websocket.ArtikCloudWebSocketCallback;
import cloud.artik.websocket.FirehoseWebSocket;

public class ArtikCloudSession {
    private static final String TAG = ArtikCloudSession.class.getSimpleName();
    private final static String DEVICE_ID = "<YOUR DEVICE ID>";
    private final static String DEVICE_TOKEN = "<YOUR DEVICE TOKEN>";
    private final static String DEVICE_NAME = "Combined Flame Detection and Temperature Sensor";

    private static ArtikCloudSession ourInstance = new ArtikCloudSession();
    private static Context ourContext;

    public final static String WEBSOCKET_LIVE_ONOPEN =
            "cloud.artik.example.iot.WEBSOCKET_LIVE_ONOPEN";
    public final static String WEBSOCKET_LIVE_ONMSG =
            "cloud.artik.example.iot.WEBSOCKET_LIVE_ONMSG";
    public final static String WEBSOCKET_LIVE_ONCLOSE =
            "cloud.artik.example.iot.WEBSOCKET_LIVE_ONCLOSE";
    public final static String WEBSOCKET_LIVE_ONERROR =
            "cloud.artik.example.iot.WEBSOCKET_LIVE_ONERROR";
    public final static String SDID = "sdid";
    public final static String DEVICE_DATA = "data";
    public final static String TIMESTEP = "ts";


    private FirehoseWebSocket mFirehoseWS = null; //  end point: /live

    public static ArtikCloudSession getInstance() {
        return ourInstance;
    }

    private ArtikCloudSession() {
        // Do nothing
    }

    public void setContext(Context context) {ourContext = context;}

    public String getDeviceID() {return DEVICE_ID;}
    public String getDeviceName() {return DEVICE_NAME;}

    public void connectFirehoseWS() {
        createFirehoseWebsocket();
        try {
            mFirehoseWS.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes a websocket /live connection
     */
    public void disconnectFirehoseWS() {
        if (mFirehoseWS != null) {
            try {
                mFirehoseWS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mFirehoseWS = null;
    }

    private void createFirehoseWebsocket() {
        try {
            mFirehoseWS = new FirehoseWebSocket(DEVICE_TOKEN, DEVICE_ID, null, null, null, new ArtikCloudWebSocketCallback() {
                @Override
                public void onOpen(int i, String s) {
                    Log.d(TAG, "FirehoseWebSocket: onOpen()");
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONOPEN);
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }

                @Override
                public void onMessage(MessageOut messageOut) {
                    Log.d(TAG, "FirehoseWebSocket: onMessage(" + messageOut.toString() + ")");
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONMSG);
                    intent.putExtra(SDID, messageOut.getSdid());
                    intent.putExtra(DEVICE_DATA, messageOut.getData().toString());
                    intent.putExtra(TIMESTEP, messageOut.getTs().toString());
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }

                @Override
                public void onAction(ActionOut actionOut) {
                }

                @Override
                public void onAck(Acknowledgement acknowledgement) {
                    Log.d(TAG, "FirehoseWebSocket::onAck: ");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONCLOSE);
                    intent.putExtra("error", "mFirehoseWS is closed. code: " + code + "; reason: " + reason);
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }

                @Override
                public void onError(WebSocketError ex) {
                    final Intent intent = new Intent(WEBSOCKET_LIVE_ONERROR);
                    intent.putExtra("error", "mFirehoseWS error: " + ex.getMessage());
                    LocalBroadcastManager.getInstance(ourContext).sendBroadcast(intent);
                }

                @Override
                public void onPing(long timestamp) {
                    Log.d(TAG, "FirehoseWebSocket::onPing: " + timestamp);
                }
            });
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
