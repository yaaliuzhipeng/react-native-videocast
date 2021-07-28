package com.lzp.videocast.upnp.xmlparser;

public class XmlDeviceService {

    public static final String name = XmlCons.service;

    private String serviceType;
    private String serviceId;
    private String SCPDURL;
    private String controlURL;
    private String eventSubURL;

    public void setControlURL(String controlURL) {
        this.controlURL = controlURL;
    }

    public void setEventSubURL(String eventSubURL) {
        this.eventSubURL = eventSubURL;
    }

    public void setSCPDURL(String SCPDURL) {
        this.SCPDURL = SCPDURL;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getSCPDURL() {
        return SCPDURL;
    }

    public String getControlURL() {
        return controlURL;
    }

    public String getEventSubURL() {
        return eventSubURL;
    }
}
