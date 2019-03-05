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


    String message(){
        while(true){
            String msg = scan.nextLine();
            if( msg.length() > 0 ){
                return msg;
            }
        }
    }

    String anything(){
        return getInput();
    }

    int number( int min, int max ) throws NumberFormatException{
        if( min < max ){
            while(true) {
                try {
                    int input = Integer.parseInt(getInput());
                    if( input < max && input >= min ) return input;
                    System.out.println("Wrong input!");

                } catch (NumberFormatException e) {
                    throw e;
                }
            }
        }
        return -1337;
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
