package com.example.oopapplication;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.*;

import java.io.IOException;
import java.util.Objects;


public class LogInController {
    @FXML
    private AnchorPane anchorPaneMain, anchorPaneMain1, anchorPaneSI, anchorPaneSU, anchorPaneCover;

    @FXML
    private ImageView imgViewBG;

    @FXML
    private Button btnSI, btnHeaderButton, btnSU;

    @FXML
    private Label lblSubHeader, lblTitle, lblSubTitle, lblSubHeader1, lblAge, lblYear, lblSIError, lblSUError;

    @FXML
    private TextField txtSINumber, txtSUNumber, txtFName, txtLName, txtCourse, txtAge;

    @FXML
    private PasswordField passSi, passSU1, passSU;

    @FXML
    private ChoiceBox<Integer> cmbYear;

    @FXML
    private ChoiceBox<String> cmbStatus;

    private boolean isSIMode = true;
    private Integer[] year = {1, 2, 3, 4};

    private String[] status = {"Student", "Guest"};

    private boolean noErrors = false;

    public void initialize(){
        ConnectDB con = new ConnectDB();
        Connection connection = con.Connect();
        cmbYear.getItems().addAll(year);
        cmbStatus.getItems().addAll(status);
        cmbStatus.setOnAction(this::setText);
        anchorPaneSU.setVisible(false);
        anchorPaneCover.toFront();
    }

    public void signUp(){
        if(noErrors){

            /********************
             *  ADD TO DATABASE *
             ********************/

        }

        else{

            /******************************
             *  DISPLAY ERROR LOGIC       *
             *  use lblSUError.setText(); *
             ******************************/

        }
    }
    public void signIn(MouseEvent event) {

        if (!noErrors) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

            } else {

            /******************************
             *  DISPLAY ERROR LOGIC       *
             *  use lblSIError.setText(); *
             ******************************/

            }
        }

    public void setText(ActionEvent event){
        if (Objects.equals(cmbStatus.getValue(), "Student")){
            txtSUNumber.setPromptText("Student Number (20XX-XXXXX)...");
            txtCourse.setVisible(true);
            lblYear.setVisible(true);
            cmbYear.setVisible(true);
        }
        else{
            txtSUNumber.setPromptText("Guest Number...");
            txtSUNumber.setText("");
            cmbYear.setValue(null);
            txtCourse.setVisible(false);
            lblYear.setVisible(false);
            cmbYear.setVisible(false);
        }
    }
    public void toggleMode(){
        btnHeaderButton.setDisable(true);
        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(0.5), anchorPaneCover);
        TranslateTransition signIntoCenter = new TranslateTransition(Duration.seconds(0.25), anchorPaneSI);
        TranslateTransition signUptoCenter = new TranslateTransition(Duration.seconds(0.25), anchorPaneSU);

        if (isSIMode) {
            moveTransition.setByX(-anchorPaneMain1.getWidth() + anchorPaneCover.getWidth());
            signIntoCenter.setByX(anchorPaneMain1.getWidth()/2 - anchorPaneSI.getWidth()/2);
            signIntoCenter.play();
            signIntoCenter.setOnFinished(event ->{
                anchorPaneSI.setVisible(false);
                anchorPaneSU.setVisible(true);
                signUptoCenter.play();
            });
            signUptoCenter.setByX(563/2);

        } else {
            moveTransition.setByX(anchorPaneMain1.getWidth() - anchorPaneCover.getWidth());
            signUptoCenter.setByX(-anchorPaneMain1.getWidth()/2 + anchorPaneSI.getWidth()/2);
            signUptoCenter.play();
            signUptoCenter.setOnFinished(event ->{
                anchorPaneSU.setVisible(false);
                anchorPaneSI.setVisible(true);
                signIntoCenter.play();
            });
            signIntoCenter.setByX(-563/2);
        }

        moveTransition.play();
        moveTransition.setOnFinished(event -> btnHeaderButton.setDisable(false));
        moveTransition.setInterpolator(Interpolator.LINEAR);
        signIntoCenter.setInterpolator(Interpolator.LINEAR);
        signUptoCenter.setInterpolator(Interpolator.LINEAR);
        isSIMode = !isSIMode;
    }
}

