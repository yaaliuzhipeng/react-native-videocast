package com.lzp.videocast.upnp.network;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ActionConnection {

    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String Header_ContentType_XML = "application/xml";
    public static final String Header_ContentType_Text = "plain/text";
    public static final String Header_ContentType_Html = "application/html";
    public static final String SOAPACTION = "SOAPACTION";

    private ActionConnection actionConnection;
    private HttpURLConnection httpURLConnection;
    private String urlBaseAddr = "";
    private String urlTargetAddr = "";
    private String url = "";
    private String requestMethod = HTTP_GET;
    private Map<String,String> headers = new HashMap<String, String>();
    private String soapAction = "";

    //URL BaseAddr不能以 "/" 结尾, 正确示例: http://192.168.0.6
    public ActionConnection(String urlBaseAddr, String urlTargetAddr) {
        this.urlBaseAddr = urlBaseAddr;
        this.urlTargetAddr = urlTargetAddr;
        injectCommonHeader();
    }
    public ActionConnection(String url) {
        this.url = url;
        injectCommonHeader();
    }
    public ActionConnection() {
        injectCommonHeader();
    }
    private void injectCommonHeader(){
        headers.put("accept","*/*");
        headers.put("connection","Keep-Alive");
        headers.put("content-type",Header_ContentType_XML);
    }

    public ActionConnection setBaseAddr(String addr) {
        this.urlBaseAddr = addr;
        return this;
    }
    public ActionConnection setTargetAddr(String addr) {
        this.urlTargetAddr = addr;
        return this;
    }
    public ActionConnection setUrl(String url) {
        this.url = url;
        return this;
    }
    public ActionConnection setRequestMethod(String method){
        this.requestMethod = method;
        return this;
    }
    public ActionConnection setRequestHeader(Map<String,String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();
            headers.put(k, v);
        }
        return this;
    }
    public ActionConnection setSoapAction(String action) {
        this.soapAction = action;
        return this;
    }

    public HttpURLConnection build() throws Exception {
        if(this.url.equals("")){
           this.url = this.urlBaseAddr + this.urlTargetAddr;
        }
        URL _url = new URL(this.url);
        //设置请求链接
        httpURLConnection = (HttpURLConnection) _url.openConnection();
        //设置请求方法
        httpURLConnection.setRequestMethod(this.requestMethod);
        //设置header
        if(!this.soapAction.equals("")){
            httpURLConnection.setRequestProperty(SOAPACTION,this.soapAction);
        }
        for( Map.Entry<String,String> entry: headers.entrySet()){
            httpURLConnection.setRequestProperty(entry.getKey(),entry.getValue());
        }
        return httpURLConnection;
    }
}
