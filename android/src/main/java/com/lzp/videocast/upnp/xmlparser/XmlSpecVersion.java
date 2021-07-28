package com.lzp.videocast.upnp.xmlparser;

public class XmlSpecVersion {
    public static final String name = XmlCons.specVersion;

    private String major;
    private String minor;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }
}
