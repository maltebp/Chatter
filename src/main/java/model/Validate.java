package model;


public class Validate {


    public static boolean username(String username){
        if( username.length() < 4   ||
            username.contains(" ")  ||
            Character.isDigit(username.charAt(0))
        ){ return false; }
        return true;
    }

}
