package sample;

import javafx.fxml.FXML;
import javafx.scene.web.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.media.*;

import java.io.*;
import java.net.*;
import java.util.regex.*;

public class ClientController {


    public static final int STREAMPORT = 12345;
    public static final int ROBOTPORT = 12346;

    private Socket s = null;

    @FXML private WebView camView;
    @FXML private Button forward;
    @FXML private Button back;
    @FXML private Button reset;
    @FXML private Button rotateLeft;
    @FXML private Button rotateRight;
    @FXML private TextField ipField;
    @FXML private TextField verifyField;

    String ip = null;


    @FXML protected void verify() throws IOException{

        String ipTest = ipField.getText();
        int verCode = 0;

        //checks if user entered a correct IP Address
        if(isValidIP(ipTest)){

            //checks if user entered properly formatted verification
            try{
                verCode = Integer.parseInt(verifyField.getText());
                if(verCode < 1000 || verCode > 9999){
                    verifyField.setText("");
                    Alert verCodeError = new Alert(Alert.AlertType.ERROR);
                    verCodeError.setHeaderText("Verification Code Input Error");
                    verCodeError.setContentText("You have entered an incorrect Verification Code.\n" +
                            "Please enter the Verification again.");
                    verCodeError.showAndWait();
                }
            }catch(NumberFormatException e){
                verifyField.setText("");
                Alert verCodeError = new Alert(Alert.AlertType.ERROR);
                verCodeError.setHeaderText("Verification Code Input Error");
                verCodeError.setContentText("You have entered an incorrect Verification Code.\n" +
                        "Please enter the Verification again.");
                verCodeError.showAndWait();
            }

            ip = ipTest;

            //if all input is correct, the user can now use the server
            //s = new Socket(ip, ROBOTPORT);
            startClientFunctions();

        }else{
            ipField.setText("");
            Alert ipError = new Alert(Alert.AlertType.ERROR);
            ipError.setHeaderText("IP Input Error");
            ipError.setContentText("You have entered an incorrect IP Address.\n" +
                    "Please enter the IP address again.");
            ipError.showAndWait();
        }



    }

    protected void startClientFunctions(){
        loadWebcamStream();
    }

    @FXML protected void sendCommands(ActionEvent e){

    }

    private boolean isValidIP(String s){

        Pattern p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        Matcher m = p.matcher(s);
        return m.find();

    }

    //http://172.26.16.103/
    public void loadWebcamStream(){
        WebEngine we = camView.getEngine();
        we.load(String.format("http://%s:%d", ip, STREAMPORT));
    }

}