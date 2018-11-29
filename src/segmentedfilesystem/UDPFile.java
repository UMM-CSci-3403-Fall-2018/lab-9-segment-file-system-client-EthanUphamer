package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPFile {
    private boolean finished = false;
    private int length = Integer.MAX_VALUE;
    private ArrayList<UDPPacket> packets;
    private UDPHeader header;
    private byte FileID;

    public UDPFile(UDPHeader header){
      this.header = header;
      this.FileID = this.header.getFileID();
    }

    public UDPFile(UPDPacket){

    }

    public void addHeaderToFile(UDPHeader header) {
      this.header = header;
    }

    public void addPacketToFile(UDPPacket packet) {
      this.packets.add(packet);
    }

    public byte getFileID(){
      return this.FileID;
    }
}
