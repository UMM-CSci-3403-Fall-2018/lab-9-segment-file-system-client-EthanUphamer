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
        byte[] buf = new byte[1028];
        DatagramPacket packet = new DatagramPacket(buf, 1028);
        socket.send(packet);
        while(completionStatus(this.files)) {
          socket.receive(packet);
          if(isHeader(packet)) {
            addHeaderToFile(packet);
          } else {
            addPacketToFile(packet);
          }
        }
        for(int i = 0; i < 3; i++) {
            File file = new File(this.files[i].retrieveHeader().getFileName());
            file.createNewFile();
        }
        for(int j = 0; j < 3; j++) {
            FileOutputStream fileoutput = new FileOutputStream(this.files[j].retrieveHeader().getFileName());
            for (int i = 0; i < this.files[j].retrievePackets().size(); i++) {
                fileoutput.write(this.files[j].retrievePackets().get(i).getData());
            }
            fileoutput.close();
        }

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
        boolean status = true;
        for(int i = 0; i < 3; i++){
         if(arr[i] != null && arr[i].sendStatus()){
             status = false;
         } else {
             return true;
         }
        }
        return status;
    }
}
