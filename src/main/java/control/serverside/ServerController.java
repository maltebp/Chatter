package control.serverside;


import java.net.ServerSocket;


public class ServerController implements Runnable {

    private RoomController room;
    private boolean run = true;
    private ServerSocket connection;


    public ServerController(RoomController room){
        this.room = room;

        new Thread(this).start();
    }


    public void close(){
        run = false;
        try{
            connection.close();
        }catch(Exception e){
            //..
        }

    }


    /**
     * Checks for incoming connections
     */
    @Override
    public void run(){
        try( ServerSocket connection = new ServerSocket(4001) ){
            this.connection = connection;
            while(run){
                new ClientConnection(connection.accept(), room);
            }
        }catch(Exception e){
            //..e.printStackTrace();
        }

    }


}
