package segmentedfilesystem;

import java.io.*;
import java.net.*;

public class Main {
    public static final int PORT_NUMBER = 6014;
    UDPFile[] files = new UDPFile[3];

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
        while(true) {
          socket.receive(p);
          if(isHeader(p)) {
            addHeaderToFile(p);
          } else {
            addPacketToFile(p);
          }
        }
        /*
        System.out.println(p.getData()[0]);
        System.out.println(p.getData()[1]);
        System.out.println(p.getData()[2]);
        System.out.println(p.getData()[3]);
        System.out.println(p.getData()[4]);
        */
    }

    private void addHeaderToFile(DatagramPacket p) {
      UDPHeader header = new UDPHeader(p);
      for(UDPFile file : files) {
        if(file == null) {
          file = new UDPFile(header);
          break;
        } else if(file.getFileID() == header.getFileID()) {
          file.addHeaderToFile(header);
          break;
        }
      }
    }

    private void addPacketToFile(DatagramPacket p) {
      UDPPacket packet = new UDPPacket(p);
      for(UDPFile file : files) {
        if(file == null) {
          file = new UDPFile(packet);
          break;
        } else if(file.getFileID() == packet.getFileID()) {
          file.addPacketToFile(packet);
          break;
        }
      }
    }

    private boolean isHeader(DatagramPacket p) {
      if(p.getData()[0] % 2 == 0) {
        return true;
      } else {
        return false;
      }
    }

}
