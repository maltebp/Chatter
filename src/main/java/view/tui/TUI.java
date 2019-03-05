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
                        "friends on your local network.\n\n"
        );
        promptContinue();
    }

    @Override
    public String getUserName() {
        clearScreen();
        System.out.print("Enter your user name: ");
        String name = input.word();
        System.out.println();
        return name;
    }

    @Override
    public void showError(Error error) {
        clearScreen();
        switch(error){
            case INVALIDUSERNAME:
                System.out.println("That's not a valid username!");
        }
        promptContinue();
    }

    @Override
    public int mainMenu() {

        clearScreen();
        System.out.println("Menu:");

        while(true){
            try{
                return input.number(0,2);

            }catch(NumberFormatException e){
                System.out.println("Wrong input!");
            }
        }
    }

    @Override
    public void showChatMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void newChatRoom(String address) {
        clearScreen();
        System.out.println("You opened a new chat room with the address:\n" +
                            address);
    }

    @Override
    public void youJoinedChatRoom(String addrress) {
        clearScreen();
        System.out.println("You've joined a chat room!");
    }

    @Override
    public String requestAddress() {
        return null;
    }

    @Override
    public void joinedChatRoom(String username) {
        System.out.println(username + " just joined the chat room!");
    }

    @Override
    public String getChatMessage() {
        return input.message();
    }

    void promptContinue(){
        System.out.println("\nPress enter to continue...");
        input.anything();
    }

    @Override
    public void welcomeUser(String username) {
        clearScreen();
        System.out.println("Hello "+username);
        promptContinue();
    }

    void clearScreen(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch(Exception e){
            System.out.println("Error occured while clearing screen.");
        }
    }
}
