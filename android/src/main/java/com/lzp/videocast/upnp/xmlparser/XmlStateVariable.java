package com.lzp.videocast.upnp.xmlparser;

import java.util.ArrayList;
import java.util.Map;

public class XmlStateVariable {
    private Map<String,String> attributes;
    private String name;
    private String dataType;
    private ArrayList<XmlAllowedValue> allowedValueList;
    private XmlAllowedValueRange allowedValueRange;
    private String defaultValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public ArrayList<XmlAllowedValue> getAllowedValueList() {
        return allowedValueList;
    }

    public void setAllowedValueList(ArrayList<XmlAllowedValue> allowedValueList) {
        this.allowedValueList = allowedValueList;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public XmlAllowedValueRange getAllowedValueRange() {
        return allowedValueRange;
    }

    public void setAllowedValueRange(XmlAllowedValueRange allowedValueRange) {
        this.allowedValueRange = allowedValueRange;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
