package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import java.util.Random;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamStreamer;

import com.mobilerobots.Aria.*;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    Webcam wc = Webcam.getDefault();
    WebcamStreamer stream = null;
    private int verificationCode = setVerificationCode();
    private ServerSocket ss = null;
    private boolean running = false;

    public static final int STREAMPORT = 12345;
    public static final int ROBOTPORT = 12346;

    @FXML private TextField ipAddress;
    @FXML private TextField verification;
    @FXML private TextArea webcamUpdate;

    @FXML protected void setIPAndVerifyText(ActionEvent click) throws Exception{
        ss = new ServerSocket(ROBOTPORT);
        ipAddress.setText(getIPAddress());
        verification.setText("Verification Code: " + Integer.toString(verificationCode));
        openWebcamFeed();
        setupRobot();
        running = true;
    }

    protected String getIPAddress() throws Exception {

        String ip;

        URL externalIP = new URL("http://checkip.amazonaws.com");
        BufferedReader ipReader = new BufferedReader(new InputStreamReader(externalIP.openStream()));
        ip = ipReader.readLine();

        return "IP Address: " + ip;
    }

    protected int setVerificationCode(){
        Random r = new Random();
        return r.nextInt(9000) + 1000;
    }

    protected void openWebcamFeed(){

        if(wc != null){
            wc.setViewSize(new Dimension(640,480));
            stream = new WebcamStreamer(STREAMPORT, wc, 30, true);
            webcamUpdate.setText("Webcam is currently streaming.\n");
        } else {
            webcamUpdate.setText("Nope");
        }
    }

    @FXML protected void stopServer(){
        if(running){
            stream = null;
            ss = null;
            System.exit(0);
        }
    }

    protected void setupRobot(){
//        try {
//            System.loadLibrary("AriaJava");
//            System.out.println("Library Loaded successfully");
//        } catch (UnsatisfiedLinkError e) {
//            System.err.println("Native code library libAriaJava failed to load. Make sure that its directory is in your library path; See javaExamples/README.txt and the chapter on Dynamic Linking Problems in the SWIG Java documentation (http://www.swig.org) for help.\n" + e);
//            System.exit(1);
//        }
    }

}