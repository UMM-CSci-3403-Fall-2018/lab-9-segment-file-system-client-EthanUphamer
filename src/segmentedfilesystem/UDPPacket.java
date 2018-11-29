package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPPacket {
    private byte status;
    private byte FileID;
    private byte[] packetNumber = new byte[2];
    private ArrayList<Byte> data;

    public UDPPacket(DatagramPacket p){
        byte[] s = p.getData();
        this.status = s[0];
        this.FileID = s[1];
        this.packetNumber[0] = s[2];
        this.packetNumber[1] = s[3];
        for(int i = 4; i < p.getLength(); i++){
            this.data.add(s[i]);
        }
    }

    public byte getFileID(){
      return this.FileID;
    }
}
