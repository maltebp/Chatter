package control.clientside;

import control.MainController;
import view.UI;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Pattern;

public class ServerConnection {

    private String name = null;
    private Socket connection;
    private boolean open = true;
    private DataOutputStream output;
    private UI ui;

    public ServerConnection(Socket connection){
        this.connection = connection;
        try{
            output = new DataOutputStream( connection.getOutputStream() );
            listen();
        }catch(Exception e){
            close();
        }
    }

    // This is a temporary solution - come up with something different!
    public void setUi(UI ui){
        this.ui = ui;
    }

    public void close(){
        open = false;
        try{
            output.close();
        }catch(Exception e){/**/}
        try{
            connection.close();
        }catch(Exception e){/**/}
    }


    public boolean sendMessage( String msg ){
        try{
            output.writeBytes(msg);
            return true;
        }catch(Exception e){
            close();
            return false;
        }
    }

    /**
     * Returns the name of the server. In case the name has been given (or loaded yet),
     * it returns the IP-address instead.
     */
    public String getName(){
        return (name != null && !name.equals("")) ? name : connection.getInetAddress().toString().substring(1);
    }


    public void listen(){


        new Thread(new Runnable() {
            @Override
            public void run() {
                try( BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                    StringBuilder builder = new StringBuilder();

                    while(open){
                        char msgPart = (char) input.read();
                        builder.append(msgPart);

                        String msg = builder.toString();

                        if( msg.length() > 3 && msg.substring(msg.length()-2).equals("\r\n") ){
                            String adjustedMsg = msg.substring(0, msg.length()-2);

                            if( adjustedMsg.substring(0, 3).equals("001") ){
                                String[] msgParts = adjustedMsg.split(Pattern.quote(";"));
                                name = msgParts[1];
                            }else{
                                ui.showChatMessage(msg);
                            }

                            builder = new StringBuilder();
                        }
                    }

                }catch(Exception e){
                    close();
                }
            }
        }).start();

    }


}
