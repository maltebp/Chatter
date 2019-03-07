package control.serverside;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection implements Runnable, ChatConnection {

    private RoomController room;
    private boolean run = true;
    private Socket connection;
    private boolean connected = false;

    private String userName;

    private DataOutputStream output;

    public ClientConnection(Socket connection, RoomController room ){
        this.room = room;
        this.connection = connection;

        try{
            output = new DataOutputStream(connection.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        recieveMessage("001;"+room.getRoomname()+"\r\n");

        new Thread(this).start();
    }


    public boolean recieveMessage(String msg){
        if( run ){
            try{
                output.writeBytes(msg);
                return true;
            }catch(Exception e){
                close();
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void close(){
        run = false;
        try{
            connection.close();
            output.close();
        }catch(Exception e){
            //...
        }

    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void run() {

        try( BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))){

            StringBuilder builder = new StringBuilder();

            while(run){

                char msgPart = (char) input.read();
                builder.append(msgPart);

                String msg = builder.toString();
                String msgEnd = "";

                if( msg.length() > 3){
                    msgEnd = msg.substring(msg.length()-2);
                }

                if( msg.length() > 3 && msgEnd.equals("\r\n") ){

                    String adjustedMsg = msg.substring(0, msg.length()-2);

                    if( !connected && adjustedMsg.substring(0,4).equals("JOIN")){
                        userName = adjustedMsg.substring(5);
                        connected = true;
                        room.addConnection(this);
                    }else{
                        room.recieveMessage(builder.toString(), this);
                    }

                    builder = new StringBuilder();

                }
            }

        }catch(Exception e){
            close();
        }

    }



    /** Checks if the client is in the chat room */
    @Override
    public boolean isConnected(){
        return connected;
    }
}
