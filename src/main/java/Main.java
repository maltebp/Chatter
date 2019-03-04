import control.MainController;
import view.tui.TUI;

public class Main {

    public static void main(String[] args) {
        new MainController( new TUI() ).start();
    }

}
