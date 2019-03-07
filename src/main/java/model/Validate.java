package model;


public class Validate {


    public static boolean username(String username){
        return username.length() > 1   &&
                username.length() < 15 &&
            !username.contains(" ")  &&
            Character.isLetter(username.charAt(0));
    }

    public static boolean roomname(String name) {
        return  name.length() > 1   &&
                name.length() < 15 &&
                !name.contains(";")  &&
                Character.isLetter(name.charAt(0));
    }
}
//...