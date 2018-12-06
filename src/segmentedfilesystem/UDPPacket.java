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

    // Take a DatagramPacket and extract the data we want
    // Putting it in an easier to deal with form
    public UDPPacket(DatagramPacket p){
        // Clone for safety
        byte[] data = p.getData().clone();

        this.status = data[0];

        this.FileID = data[1];

        byte[] packetnumberdata = {data[2], data[3]};
        ByteBuffer packetnumber = ByteBuffer.wrap(packetnumberdata);
        // .getInt() doesn't work in this case since it requires more than two bytes
        // We would have to pad with extra 0 bytes to use .getInt()
        this.packetNumber = (int) packetnumber.getShort();

        for(int i = 4; i < p.getLength(); i++){
            this.data.add(data[i]);
        }

    }

    public byte getFileID(){ return this.FileID;}

    public byte getStatusByte(){ return this.status;}

    // getData() returns this.data as an array instead of an ArrayList
    // to make it easier to print to files.
    public byte[] getData(){
        byte[] data = new byte[this.data.size()];
        for(int i = 0; i < this.data.size(); i++){
            data[i] = this.data.get(i);
        }
        return data;
    }

    // Custom compare function so sorting is easier
    public int compareTo(UDPPacket p) { return this.getPacketNumber() - p.getPacketNumber(); }

    public int getPacketNumber() { return this.packetNumber;}

}
