package broker.three.test;

import broker.three.client.Broker;

public class ClientTestApp {
    public static void main(String args[])throws Exception {
        Broker broker = new Broker();
        Thread t = new Thread(broker);
        t.start();
    }
}
