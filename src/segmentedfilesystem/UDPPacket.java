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
        byte[] data = p.getData();
        this.status = data[0];
        this.FileID = data[1];
        byte[] packetnumberdata = {data[2], data[3]};
        ByteBuffer packetnumber = ByteBuffer.wrap(packetnumberdata);
        this.packetNumber = (int) packetnumber.getShort();
        for(int i = 4; i < p.getLength(); i++){
            this.data.add(data[i]);
        }
    }

    public byte getFileID(){
      return this.FileID;
    }

    public byte getStatusByte(){ return this.status;}

    public byte[] getData(){
        byte[] data = new byte[this.data.size()];
        for(int i = 0; i < this.data.size(); i++){
            data[i] = this.data.get(i);
        }
        return data;
    }

    public int compareTo(UDPPacket p) { return this.getPacketNumber() - p.getPacketNumber(); }

    public int getPacketNumber() { return this.packetNumber;}

}
