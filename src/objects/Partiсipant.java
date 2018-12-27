package objects;


import java.util.ArrayList;
import java.util.List;

public class Partiсipant {

//constructors:
    public Partiсipant(String idd){
        this.uuid = idd;
    }

//static members (variables)
    public static String uuid;
    public static int role;
    public static int persona;
    public static List<Integer> weapons = new ArrayList<>();
    public static List<Integer> hand = new ArrayList<>();

//setters and getters

}
