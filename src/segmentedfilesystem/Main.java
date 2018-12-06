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
        UDPFile[] files = new UDPFile[3];
        byte[] buf = new byte[1028];
        DatagramPacket p = new DatagramPacket(buf, 1028);
        socket.send(p);
        int packetCount = 0;
        while(completionStatus(this.files)) {
          socket.receive(p);
          //packetCount++;
          //System.out.println("Packets recieved: " + packetCount);
          if(isHeader(p)) {
            addHeaderToFile(p);
          } else {
            addPacketToFile(p);
          }
        }
        //System.out.println(files[1].retrieveHeader().getFileName());
        File file = new File("hello");
        file.createNewFile();

        System.out.println("We actually left the while loop");
        /*FileOutputStream fos = new FileOutputStream(files[0].retrieveHeader().getFileName());
            for(int i = 0; i < files[0].retrievePackets().size(); i++) {
                fos.write(files[0].retrievePackets().get(i).getData());
            }
            fos.close();*/


    }

    private void addHeaderToFile(DatagramPacket p) {
      UDPHeader header = new UDPHeader(p);
      for(int i = 0; i < files.length; i++) {
        if(files[i] == null) {
          files[i] = new UDPFile(header);
          break;
        } else if(files[i].getFileID() == header.getFileID()) {
          files[i].addHeaderToFile(header);
          break;
        }
      }
    }

    private void addPacketToFile(DatagramPacket p) {
      UDPPacket packet = new UDPPacket(p);
      for(int i = 0; i < files.length; i++) {
        if(files[i] == null) {
          files[i] = new UDPFile(packet);
          break;
        } else if(files[i].getFileID() == packet.getFileID()) {
          files[i].addPacketToFile(packet);
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

    private boolean completionStatus(UDPFile[] arr){
        boolean b = true;
        for(int i = 0; i < 3; i++){
         if(arr[i] != null && arr[i].sendStatus()){
             b = false;
         } else {
             b =true;
         }
        }
        return b;
    }

    //private boolean checkLength(UDPFile[] arr){
        //if(UDPFile)
    //}

}
