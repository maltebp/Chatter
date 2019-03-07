package model;

import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

public class RoomSearcher {

    public static LinkedList<Socket> getAvailableRooms(){
        return checkAvailableRooms( getAvailableHosts() );
    }


    private static LinkedList<String> getAvailableHosts() {

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


    /**
     * Checks if the available hosts have a chatroom open, by connecting to
     * the chatroom socket (port 4001).
     * @param availableAddresses Available hosts checked with getAvailableHosts()
     * @return List of all available connections (connection already established).
     */
    private static LinkedList<Socket> checkAvailableRooms(LinkedList<String> availableAddresses ){
        LinkedList<Socket> availableRooms = new LinkedList<>();

        for( String address : availableAddresses ){
            new Thread(new Runnable() {   // new thread for parallel execution
                public void run() {
                    try {
                        Socket connection = new Socket(address, 4001);
                        availableRooms.add(connection);
                    } catch (Exception e) {
                        //System.out.println(address + " has no room.");
                        //e.printStackTrace();
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
        }


        return availableRooms;
    }


}
