package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.ArrayList;

public class UDPFile {
    private boolean finished = false;
    private int length = Integer.MAX_VALUE;
    private ArrayList<UDPPacket> packets;
    private UDPHeader header;

    public UDPFile(DatagramPacket p){

    }
}
