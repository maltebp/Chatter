package view.tui;

import java.util.Scanner;

class TUI_Input {

    private Scanner scan = new Scanner(System.in);

    /**
     * Gets ANY input from the user
     * @return That which the user enters
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


    String word(){
        while(true){
            String input = getInput();
            if( input != null && !input.equals("") && !input.contains(" ") ){
                return input;
            }
        }
    }

}
