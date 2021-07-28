package com.lzp.videocast.upnp.network;

import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostAction {

    public static void SetAVTransportCurrentURI() throws Exception {
        URL url = new URL("http://192.168.0.104:39520/_urn:schemas-upnp-org:service:AVTransport_control");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        //设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/xml");

        //设置 Soap Action
        connection.setRequestProperty("SOAPACTION", "urn:upnp-org:serviceId:AVTransport#SetAVTransportURI");

        connection.connect();
        OutputStream outputStream = connection.getOutputStream();

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<s:Envelope s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "    <s:Body>\n" +
                "        <u:SetAVTransportURI xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\">\n" +
                "            <InstanceID>0</InstanceID>\n" +
                "            <CurrentURI>http://cos-lianailing.hashfun.cn/movies/1/8.mp4</CurrentURI>\n" +
                "            <CurrentURIMetaData>TestVideo</CurrentURIMetaData>\n" +
                "        </u:SetAVTransportURI>\n" +
                "    </s:Body>\n" +
                "</s:Envelope>");
        outputStream.write(builder.toString().getBytes());
        outputStream.flush();
        outputStream.close();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK){
            //ok
            Log.i("TAG", "SetAVTransportCurrentURI Success, 已同步视频播放链接至终端");
        }
        connection.disconnect();
    }

    public static void Play() throws Exception {
        URL url = new URL("http://192.168.0.104:39520/_urn:schemas-upnp-org:service:AVTransport_control");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        //设置通用的请求属性
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Content-Type", "application/xml");

        //设置 Soap Action
        connection.setRequestProperty("SOAPACTION", "urn:upnp-org:serviceId:AVTransport#Play");

        connection.connect();
        OutputStream outputStream = connection.getOutputStream();

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n" +
                "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                "    <s:Body>\n" +
                "        <u:Play xmlns:u=\"urn:schemas-upnp-org:service:AVTransport:1\">\n" +
                "            <InstanceID>0</InstanceID>\n" +
                "            <Speed>1</Speed>\n" +
                "        </u:Play>\n" +
                "    </s:Body>\n" +
                "</s:Envelope>");
        outputStream.write(builder.toString().getBytes());
        outputStream.flush();
        outputStream.close();

        int code = connection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK){
            //ok
            Log.i("TAG", "Play Success, 当前开始播放视频");
        }
        connection.disconnect();
    }

    private static void generateConnection(){

    }
}
