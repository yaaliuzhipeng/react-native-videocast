package com.lzp.videocast.upnp.xmlparser;

import java.util.ArrayList;

public class XmlDevice {

    public static final String name = XmlCons.device;

    private String deviceType;
    private String friendlyName;
    private String manufacturer;
    private String manufacturerURL;
    private String modelDescription;
    private String modelName;
    private String modelNumber;
    private String modelURL;
    private String serialNumber;
    private String UDN;
    private ArrayList<XmlDeviceService> serviceList;

    public XmlDevice(){}

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setManufacturerURL(String manufacturerURL) {
        this.manufacturerURL = manufacturerURL;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setModelURL(String modelURL) {
        this.modelURL = modelURL;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setUDN(String UDN) {
        this.UDN = UDN;
    }

    public void setServiceList(ArrayList<XmlDeviceService> serviceList) {
        this.serviceList = serviceList;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getManufacturerURL() {
        return manufacturerURL;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getModelURL() {
        return modelURL;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getUDN() {
        return UDN;
    }

    public ArrayList<XmlDeviceService> getServiceList() {
        return serviceList;
    }
}
