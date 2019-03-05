package control.serverside;


import java.net.ServerSocket;


public class ServerController implements Runnable {

    private RoomController room;
    private boolean run = true;


    public ServerController(RoomController room){
        this.room = room;

        new Thread(this).start();

        try{


        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void stop(){
        run = false;
    }


    /**
     * Checks for incoming connections
     */
    @Override
    public void run(){
        try( ServerSocket connection = new ServerSocket(4001) ){
            while(run){
                room.addConnection( new ClientConnection(connection.accept(), room) );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
