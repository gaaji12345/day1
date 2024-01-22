package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;

public class ClientFormController  extends Thread{
    public Label lblText;
    public ScrollPane Spane;
    public TextField txtField;
    public VBox vbox;
    public AnchorPane AncMain;

    private FileChooser fileChooser;
    private File filePath;
    DataOutputStream dataOutputStream;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;


    public void initialize() throws IOException {
        String userName=LoginFormController.username;
        lblText.setText(userName);
        try {
            socket = new Socket("localhost", 2993);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            this.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
        this.vbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                Spane.setVvalue((Double) newValue);
            }
        });

    }



    public void btnSendOnAction(MouseEvent mouseEvent) {
    }

    public void btnCamOnAction(MouseEvent mouseEvent) {
    }
}
