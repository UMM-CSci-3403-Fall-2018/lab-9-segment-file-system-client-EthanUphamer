package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPHeader {
    private boolean status;
    private byte FileID;
    private ArrayList<Byte> filename;

    public UDPHeader(DatagramPacket p){
        byte[] s = p.getData();
        this.status = false;
        this.FileID = s[1];
        for(int i = 2; i < p.getLength(); i++){
            this.filename.add(s[i]);
        }
    }

    public byte getFileID() {
      return this.FileID;
    }
    public void updateStatus() {
        this.status = true;
    }
}
