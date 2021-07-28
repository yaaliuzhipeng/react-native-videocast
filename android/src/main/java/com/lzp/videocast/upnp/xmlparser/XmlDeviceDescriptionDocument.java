package com.lzp.videocast.upnp.xmlparser;

public class XmlDeviceDescriptionDocument {

    private XmlDevice device;
    private XmlSpecVersion specVersion;

    public XmlDevice getDevice() {
        return device;
    }

    public void setDevice(XmlDevice device) {
        this.device = device;
    }

    public XmlSpecVersion getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(XmlSpecVersion specVersion) {
        this.specVersion = specVersion;
    }
}
