package control;

import model.Validate;
import view.UI;

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
    }

}
