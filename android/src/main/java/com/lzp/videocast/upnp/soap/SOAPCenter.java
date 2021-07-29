package com.lzp.videocast.upnp.soap;


import com.lzp.videocast.upnp.network.ActionConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class SOAPCenter {

    private static final String TAG = "【SOAPCenter】";

    private String url;

    //http://192.168.0.10/control
    public SOAPCenter init(String baseURL, String targetURL) {
        this.url = baseURL + "/" + targetURL;
        return this;
    }

    public SendActionResponse sendAction(String actionName, String body) throws Exception {
        if (this.url == null) {
            throw new Exception("requested url not initialized");
        }
        ActionConnection actionConnection = new ActionConnection();
        HttpURLConnection connection = actionConnection
                .setRequestMethod(ActionConnection.HTTP_POST)
                .setSoapAction("",actionName)
                .setUrl(this.url)
                .build();
        connection.connect();
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(body.getBytes());
        outputStream.flush();
        outputStream.close();

        int respCode = connection.getResponseCode();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return new SendActionResponse(respCode,builder.toString());
    }

    private static class SendActionResponse {
        private final int responseCode;
        private final String reponseBody;
        public SendActionResponse(int code,String body) {
            responseCode = code;
            reponseBody = body;
        }
        public int getResponseCode(){
            return responseCode;
        }
        public String getReponseBody(){
            return reponseBody;
        }
    }

}
