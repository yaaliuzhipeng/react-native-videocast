package com.example;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.lzp.videocast.upnp.UpnpConstants;
import com.lzp.videocast.upnp.ssdp.SSDPCenter;
import com.lzp.videocast.upnp.ssdp.SSDPDataFactory;
import com.lzp.videocast.upnp.ssdp.SSDPScanListener;

public class MainActivity extends ReactActivity {

    Context context;
    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "example";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = this.getApplicationContext();
        super.onCreate(savedInstanceState);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("TAG", "scan: 开始扫描");
//                SSDPCenter
//                        .getInstance()
//                        .initialize(context, 3600)
//                        .scan(UpnpConstants.TypeServerKind.UpnpAVTransport1, new SSDPScanListener() {
//                            @Override
//                            public void onNewMessage(String message, String host) {
//                                boolean isMatched = SSDPDataFactory.filterMulticastsocketReponse(message,UpnpConstants.TypeServerKind.UpnpAVTransport1);
//                                if(isMatched){
//                                    Log.i("TAG", "onNewMessage: \n"+message+"\n\n");
//                                }
//                            }
//                            @Override
//                            public void onScanTimeout() {}
//                            @Override
//                            public void onError(Error error) {
//
//                            }
//                        });
//            }
//        }).start();

    }
}
