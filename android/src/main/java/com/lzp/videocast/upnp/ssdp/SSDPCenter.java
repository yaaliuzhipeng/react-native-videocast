package com.lzp.videocast.upnp.ssdp;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.lzp.videocast.upnp.UpnpConstants;
import com.lzp.videocast.upnp.network.AMulticastSocket;
import com.lzp.videocast.upnp.network.MulticastsocketSearchRequestEntity;

import java.io.IOError;
import java.io.IOException;
import java.net.DatagramPacket;

public class SSDPCenter {
    private static final String TAG = "【SSDPCenter】";
    private static SSDPCenter instance;
    public static SSDPCenter getInstance(){
        if (instance == null) {
            instance = new SSDPCenter();
            return instance;
        }
        return instance;
    }

    //Instance Properties Defined here
    private static final String MulticastLockTag = "multicastlock";
    private Context context;
    private WifiManager.MulticastLock multicastLock;
    private static boolean scanStopFlag = false;
    private static boolean scanTimeoutFlag = false;
    private int scanTimeoutValue = 15;
    private Thread scanTimeoutThread = null;

    public SSDPCenter (){}

    public void initialize(Context ctx){
        this.context = ctx;
    }
    public void initialize(Context ctx,int scanTimeout){
        this.context = ctx;
        scanTimeoutValue = scanTimeout;
    }

    private void getMultiLock(){
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        multicastLock = wm.createMulticastLock(MulticastLockTag);
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();
    }
    private void releaseMultiLock(){
        if(multicastLock != null){
            multicastLock.release();
            multicastLock = null;
        }
    }
    private void resetFlag(){
        scanStopFlag = false;
        scanTimeoutFlag = false;
    }

    public void scan(String type,SSDPScanListener listener) {
        if(context == null){
            return;
        }
        resetFlag();
        getMultiLock();
        //构造多播请求信息
        MulticastsocketSearchRequestEntity requestEntity = SSDPDataFactory.getMultisocketSearchRequestEntity(type,5);
        //创建Socket，发送请求信息
        AMulticastSocket multicastSocket = null;
        try {
            multicastSocket = new AMulticastSocket(UpnpConstants.MulticastSocketHost,UpnpConstants.MulticastSocketPort);
            multicastSocket.send(requestEntity.getEntity());
            scanTimeoutThread = new Thread(new Runnable() {  //执行扫描超时方法
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000*scanTimeoutValue);
                        scanTimeoutFlag = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            scanTimeoutThread.start();
            while (!scanStopFlag) {  //中断标志为TRUE时停止循环
                if(scanTimeoutFlag){
                    listener.onScanTimeout();
                    break;
                }
                DatagramPacket dp = multicastSocket.receive();
                String resp = new String(dp.getData()).trim();
                String fromhost = dp.getAddress().getHostAddress();
                listener.onNewMessage(resp,fromhost);
            }
            multicastSocket.close();
            releaseMultiLock();
            resetFlag();
            if(scanTimeoutThread != null){
                scanTimeoutThread.interrupt();
                scanTimeoutThread = null;
            }
        } catch (IOException e) {
            listener.onError(new IOError(e.getCause()));
        }
    }
    public void stopScan(){
        scanStopFlag = true;
        releaseMultiLock();
    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(!scanStopFlag){
            scanStopFlag = true;
        }
    }

}

