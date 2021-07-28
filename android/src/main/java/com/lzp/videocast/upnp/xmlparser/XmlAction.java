package com.lzp.videocast.upnp.xmlparser;

import java.util.ArrayList;

public class XmlAction {
    private String name;
    private ArrayList<XmlActionArgument> argumentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<XmlActionArgument> getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(ArrayList<XmlActionArgument> argumentList) {
        this.argumentList = argumentList;
    }
}
