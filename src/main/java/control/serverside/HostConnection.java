package control.serverside;

import view.UI;

public class HostConnection implements ChatConnection {

    private UI ui;
    private RoomController room;
    private String username;

    public HostConnection( String username, RoomController room, UI ui ){
        this.username = username;
        this.room = room;
        this.ui = ui;
    }

    @Override
    public void recieveMessage( String msg ){
        ui.showChatMessage( msg );
    }

    public void writeMessage(){
        while(true){
            String msg = ui.getChatMessage();
            if( msg.equals("exit")) break;
            room.recieveMessage( username + ": "+msg, this );
        }
    }
}
