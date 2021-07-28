package com.lzp.videocast.upnp.soap;


public class SOAPCenter {

    private static final String TAG = "【SOAPCenter】";
    private static SOAPCenter instance;
    public static SOAPCenter getInstance(){
        if (instance == null) {
            instance = new SOAPCenter();
            return instance;
        }
        return instance;
    }

    public void setPlayURL(){

    }
    public void play(){

    }
    public void pause(){

    }
}
