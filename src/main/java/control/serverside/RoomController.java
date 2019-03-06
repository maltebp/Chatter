package control.serverside;

import view.UI;

import java.util.LinkedList;

public class RoomController {

    private final UI ui;
    private LinkedList<ChatConnection> connections = new LinkedList<>();

    public RoomController(UI ui){
        this.ui = ui;
    }

    public void addConnection( ChatConnection connection ){
        ui.joinedChatRoom("Some user");
        connections.add(connection);
    }


    /** Sends a message recieved message to all */
    public void recieveMessage(String msg, ChatConnection source ){
        for( ChatConnection client : connections){
            if( client.isConnected() ) {
                if (client != source) {
                    boolean clientIsActive = client.recieveMessage(msg);
                    if (!clientIsActive) {
                        ui.leftChatRoom("Some user");
                        connections.remove(client);
                    }
                }
            }
        }
    }

}
