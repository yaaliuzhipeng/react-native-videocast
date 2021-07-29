package com.lzp.videocast.upnp.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

    public static String downloadXml(String url) throws Exception {
        URL _u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) _u.openConnection();
        connection.setRequestMethod("GET");
        int respCode = connection.getResponseCode();
        if (respCode == HttpURLConnection.HTTP_OK){
            InputStream stream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream,"utf-8"));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null ){
                builder.append(line);
            }
            bufferedReader.close();
            stream.close();
            connection.disconnect();
            return builder.toString();
        }else{
            connection.disconnect();
            return "";
        }
    }
}
