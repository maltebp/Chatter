package view;

public interface UI {

    void welcomeScreen();

    String getUserName();

    void welcomeUser(String username);

    void showError(Error error);

    int mainMenu();

    void showChatMessage( String msg );

    void newChatRoom( String address );
    void youJoinedChatRoom(String addrress );
    void joinedChatRoom( String username );

    String requestAddress();

    String getChatMessage();

    enum Error{
        INVALIDUSERNAME,
        WRONGINPUT
    }

    class ConnectionAddress{
        private String ip;
        private int port;

    }

}
