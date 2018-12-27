package main.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../fxml/main.fxml"));
        primaryStage.setTitle("Shots");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();
        //Добавить запрос имени.
        new nameController().showDialog(new ActionEvent());
     //   ClientSomthing cls = new ClientSomthing("localhost", 8080);
        //Попробуем создать контроллер
     //   Controller ctrl = new Controller(cls);

        //Controller ctrl = new Controller();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

