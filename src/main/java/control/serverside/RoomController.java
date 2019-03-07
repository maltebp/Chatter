package control.serverside;

import view.UI;

import java.util.LinkedList;

public class RoomController {

    private final UI ui;
    private String roomname;
    private LinkedList<ChatConnection> connections = new LinkedList<>();

    public RoomController(String roomname, UI ui){
        this.roomname = roomname;
        this.ui = ui;
    }

    public void addConnection( ChatConnection connection ){
        ui.joinedChatRoom(connection.getUserName());
        connections.add(connection);
    }

    public void close(){
        for( ChatConnection client : connections){
            if( client.isConnected() ) {
                client.close();
            }
        }
    }

    public String getRoomname(){
        return roomname;
    }


    /** Sends a message recieved message to all */
    public void recieveMessage(String msg, ChatConnection source ){
        for( ChatConnection client : connections){
            if( client.isConnected() ) {
                if (client != source) {
                    boolean clientIsActive = client.recieveMessage(msg);
                    if (!clientIsActive) {
                        ui.leftChatRoom(client.getUserName());
                        connections.remove(client);
                    }
                }
            }
        }
    }

}
