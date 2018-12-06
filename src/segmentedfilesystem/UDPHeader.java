package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPHeader {
    private byte FileID;
    private String filename;

    public UDPHeader(DatagramPacket p){
        byte[] data = p.getData().clone();
        int packetlength = p.getLength();
        this.FileID = data[1];
        byte[] filename = new byte[packetlength-2];
        for(int i = 2; i < packetlength; i++){
            filename[i-2] = data[i];
        }
        this.filename = new String(filename);
    }

    public byte getFileID() {
      return this.FileID;
    }

    public String getFileName() {
        return this.filename;
    }
}
