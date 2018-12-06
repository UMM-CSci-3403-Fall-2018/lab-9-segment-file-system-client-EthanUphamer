package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPHeader {
    private byte FileID;
    private String filename;

    // Take a DatagramPacket and deconstruct it into a form we can more easily use
    // Omitting unnecessary data
    public UDPHeader(DatagramPacket p){
        // Clone it so we don't run into issues of it being overwritten on a new receive
        byte[] data = p.getData().clone();
        int packetlength = p.getLength();

        this.FileID = data[1];

        // Getting the filename is a little tricky
        // Have to take the data after the first two bytes and convert to string
        byte[] filename = new byte[packetlength-2];
        for(int i = 2; i < packetlength; i++){
            filename[i-2] = data[i];
        }
        this.filename = new String(filename);
    }

    public byte getFileID() { return this.FileID;}

    public String getFileName() { return this.filename;}
}
