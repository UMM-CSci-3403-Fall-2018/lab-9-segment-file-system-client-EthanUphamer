package segmentedfilesystem;

import java.io.*;
import java.net.*;

public class Main {
    public static final int PORT_NUMBER = 6014;

    public static void main(String[] args) throws IOException {
        Main client = new Main();
        client.start();
    }

    private void start() throws IOException {
        InetAddress heartofgold = InetAddress.getByName("heartofgold.morris.umn.edu");
        SocketAddress s = new InetSocketAddress(heartofgold, PORT_NUMBER);
        DatagramSocket socket = new DatagramSocket();
        socket.connect(s);
        byte[] buf = new byte[10];
        DatagramPacket p = new DatagramPacket(buf, 10);
        socket.send(p);
        socket.receive(p);
            System.out.println(p.getData()[0]);
            System.out.println(p.getData()[1]);
            System.out.println(p.getData()[2]);
            System.out.println(p.getData()[3]);
            System.out.println(p.getData()[4]);
    }

}
