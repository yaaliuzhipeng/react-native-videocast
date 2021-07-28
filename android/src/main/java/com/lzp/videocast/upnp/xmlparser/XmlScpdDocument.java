package com.lzp.videocast.upnp.xmlparser;

import java.util.ArrayList;

public class XmlScpdDocument {

    private XmlSpecVersion specVersion;
    private ArrayList<XmlAction> actionList;
    private ArrayList<XmlStateVariable> serviceStateTable;

    public XmlSpecVersion getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(XmlSpecVersion specVersion) {
        this.specVersion = specVersion;
    }

    public ArrayList<XmlAction> getActionList() {
        return actionList;
    }

    public void setActionList(ArrayList<XmlAction> actionList) {
        this.actionList = actionList;
    }

    public ArrayList<XmlStateVariable> getServiceStateTable() {
        return serviceStateTable;
    }

    public void setServiceStateTable(ArrayList<XmlStateVariable> serviceStateTable) {
        this.serviceStateTable = serviceStateTable;
    }
}
