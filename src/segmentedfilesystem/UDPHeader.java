package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPHeader {
    private byte FileID;
    private String filename;

    public UDPHeader(DatagramPacket p){
        byte[] s = p.getData().clone();
        int plength = p.getLength();
        this.FileID = s[1];
        byte[] b = new byte[plength-2];
        for(int i = 2; i < plength; i++){
            b[i-2] = s[i];
        }
        this.filename = new String(b);
        System.out.println(filename);
    }

    public byte getFileID() {
      return this.FileID;
    }
}
