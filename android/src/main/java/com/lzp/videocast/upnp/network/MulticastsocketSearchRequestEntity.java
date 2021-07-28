package com.lzp.videocast.upnp.network;

import com.lzp.videocast.upnp.UpnpConstants;

public class MulticastsocketSearchRequestEntity {

    private String st = "ssdp:all";
    private String entity = "";

    /**
     * @param st , UpnpConstants => Type: TypeDeviceKind,TypeServerKind,TypeAllKind
     * @param mx , number , 0 -> (n+)
     */
    public MulticastsocketSearchRequestEntity(String st, Integer mx) {
        StringBuilder content = new StringBuilder();
        content.append(UpnpConstants.MulticastSocketRequestHeader).append(UpnpConstants.SymNewLine);
        content.append(UpnpConstants.SymMAN).append(UpnpConstants.SymSepa).append(UpnpConstants.SymQuote).append(UpnpConstants.MAN).append(UpnpConstants.SymQuote).append(UpnpConstants.SymNewLine);
        content.append(UpnpConstants.SymMX).append(UpnpConstants.SymSepa).append(mx).append(UpnpConstants.SymNewLine);
        content.append(UpnpConstants.SymHOST).append(UpnpConstants.SymSepa).append(UpnpConstants.MulticastSocketHost).append(":").append(UpnpConstants.MulticastSocketPort).append(UpnpConstants.SymNewLine);
        content.append(UpnpConstants.SymST).append(UpnpConstants.SymSepa).append(st);
        this.entity = content.toString();
    }
    /**
     * @return
     *  M-SEARCH * HTTP/1.1
     *  MAN: "ssdp:discover"
     *  MX: 5
     *  HOST: 239.255.255.250:1900
     *  ST: upnp:rootdevice
     */
    public String getEntity() {
        return this.entity;
    }
}
