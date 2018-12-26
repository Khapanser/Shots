package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.imageio.IIOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Controller{
    public static String uuid;
    public static List<Integer> cards = new ArrayList<>();
    public static String role;

    public String str;

    public static ClientSomthing cls;


    @FXML
    public Button startButton;
    @FXML
    public Label idLabel;
    @FXML
    public Label roleLabel;
    @FXML
    public Label cardsLabel;

//конструкторы:


    public Controller(){

    }

    public Controller(ClientSomthing cls){
        this.cls = cls;
    }


    public static void setID(String s){
        uuid = s;
    }


    public void startButtonAction(ActionEvent actionEvent) {
        System.out.println("Start button clicked");
        //startButton.disarm();
        try {
            cls.out.write("0,0"+"\n");
            cls.out.flush();
        } catch (Exception io){System.out.println("Ошибка при попытке отправить сообщение на сервер при нажатии Start Button");}

        try{
            Thread.sleep(1000);
        }catch (Exception e){}

        idLabel.setText("   "+uuid);
        roleLabel.setText("   "+role);
        //str = cards.toString();
       // cardsLabel.setText("   "+str);


    }



}
