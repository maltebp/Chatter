package control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

public class RoomSearcher {

    public static LinkedList<String> getAvailableHosts() {

        LinkedList<String> addresses = new LinkedList<String>();

        final byte[] ip;
        try {
            ip = InetAddress.getLocalHost().getAddress();
        } catch (Exception e) {
            return addresses;     // exit method, otherwise "ip might not have been initialized"
        }

        for(int i=1;i<=254;i++) {
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            new Thread(new Runnable() {   // new thread for parallel execution
                public void run() {
                    try {
                        ip[3] = (byte)j;
                        InetAddress address = InetAddress.getByAddress(ip);
                        String output = address.toString().substring(1);
                        if (address.isReachable(5000)) {
                            //System.out.println(output + " ("+ address.getHostName() +") is on the network");
                            addresses.add(output);

                        } else {
                            //System.out.println("Not Reachable: "+output);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }).start();     // dont forget to start the thread
        }

        try{
            Thread.sleep(6000);
        }catch(Exception e){
            e.printStackTrace();
        }

        return addresses;
    }


    public static LinkedList<Socket> getAvailableRooms( LinkedList<String> availableAddresses ){
        LinkedList<Socket> availableRooms = new LinkedList<>();

        for( String address : availableAddresses ){
            new Thread(new Runnable() {   // new thread for parallel execution
                public void run() {
                    try {
                        Socket connection = new Socket(address, 4001);
                        availableRooms.add(connection);
                    } catch (Exception e) {
                        System.out.println(address + " has no room.");
                        //e.printStackTrace();
                    }
                }
            }).start();     // dont forget to start the thread
        }

        try {
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }


        return availableRooms;
    }


    public static void main(String[] args) {
        LinkedList<String> hosts = getAvailableHosts();
        for( String host : hosts ){
            System.out.println(host);
        }

        LinkedList<Socket> rooms = getAvailableRooms(hosts);

        if( rooms.size() == 0 ){
            System.out.println("No rooms available");
        }else{
            System.out.println("Rooms: ");
            for( Socket socket : rooms ){
                System.out.println(socket.getInetAddress());
            }
        }

    }

}
