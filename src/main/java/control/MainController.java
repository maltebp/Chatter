package control;

import view.UI;

public class MainController {

    private UI ui;

    public MainController(UI ui){
        this.ui = ui;
    }

    public void start(){
        ui.welcomeScreen();


    }

}
