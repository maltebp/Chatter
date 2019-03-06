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

    private DataOutputStream output;

    public ClientConnection(Socket connection, RoomController room ){
        this.room = room;
        this.connection = connection;

        try{
            output = new DataOutputStream(connection.getOutputStream());
        }catch(Exception e){
            e.printStackTrace();
        }

        new Thread(this).start();
    }


    public boolean recieveMessage(String string){
        try{
            output.writeBytes(string);
            return true;
        }catch(SocketException e) {
            return false;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public void run() {

        try( BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))){

            StringBuilder builder = new StringBuilder();

            while(run){

                if( input.ready() ) {

                    while( input.ready() ){
                        char msgPart = (char) input.read();
                        builder.append(msgPart);
                    }

                    room.recieveMessage(builder.toString(), this);
                    builder = new StringBuilder();
                }
                Thread.sleep(500);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
