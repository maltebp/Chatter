package control.serverside;

import view.UI;

public class HostConnection implements ChatConnection {

    private UI ui;
    private RoomController room;

    public HostConnection( RoomController room, UI ui ){
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
            room.recieveMessage( msg, this );
        }
    }
}
