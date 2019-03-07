package view.tui;


import view.UI;

import java.sql.SQLOutput;

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
        String name = input.message();
        System.out.println();
        return name;
    }

    @Override
    public void showError(Error error) {
        clearScreen();
        switch(error){
            case INVALIDUSERNAME:
                System.out.println("That's not a valid username!");
                break;
            case INVALIDROOMNAME:
                System.out.println("That's not a valid room name");
                break;
            default:
                System.out.println("Error");
                break;
        }
        try{
            Thread.sleep(5000);
        }catch(Exception e){
            e.printStackTrace();
            promptContinue();
        }
    }

    @Override
    public void leftChatRoom(String username) {
        System.out.println(username + " left the chat.");
    }

    @Override
    public void chatRoomClosed() {
        clearScreen();
        System.out.println("Chatroom was closed from server side.");
        sleep(5);
    }

    @Override
    public void searchForRoom() {
        clearScreen();
        System.out.println("Searching for rooms...");
    }

    @Override
    public int chooseRoom(String[] rooms) {
        clearScreen();
        if( rooms.length == 1 ) {
            System.out.println("There is only 1 available room: \n  "+
                                rooms[0]+"\n\n" +
                                "Joining...");
            sleep(6);
            return 0;
        }else if(rooms.length > 1){
            System.out.println("There are " + rooms.length + " available rooms.\nChoose a room to join: ");
            for (int i = 0; i < rooms.length; i++) {
                System.out.println(" " + (i + 1) + ") " + rooms[i]);
            }
            return input.number(1, rooms.length + 1) - 1;

        }else{
            System.out.println("There are no available rooms.");
        }
        sleep(4);
        return -1;
    }

    @Override
    public int mainMenu() {

        clearScreen();
        System.out.println("Menu:\n" +
                            " 1) Host new chat room\n" +
                            " 2) Join chat room");

        while(true){
            try{
                return input.number(1,3)-1;

            }catch(NumberFormatException e){
                System.out.println("Wrong input!");
            }
        }
    }

    @Override
    public void showChatMessage(String msg) {
        System.out.print(msg);
    }

    @Override
    public String getRoomName() {
        clearScreen();
        System.out.print("Enter the room name: ");
        String name = input.message();
        System.out.println();
        return name;
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
    public String getIpAddress() {
        clearScreen();
        System.out.println("Enter IP address: ");
        return input.word();
    }

    @Override
    public int getPort() {
        clearScreen();
        System.out.println("Enter port number: ");
        return input.number(-2030923, 230293);
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

    void sleep(double seconds){
        try{
            Thread.sleep( (int) seconds*1000);
        }catch(Exception e){
            System.out.println("Error occurred during sleep");
            e.printStackTrace();
        }

    }
}
