package segmentedfilesystem;

import java.util.ArrayList;
import java.util.Collections;

public class UDPFile {
    private boolean finished = false;
    private int length = Integer.MAX_VALUE;
    private ArrayList<UDPPacket> packets = new ArrayList<UDPPacket>();
    private UDPHeader header;
    private byte FileID;

    public UDPFile(UDPHeader header){
      this.header = header;
      this.FileID = this.header.getFileID();
    }

    public UDPFile(UDPPacket p){
        addPacketToFile(p);
        this.FileID = p.getFileID();
    }

    public void addHeaderToFile(UDPHeader header) {
      this.header = header;
      if(this.length == this.packets.size()){
          this.finished = true;
          Collections.sort(this.packets);
      }
    }

    public void addPacketToFile(UDPPacket packet) {
      this.packets.add(packet);
      if(isFinalPacket(packet)) {
          this.length = packet.getPacketNumber() + 1;
      }
      if(this.header != null && this.length == this.packets.size()){
          this.finished = true;
          Collections.sort(this.packets);
      }
    }

    public ArrayList<UDPPacket> retrievePackets(){return this.packets;}

    public UDPHeader retrieveHeader(){return this.header;}

    public byte getFileID(){
      return this.FileID;
    }

    public boolean sendStatus(){return this.finished;}

    private boolean isFinalPacket(UDPPacket p) {
        if(p.getStatusByte() % 4 == 3) {
            return true;
        }
        return false;
    }
}
