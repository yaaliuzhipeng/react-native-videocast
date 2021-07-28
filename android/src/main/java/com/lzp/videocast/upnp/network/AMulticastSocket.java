package com.lzp.videocast.upnp.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class AMulticastSocket {

    private MulticastSocket multicastSocket;
    private InetAddress inetAddress;
    private int port = 1900;

    public AMulticastSocket(String host,int port) throws IOException {
        this.port = port;
        multicastSocket = new MulticastSocket(port);
        inetAddress = InetAddress.getByName(host);
        multicastSocket.joinGroup(inetAddress);
    }
    public void send(String data) throws IOException {
        DatagramPacket dp =  new DatagramPacket(data.getBytes(),data.length(),inetAddress,port);
        multicastSocket.send(dp);
    }
    public DatagramPacket receive() throws IOException,NullPointerException {
        int bufSize = 2048;
        byte[] buf = new byte[bufSize];
        if (multicastSocket == null){
            throw new NullPointerException();
        }
        DatagramPacket dp = new DatagramPacket(buf,bufSize);
        multicastSocket.receive(dp);
        return dp;
    }
    public void close(){
        if (multicastSocket != null) {
            multicastSocket.close();
        }
    }
}
