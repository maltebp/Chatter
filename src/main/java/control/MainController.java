package control;

import control.clientside.ServerConnection;
import control.serverside.HostConnection;
import control.serverside.RoomController;
import control.serverside.ServerController;
import model.RoomSearcher;
import model.Validate;
import view.UI;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import static java.net.InetAddress.getLocalHost;

public class MainController {

    private UI ui;

    private String username;

    public MainController(UI ui){
        this.ui = ui;
    }

    public void start(){
        ui.welcomeScreen();

        while(true){
            username = ui.getUserName();
            if(Validate.username(username)) break;
            ui.showError(UI.Error.INVALIDUSERNAME);
        }

        ui.welcomeUser(username);

        while(true){
            switch(ui.mainMenu()){
                case 0:
                    hostChatRoom();
                    break;
                case 1:
                    joinChatRoom();
                    break;
            }
        }

    }

    private void hostChatRoom(){

        String roomName = "";
        do{
            roomName = ui.getRoomName();
            if( Validate.roomname(roomName)) break;
            ui.showError(UI.Error.INVALIDROOMNAME);
        }while(true);

        RoomController room = new RoomController(roomName, ui);
        HostConnection host = new HostConnection(username, room, ui);
        room.addConnection( host );
        ServerController server = new ServerController( room );

        try{
            ui.newChatRoom(getLocalHost().getHostAddress() + ":4001");
        }catch(Exception e){
            e.printStackTrace();
        }

        host.writeMessage();

        server.close();
        room.close();
    }


    private void joinChatRoom(){
        ui.searchForRoom();


        LinkedList<ServerConnection> availableRooms = RoomSearcher.getAvailableRooms();
        String[] names = new String[availableRooms.size()];


        for (int i = 0; i < availableRooms.size(); i++) {
            names[i] = availableRooms.get(i).getName();
        }

        int chosenRoomId = ui.chooseRoom(names);

        if( availableRooms.size() > 0 ) {

            // Close uneeded connections and start correct one
            for (int i = 0; i < availableRooms.size(); i++) {
                if (i != chosenRoomId) {
                    availableRooms.get(i).close();
                }
            }

            ServerConnection room = availableRooms.get(chosenRoomId);
            room.setUi(ui);
            room.sendMessage("JOIN "+username+"\r\n");

            ui.youJoinedChatRoom(room.getName());

            while (true) {
                String msg = ui.getChatMessage();
                if (msg.equals("exit")) break;
                room.sendMessage(username + ": "+msg+"\r\n");
            }
            room.close();
        }

    }

}
