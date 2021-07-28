package com.lzp.videocast.upnp.ssdp;

import com.lzp.videocast.upnp.UpnpConstants;
import com.lzp.videocast.upnp.network.MulticastsocketSearchRequestEntity;

import java.util.HashMap;
import java.util.Map;

public class SSDPDataFactory {
    private static final String TAG = SSDPDataFactory.class.getSimpleName();
    /**
     *  获取多播搜索信息实体
     */
    public static MulticastsocketSearchRequestEntity getMultisocketSearchRequestEntity(String st, Integer mx) {
        return new MulticastsocketSearchRequestEntity(st,mx);
    }
    public static MulticastsocketSearchRequestEntity getMultisocketSearchRequestEntity(String st) {
        return new MulticastsocketSearchRequestEntity(st,5);
    }

    /**
     * @Description 判断并解析 AVTransport搜索类型的响应数据
     * 目标是 Server , Location , USN
     */
    public static Map<String, String> parseMulticastsocketReponse(String response) {
        Map<String,String> resp = new HashMap<>();

        String[] segs = response.split("\n");
        for (int i = 0; i < segs.length; i++){
            String[] ss = segs[i].split(":");
//            Log.i(TAG, "当前遍历seg => "+i+"  分割后首个元素是: "+ss[0]);
            if(ss[0].trim().equalsIgnoreCase(UpnpConstants.MulticastSocketHeaderLOCATION)){
                int firstSepIndex = segs[i].indexOf(":");
                String location = segs[i].substring(firstSepIndex+1).trim();
                resp.put(UpnpConstants.MulticastSocketHeaderLOCATION,location);
                break;
            }
        }
        return resp;
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