package model;


public class Validate {


    public static boolean username(String username){
        return username.length() > 1   &&
                username.length() < 15 &&
            !username.contains(" ")  &&
            Character.isLetter(username.charAt(0));
    }

}
