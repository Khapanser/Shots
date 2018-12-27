package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class nameController {

        public void showDialog(ActionEvent actionEvent){

            try {
                Stage stage = new Stage();
                Parent name = FXMLLoader.load(getClass().getResource("../../fxml/name.fxml"));
                stage.setTitle("Name");
                stage.setMinHeight(150);
                stage.setMinWidth(300);
                stage.setResizable(false);
                stage.setScene(new Scene(name));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
                stage.show();
            }catch(IOException e){
                System.out.println("не запустилось окно NAME");
            }

        }


}
