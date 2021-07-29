package com.lzp.videocast.upnp.ssdp;

import com.lzp.videocast.upnp.xmlparser.XmlDeviceDescriptionDocument;

public class SSDPDeviceResponse {

    private String uuid;
    private String location;
    private XmlDeviceDescriptionDocument descriptionDocument;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public XmlDeviceDescriptionDocument getDescriptionDocument() {
        return descriptionDocument;
    }

    public void setDescriptionDocument(XmlDeviceDescriptionDocument descriptionDocument) {
        this.descriptionDocument = descriptionDocument;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
