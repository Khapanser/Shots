package objects;


import main.java.Controller;

import java.util.ArrayList;
import java.util.List;

public class Partiсipant {

//constructors:
    public Partiсipant(String idd){
        this.uuid = idd;
    }

//static members (variables)
    public  String uuid;
    public  int role;
    public  int persona;
    public  String nickname;
    public  List<Integer> weapons = new ArrayList<>();
    public  List<Integer> hand = new ArrayList<>();
    public  Controller.ClientSomthing clientSomthing;

//setters and getters\


}
