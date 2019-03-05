package control;

import control.serverside.HostConnection;
import control.serverside.RoomController;
import control.serverside.ServerController;
import model.Validate;
import view.UI;

import java.io.DataOutputStream;
import java.net.Socket;

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

        switch(ui.mainMenu()){
            case 0:
                hostChatRoom();
                break;
            case 1:
                joinChatRoom();
                break;
        }
    }


    void hostChatRoom(){
        RoomController room = new RoomController(ui);
        HostConnection host = new HostConnection(room, ui);
        room.addConnection( host );
        new ServerController( room );
        ui.newChatRoom("localhost:4000");
        host.writeMessage();
    }


    void joinChatRoom(){

        // Request address
        try(Socket socket = new Socket( "localhost", 4001 )){

            ui.youJoinedChatRoom("");

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            new Listener(socket, ui);

            while(true){
                String msg = ui.getChatMessage();
                if(msg.equals("exit")) break;
                output.writeBytes(msg);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
