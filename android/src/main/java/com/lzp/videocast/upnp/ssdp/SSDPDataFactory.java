package com.lzp.videocast.upnp.ssdp;

import com.lzp.videocast.upnp.UpnpConstants;
import com.lzp.videocast.upnp.network.MulticastsocketSearchRequestEntity;

import java.util.HashMap;
import java.util.Map;

public class SSDPDataFactory {
    private static final String TAG = SSDPDataFactory.class.getSimpleName();

    /**
     * 获取多播搜索信息实体
     */
    public static MulticastsocketSearchRequestEntity getMultisocketSearchRequestEntity(String st, Integer mx) {
        return new MulticastsocketSearchRequestEntity(st, mx);
    }

    public static MulticastsocketSearchRequestEntity getMultisocketSearchRequestEntity(String st) {
        return new MulticastsocketSearchRequestEntity(st, 5);
    }

    public static FilteredMulticastsocketResponse filterMulticastsocketReponse(String response, String type) {
        FilteredMulticastsocketResponse resp = new FilteredMulticastsocketResponse();
        resp.setMatched(false);
        String[] segs = response.split("\n");
        for (String seg : segs) {
            String[] ss = seg.split(":");
            String key = ss[0].trim();
            if (key.equalsIgnoreCase(UpnpConstants.SymNT) || key.equalsIgnoreCase(UpnpConstants.SymST)) {
                //响应中包含 ST 或 NT字段; 获取字段值
                int firstSepIndex = seg.indexOf(":");
                String value = seg.substring(firstSepIndex + 1).trim();
                if (value.equalsIgnoreCase(type)) {
                    resp.setMatched(true);
                }
            }
            if (key.equalsIgnoreCase(UpnpConstants.SymLocation)) {
                int firstSepIndex = seg.indexOf(":");
                String value = seg.substring(firstSepIndex + 1).trim();
                resp.setLocation(value);
            }
        }
        if(resp.isMatched() && resp.getLocation() == null) {
            resp.setMatched(false);
        }
        return resp;
    }

    public static class FilteredMulticastsocketResponse {
        private boolean matched;
        private String location;

        public boolean isMatched() {
            return matched;
        }

        public void setMatched(boolean matched) {
            this.matched = matched;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

}

/*
   Example 1:
    NOTIFY * HTTP/1.1
    Server: Linux/3.0.8+ UPnP/1.0 CyberLinkJava/1.8
    Cache-Control: max-age=300
    Location: http://192.168.0.104:39520/description.xml
    NTS: ssdp:alive
    NT: urn:schemas-upnp-org:service:ConnectionManager:1
    USN: uuid:fec59c28-465e-4f72-aae4-a7a244a819fe::urn:schemas-upnp-org:service:ConnectionManager:1
    HOST: 239.255.255.250:1900
    IP: 192.168.0.104

   Example 2:

    M-SEARCH * HTTP/1.1
    HOST: 239.255.255.250:1900
    ST: urn:schemas-upnp-org:device:InternetGatewayDevice:1
    MAN: "ssdp:discover"
    MX: 2
    IP: 192.168.0.154

   Example 3:

    M-SEARCH * HTTP/1.1
    MAN: "ssdp:discover"
    MX: 5
    HOST: 239.255.255.250:1900
    ST: urn:schemas-upnp-org:service:AVTransport:1
    IP: 192.168.0.150

 */