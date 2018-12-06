package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Comparator;
import java.nio.*;

public class UDPPacket implements Comparable<UDPPacket> {
    private byte status;
    private byte FileID;
    private int packetNumber;
    private ArrayList<Byte> data = new ArrayList<Byte>();

    public UDPPacket(DatagramPacket p){
        byte[] s = p.getData();
        this.status = s[0];
        this.FileID = s[1];
        //this.packetNumber = (s[2] << 8) | s[3] & 0xFF;
        byte[] temp = {s[2], s[3]};
        ByteBuffer wrapped = ByteBuffer.wrap(temp);
        this.packetNumber = (int) wrapped.getShort();
        //System.out.println(this.packetNumber);
        for(int i = 4; i < p.getLength(); i++){
            this.data.add(s[i]);
        }
    }

    public byte getFileID(){
      return this.FileID;
    }

    public byte getStatusByte(){ return this.status;}

    public byte[] getData(){
        byte[] b = new byte[this.data.size()];
        for(int i = 0; i < data.size(); i++){
            b[i] = this.data.get(i);
        }
        return b;
    }

    public int compareTo(UDPPacket p) { return this.getPacketNumber() - p.getPacketNumber(); }

    public int getPacketNumber() { return this.packetNumber;}

}
