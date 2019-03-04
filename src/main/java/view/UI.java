package view;

public interface UI {

    void welcomeScreen();

    String getUserName();

    void welcomeUser(String username);

    void showError(Error error);

    int mainMenu();

    enum Error{
        INVALIDUSERNAME
    }

}
