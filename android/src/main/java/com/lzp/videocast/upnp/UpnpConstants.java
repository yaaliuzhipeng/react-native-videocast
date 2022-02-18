package com.lzp.videocast.upnp;

public class UpnpConstants {
    /**
     * @SSDP 设备、服务类型
     */
    public static String TypeAllKind = "ssdp:all";
    public static class TypeDeviceKind {
        public static final String UpnpRootDevice = "upnp:rootdevice";
        public static final String UpnpInternetGatewayDevice1 = "urn:schemas-upnp-org:device:InternetGatewayDevice:1";
        public static final String UpnpWANConnectionDevice1 = "urn:schemas-upnp-org:device:WANConnectionDevice:1";
        public static final String UpnpWANDevice1 = "urn:schemas-upnp-org:device:WANDevice:1";
        public static final String UpnpWANCommonInterfaceConfig1 = "urn:schemas-upnp-org:service:WANCommonInterfaceConfig:1";
        public static final String UpnpWANIPConnection1 = "urn:schemas-upnp-org:device:WANIPConnection:1";
        public static final String UpnpLayer3Forwarding1 = "urn:schemas-upnp-org:service:Layer3Forwarding:1";
    }
    public static class TypeServerKind {
        public static final String UpnpMediaServer1 = "urn:schemas-upnp-org:service:MediaServer:1";
        public static final String UpnpMediaRenderer1 = "urn:schemas-upnp-org:service:MediaRenderer:1";
        public static final String UpnpContentDirectory1 = "urn:schemas-upnp-org:service:ContentDirectory:1";
        public static final String UpnpRenderingControl1 = "urn:schemas-upnp-org:service:RenderingControl:1";
        public static final String UpnpConnectionManager1 = "urn:schemas-upnp-org:service:ConnectionManager:1";
        public static final String UpnpAVTransport1 = "urn:schemas-upnp-org:service:AVTransport:1";
    }

    //多播搜索请求
    public static String MulticastSocketHeaderMSEARCH = "M-SEARCH";
    public static String MulticastSocketHeaderNOTIFY = "SEARCH";
    public static String MulticastSocketHeaderUSN = "USN";
    public static String MulticastSocketHeaderSERVER = "SERVER";
    public static String MulticastSocketHeaderLOCATION = "LOCATION";
    public static String MulticastSocketRequestHeader = "M-SEARCH * HTTP/1.1";
    public static String MAN = "ssdp:discover";
    public static String MulticastSocketHost = "239.255.255.250";
    public static int MulticastSocketPort = 1900;


    public static String SymNewLine = "\r\n";
    public static String SymSepa = ": ";
    public static String SymQuote = "\"";

    public static String SymMAN = "MAN";
    public static String SymMX = "MX";
    public static String SymHOST = "HOST";
    public static String SymST = "ST";
    public static String SymNT = "NT";
    public static String SymLocation = "Location";

    public static String AcGetCurrentTransportActions = "GetCurrentTransportActions";
    public static String AcGetDeviceCapabilities = "GetDeviceCapabilities";
    public static String AcGetMediaInfo = "GetMediaInfo";
    public static String AcGetPositionInfo = "GetPositionInfo";
    public static String AcGetTransportInfo = "GetTransportInfo";
    public static String AcGetTransportSettings = "GetTransportSettings";
    public static String AcNext = "Next";
    public static String AcPause = "Pause";
    public static String AcPlay = "Play";
    public static String AcPrevious = "Previous";
    public static String AcSetAVTransportURI = "SetAVTransportURI";
    public static String AcSeek = "Seek";
    public static String AcSetPlayMode = "SetPlayMode";
    public static String AcStop = "Stop";

}
