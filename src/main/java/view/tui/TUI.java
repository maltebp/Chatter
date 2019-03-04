package view.tui;


import view.UI;

public class TUI implements UI {

     private TUI_Input input = new TUI_Input();


    @Override
    public void welcomeScreen() {
        clearScreen();
        System.out.println(
                        "==========================\n" +
                        "--- WELCOME TO CHATTER ---\n" +
                        "==========================\n\n" +
                        "This is a small chat program\n" +
                        "where you can chat with your\n" +
                        "friends on your local network.\n\n" +
                        "Enter anything to continue..."
        );
        input.anything();
    }


    void clearScreen(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch(Exception e){
            System.out.println("Error occured while clearing screen.");
        }
    }
}
