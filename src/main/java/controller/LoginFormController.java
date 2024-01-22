package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {


    public Button txtLogin;
    public TextField txtField;

    static String username;
    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        username=txtField.getText();
        txtField.clear();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientForm.fxml"))));
        stage.close();
        stage.centerOnScreen();
        stage.show();
       /* FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/view/CustomerForm.fxml"));
        Parent load= fxmlLoader.load();
        ClientControlller controller=fxmlLoader.getController();
        txtName.getChildren().clear();
        txtName.getChildren().add(load);*/

    }
    }

