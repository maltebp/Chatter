package control;

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

    void hostChatRoom(){
        RoomController room = new RoomController(ui);
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


    void joinChatRoom(){
        ui.searchForRoom();

        LinkedList<Socket> availableRooms = RoomSearcher.getAvailableRooms();
        String[] addresses = new String[availableRooms.size()];


        for (int i = 0; i < availableRooms.size(); i++) {
            addresses[i] = availableRooms.get(i).getInetAddress().toString().substring(1);
        }

        int chosenRoomId = ui.chooseRoom(addresses);

        if( availableRooms.size() > 0 ) {

            // Close uneeded connections and start correct one
            for (int i = 0; i < availableRooms.size(); i++) {
                if (i != chosenRoomId) {
                    try {
                        availableRooms.get(i).close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            Socket chosenRoom = availableRooms.get(chosenRoomId);

            // Request address
            try {

                ui.youJoinedChatRoom("");
                DataOutputStream output = new DataOutputStream(chosenRoom.getOutputStream());
                new Listener(chosenRoom, ui);

                output.writeBytes("CONNECT");

                while (true) {
                    String msg = ui.getChatMessage();
                    if (msg.equals("exit")) break;
                    output.writeBytes(username + ": " + msg);
                }
                chosenRoom.close();

            } catch (Exception e) {

                ui.chatRoomClosed();

                //e.printStackTrace();
            }

        }

    }

}
