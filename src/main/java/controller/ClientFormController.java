package controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientFormController  extends Thread{
    public Label lblText;
    public ScrollPane Spane;
    public TextField txtField;
    public VBox vbox;
    public AnchorPane AncMain;
    public AnchorPane emojiAnc;

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
            socket = new Socket("localhost", 2994);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            this.start();
        } catch (IOException e) {
            e.printStackTrace();

        }
       /* this.vbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                Spane.setVvalue((Double) newValue);
            }
        });*/
        emojiAnc.setVisible(false);

    }

    @Override
    public void run() {
        try {
            while (true) {


                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

                System.out.println(cmd);
                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i] + " ");
                }
                System.out.println(fullMsg);

                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }

                System.out.println(st);
                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);

                }
                //get 3 chars only

                System.out.println(firstChars);

                if (firstChars.equalsIgnoreCase("img")) {
                    //for the Images

                    st = st.substring(3, st.length() - 1);


                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);


                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);


                    if (!cmd.equalsIgnoreCase(lblText.getText())) {

                        vbox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);


                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text("");
                        hBox.getChildren().add(text1);

                    }

                    Platform.runLater(() -> vbox.getChildren().addAll(hBox));


                } else {

                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblText.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);

                        HBox hBoxTime = new HBox();//1
                        hBoxTime.setAlignment(Pos.CENTER_RIGHT);
                        hBoxTime.setPadding(new Insets(0,5,5,10));
                        String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                        Text time = new Text(stringTime);
                        time.setStyle("-fx-font-size: 8");//l


                        tempFlow.setStyle("-fx-color: rgb(239,242,255);" +
                                "-fx-background-color: rgb(28,92,255);" +
                                " -fx-background-radius: 10px");
                        tempFlow.setPadding(new Insets(3, 10, 3, 10));
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200); //200

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12); //12


                    if (!cmd.equalsIgnoreCase(lblText.getText() + ":")) {


                        vbox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);

                    } else {

                        Text text2 = new Text(fullMsg + " ");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                        hBox.setPadding(new Insets(2, 5, 2, 10));


                        HBox hBoxTime = new HBox();//1
                        hBoxTime.setAlignment(Pos.CENTER_RIGHT);
                        hBoxTime.setPadding(new Insets(0,5,5,10));
                        String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                        Text time = new Text(stringTime);
                        time.setStyle("-fx-font-size: 8");//L

                        flow2.setStyle("-fx-color: rgb(239,242,255);" +
                                "-fx-background-color: rgb(191,241,9);" +
                                "-fx-background-radius: 10px");
                        flow2.setPadding(new Insets(3, 10, 3, 10));
                    }

                    Platform.runLater(() -> vbox.getChildren().addAll(hBox));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void btnSendOnAction(MouseEvent mouseEvent) {

        String msg = txtField.getText();
        writer.println(lblText.getText() + ": " + msg);

        txtField.clear();


        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);

        }
    }

    public void btnCamOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblText.getText() + " " + "img" + filePath.getPath());
    }

    public void OffOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void emoji_OnAction(MouseEvent mouseEvent) {
        emojiAnc.setVisible(true);
    }

    public void smile_emo(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128540));
       txtField.setText(emoji);
       emojiAnc.setVisible(false);
    }

    public void emo_grid(MouseEvent mouseEvent) {
    }

    public void hide_emoji(MouseEvent mouseEvent) {
        emojiAnc.setVisible(false);
    }
}
