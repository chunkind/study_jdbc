package broker.three.test;

import broker.three.server.ProtocolHandler;

public class ServerTestApp {
    public static void main(String args[])throws Exception {
        //server start
        ProtocolHandler handelr  =  new ProtocolHandler("127.0.0.1");
        handelr.start();
    }
}
