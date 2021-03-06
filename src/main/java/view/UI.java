package view;

public interface UI {

    void welcomeScreen();

    String getUserName();

    void welcomeUser(String username);

    void showError(Error error);

    int mainMenu();

    void showChatMessage( String msg );

    String getRoomName( );

    void newChatRoom( String address );

    void youJoinedChatRoom(String roomName );

    void joinedChatRoom( String username );

    void leftChatRoom( String username );

    int chooseRoom( String[] rooms );

    void searchForRoom();

    void chatRoomClosed();

    String requestAddress();

    String getChatMessage();

    String getIpAddress();

    int getPort();

    enum Error{
        INVALIDUSERNAME,
        WRONGINPUT,
        INVALIDPORT,
        INVALIDADDRESS,
        INVALIDROOMNAME
    }
}
