package view.tui;

import java.util.Scanner;

class TUI_Input {

    private Scanner scan = new Scanner(System.in);

    /**
     * Gets ANY input from the user. Does not validate the
     * input in any way.
     */
    private String getInput(){
        while(true) {
            try {
                return scan.nextLine();
            } catch (Exception e) {
                System.out.println("Bug in input!");
                e.printStackTrace();
            }
        }
    }


    String anything(){
        return getInput();
    }


    /**
     * Prompt an input from the user in form of a single word;
     * meaning that it may contain no spaces, nor be empty.
     */
    String word(){
        while(true){
            String input = getInput();
            if( input != null && !input.equals("") && !input.contains(" ") ){
                return input;
            }
        }
    }

}
