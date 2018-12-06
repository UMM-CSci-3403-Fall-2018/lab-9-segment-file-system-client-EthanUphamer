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
        // Setting up the socket connection
        InetAddress heartofgold = InetAddress.getByName("heartofgold.morris.umn.edu");
        SocketAddress s = new InetSocketAddress(heartofgold, PORT_NUMBER);
        DatagramSocket socket = new DatagramSocket();
        socket.connect(s);

        // Setting up the buffer for receiving packets from the server
        byte[] buf = new byte[1028];

        // Create and send a packet to the server to say hello
        DatagramPacket packet = new DatagramPacket(buf, 1028);
        socket.send(packet);

        // While loop to receive packets from the server
        // Checks every time to see if the files are complete
        while(completionStatus(this.files)) {

          // Get the packet from the server
          socket.receive(packet);

          // Check if the packet is a header or not and send to the appropriate method
          if(isHeader(packet)) {
            addHeaderToFile(packet);
          } else {
            addPacketToFile(packet);
          }
        }

        // Create the Files to output the data to
        for(int i = 0; i < 3; i++) {
            File file = new File(this.files[i].retrieveHeader().getFileName());
            file.createNewFile();
        }

        // Output the actual data to each file based on it's file name
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
      // Loop through the UDPFile array to see if we have a UDPFile to match the header
      for(int i = 0; i < files.length; i++) {
        // If we reach a spot in the array that is null we know that we haven't gotten any data for a file of the particular FileID
        // So we create a new UDPFile with the header and add it to the array
        if(files[i] == null) {
          files[i] = new UDPFile(header);
          break;
        } // Check if the FileID's match and if they do we add the header to that file
         else if(files[i].getFileID() == header.getFileID()) {
          files[i].addHeaderToFile(header);
          break;
        }
      }
    }

    private void addPacketToFile(DatagramPacket p) {
      UDPPacket packet = new UDPPacket(p);
      // Loop through the UDPFile array to see if we have a UDPFile to match the packet we got
      for(int i = 0; i < files.length; i++) {
        // If we reach a spot in the array that is null we know that we haven't gotten any data for a file of the particular FileID
        // So we create a new UDPFile with the packet and add it to the array
        if(files[i] == null) {
          files[i] = new UDPFile(packet);
          break;
        } // Check if the FileID's match and if they do we add the packet to that file
          else if(files[i].getFileID() == packet.getFileID()) {
          files[i].addPacketToFile(packet);
          break;
        }
      }
    }

    private boolean isHeader(DatagramPacket p) {
      // If the first byte of data we get is even we know the packet is a header
      if(p.getData()[0] % 2 == 0) {
        return true;
      } else {
        return false;
      }
    }

    // Loops through the array of UDPFile's and returns true if we aren't done and false otherwise
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
