package main.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Statement;

public class Main extends Application {

    @FXML
    public TextArea nameField;

    public static FXMLLoader loader;
    public static String nickname;
    public static Stage util;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Controller ctrl = new Controller();
        loader = new FXMLLoader(getClass().getResource("../../fxml/main.fxml"));
        Parent root = loader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("../../fxml/main.fxml"));
        primaryStage.setTitle("Shots");
        primaryStage.setScene(new Scene(root, 900, 700));
        primaryStage.show();


        //Выведем окно name, чтобы записать имя и передадим его
        try {
            util = new Stage(StageStyle.TRANSPARENT);
            util.initModality(Modality.WINDOW_MODAL);
            Parent name = FXMLLoader.load(getClass().getResource("../../fxml/name.fxml"));
            util.setScene(new Scene(name));
            util.show();
        } catch (IOException e) {
            System.out.println("не запустилось окно NAME");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }


    public void nameButton(ActionEvent actionEvent) {
        this.nickname = (String) nameField.getText();
        Controller controller = loader.getController();
        controller.messFromNameCtrl(nickname);
        util.close();
    }

}

