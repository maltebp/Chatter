package control;

import view.UI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listener implements Runnable {

    private Socket connection;
    private UI ui;

    public Listener( Socket connection, UI ui ){
        this.connection = connection;
        this.ui = ui;
        new Thread(this).start();
    }

    @Override
    public void run(){
        try( BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))){

            StringBuilder builder = new StringBuilder();

            while(true){

                if( input.ready() ) {

                    while( input.ready() ){
                        char msgPart = (char) input.read();
                        builder.append(msgPart);
                    }

                    ui.showChatMessage(builder.toString());
                    builder = new StringBuilder();
                }
                Thread.sleep(500);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


}
