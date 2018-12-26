package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Shots");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();

        ClientSomthing cls = new ClientSomthing("localhost", 8080);
        //Попробуем создать контроллер
        Controller ctrl = new Controller(cls);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

