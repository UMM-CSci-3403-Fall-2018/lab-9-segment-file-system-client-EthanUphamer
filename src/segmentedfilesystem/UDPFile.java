package segmentedfilesystem;

import java.util.ArrayList;
import java.util.Collections;

public class UDPFile {
    private boolean finished = false;
    private int length = Integer.MAX_VALUE;
    private ArrayList<UDPPacket> packets = new ArrayList<UDPPacket>();
    private UDPHeader header;
    private byte FileID;

    // Constructor for if the first packet we get for a file is a header
    public UDPFile(UDPHeader header){
      this.header = header;
      this.FileID = this.header.getFileID();
    }

    // Constructor for if the first packet we get for a file is a data packet
    public UDPFile(UDPPacket p){
        addPacketToFile(p);
        this.FileID = p.getFileID();
    }

    public void addHeaderToFile(UDPHeader header) {
      this.header = header;
      // Checks to see if the file has all the data packets it needs as well as the header
      // If it does we set finished to be true and sort the array so that the packets are in the right order
      if(this.length == this.packets.size()){
          this.finished = true;
          Collections.sort(this.packets);
      }
    }

    public void addPacketToFile(UDPPacket packet) {
      this.packets.add(packet);
      // Check if this is the last packet of the file
      // If it is we use that packets packetNumber to set the total number of packets we should expect from the server
      if(isFinalPacket(packet)) {
          this.length = packet.getPacketNumber() + 1;
      }
      // Check if we have all the data packets and the header for the file
      // If we do, we set finished to be true and sort the array of packets so they're in the right order
      if(this.header != null && this.length == this.packets.size()){
          this.finished = true;
          Collections.sort(this.packets);
      }
    }

    public ArrayList<UDPPacket> retrievePackets(){return this.packets;}

    public UDPHeader retrieveHeader(){return this.header;}

    public byte getFileID(){ return this.FileID;}

    public boolean sendStatus(){ return this.finished;}

    // We can check if a packet is the final packet of a final by seeing if the second bit is 1
    // We check that by seeing if the status byte is 3 mod 4
    private boolean isFinalPacket(UDPPacket p) {
        if(p.getStatusByte() % 4 == 3) {
            return true;
        }
        return false;
    }
}
