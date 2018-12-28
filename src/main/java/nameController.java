package main.java;

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

public class NameController {

    public static String nickname;
    @FXML
    public TextArea nameField;
    private FXMLLoader loader;
    private Stage util;

    public NameController(FXMLLoader loader){
        this.loader = loader;
        this.showDialog();
    }

    public NameController(){

    }


    public void showDialog() {

        try {
            Stage util = new Stage(StageStyle.TRANSPARENT);
            util.initModality(Modality.APPLICATION_MODAL);
            Parent name = FXMLLoader.load(getClass().getResource("../../fxml/name.fxml"));
            util.setScene(new Scene(name));

            util.show();
        } catch (IOException e) {
            System.out.println("не запустилось окно NAME");
        }

    }

    //Через getController передаём Controller введённый никнейм.
    public void nameButton(ActionEvent actionEvent) {
      /*  this.nickname = (String) nameField.getText();
        Controller controller = loader.getController();
        controller.messFromNameCtrl(nickname);
        util.close();*/
    }
}
