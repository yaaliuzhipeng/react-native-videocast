package com.lzp.videocast.upnp.xmlparser;

public class XmlActionArgument {

    private String name;
    private String direction;
    private String relatedStateVariable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRelatedStateVariable() {
        return relatedStateVariable;
    }

    public void setRelatedStateVariable(String relatedStateVariable) {
        this.relatedStateVariable = relatedStateVariable;
    }
}
