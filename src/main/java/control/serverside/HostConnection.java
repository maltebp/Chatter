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
    public boolean recieveMessage( String msg ){
        ui.showChatMessage( msg );
        return true;
    }

    public void writeMessage(){
        while(true){
            String msg = ui.getChatMessage();
            if( msg.equals("exit")) break;
            room.recieveMessage( username + ": "+msg, this );
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
