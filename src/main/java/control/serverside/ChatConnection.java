package control.serverside;

public interface ChatConnection {
    /**
     *
     * @param string The message to send
     * @return Whether or not the client is still active
     */
    boolean recieveMessage( String string );
}
