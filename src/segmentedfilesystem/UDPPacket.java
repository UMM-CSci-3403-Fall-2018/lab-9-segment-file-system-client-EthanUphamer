package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Comparator;

public class UDPPacket implements Comparable<UDPPacket> {
    private byte status;
    private byte FileID;
    private int packetNumber;
    private ArrayList<Byte> data = new ArrayList<Byte>();

    public UDPPacket(DatagramPacket p){
        byte[] s = p.getData();
        this.status = s[0];
        this.FileID = s[1];
        this.packetNumber = (s[2] << 8) | s[3] & 0xFF;
        System.out.println(this.packetNumber);
        for(int i = 4; i < p.getLength(); i++){
            this.data.add(s[i]);
        }
    }

    public byte getFileID(){
      return this.FileID;
    }

    public byte getStatusByte(){ return this.status;}

    public int compareTo(UDPPacket p) { return this.getPacketNumber() - p.getPacketNumber(); }

    public int getPacketNumber() { return this.packetNumber;}

}
