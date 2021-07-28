package com.lzp.videocast.upnp.ssdp;

public interface SSDPScanListener {

    void onNewMessage(String message, String host);
    void onScanTimeout();
    void onError(Error error);
}
