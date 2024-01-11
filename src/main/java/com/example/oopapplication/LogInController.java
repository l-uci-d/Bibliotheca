package com.example.oopapplication;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class LogInController {
    public LogInController(){}


    @FXML private AnchorPane anchorPaneRoot, anchorPaneMain, anchorPaneSI, anchorPaneSU, anchorPaneCover;
    @FXML private ImageView imgViewBG;

    @FXML private Button btnSI, btnHeaderButton, btnSU;

    @FXML private Label lblClock, lblDate, lblSubHeader, lblTitle, lblSubHeader1, lblShow
            , lblShowSU, lblShowSU1
            ;

    @FXML private TextField txtSINumber, txtFName, txtLName, txtPass, txtPassSU, txtPassSU1;

    @FXML private PasswordField passSi, passSU1, passSU;

    @FXML private Text txtSucc, lblSUError, lblSIError;

    private boolean isSIMode = true;
    private Integer[] year = {1, 2, 3, 4};

    public String currentSY = getCurrentSYSEM("SY"),
            currentSem = getCurrentSYSEM("Sem");

    DataBaseMethods DB = new DataBaseMethods();
    MainMenuController mainMenuController = new MainMenuController();
    static AdminController adminController = new AdminController();
    public LocalDateTime currentDateTime = null;

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        if (currentDateTime != null)
            this.currentDateTime = currentDateTime;
        else
            this.currentDateTime = LocalDateTime.now();
    }
    public LocalTime getCurrentTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = this.currentDateTime.format(formatter);
        return LocalTime.parse(formattedTime, formatter);
    }
    public LocalDate getCurrentDate(){
        return this.currentDateTime.toLocalDate();
    }

    public String getCurrentSYSEM(String choice){
        if(choice.equals("Sem"))
            return this.currentSem;
        else
            return this.currentSY;
    }
    public void setCurrentSYSEM(){
        this.currentSem = DB.currentSYSEM("Sem");
        System.out.println("Current Sem = " + this.currentSem);
        this.currentSY = DB.currentSYSEM("SY");
        System.out.println("Current SY = " + this.currentSY);
    }

        public void initialize() {//////////////////////////////////////////////////////////////////////////////////////

            setCurrentDateTime(null);
            System.out.println(currentDateTime);
            System.out.println(getCurrentTime());
            System.out.println(getCurrentDate());
            setCurrentSYSEM();

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> mainMenuController.updateDateTimeLabels(lblClock, lblDate, null)));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            lblShow.setUserData(txtPass);
            txtPass.setUserData(passSi);

            lblShowSU.setUserData(txtPassSU);
            txtPassSU.setUserData(passSU);

            lblShowSU1.setUserData(txtPassSU1);
            txtPassSU1.setUserData(passSU1);


        }

        public void signUp() {//////////////////////////////////////////////////////////////////////////////////////
            if(lblShowSU.getText().equals("Hide"))
                passSU.setText(txtPassSU.getText());
            else
                txtPassSU.setText(passSU.getText());
            if(lblShowSU1.getText().equals("Hide"))
                passSU1.setText(txtPassSU1.getText());
            else
                txtPassSU1.setText(passSU1.getText());

            String firstName = txtFName.getText().trim();
            String lastName = txtLName.getText().trim();

            if(InputValidation.isValidName(firstName) && InputValidation.isValidName(lastName)){
                    if (!passSU.getText().isEmpty() && passSU.getText().equals(passSU1.getText())) {
                        if(passSU.getText().length() < 20 && passSU.getText().length() > 7){
                            if(InputValidation.isValidPass(passSU1.getText()) ||
                                    InputValidation.isValidPass(txtPassSU1.getText())) {


                                String[] userTypes = {"Student", "Guest", "Employee"};
                                String[] ids = new String[userTypes.length];

                                for (int i = 0; i < userTypes.length; i++) {
                                    ids[i] = DataBaseMethods.getUserID(userTypes[i], firstName, lastName, DataBaseMethods.getUserIDColumnName(userTypes[i]));
                                    System.out.println("ID = "+ ids[i]);
                                    if (DataBaseMethods.getCount("User", ids[i], "User_ID", "") == 1) {
                                        lblSUError.setText(userTypes[i] + " is already registered, please log in with username or ID " + ids[i] +".");

                                        return;
                                    }
                                }

                                passSU1.setDisable(true);
                                txtPassSU1.setDisable(true);
                                passSU.setDisable(true);
                                txtPassSU.setDisable(true);
                                txtFName.setDisable(true);
                                txtLName.setDisable(true);
                                btnSU.setDisable(true);
                                lblSUError.setText(" ");

                                List<String> userColNames = Arrays.asList("User_ID", "User_Type", "Password");

                                String  id = ids[0].isEmpty()  ?  ids[1] : ids[0];
                                if (ids[0].isEmpty() && ids[1].isEmpty()) {
                                    List<String> colNames = Arrays.asList("Guest_ID", "FirstName", "LastName");
                                    id = DataBaseMethods.getNextID("Guest", DataBaseMethods.getUserIDColumnName("Guest"), 2, "");
                                    List<String> newVals = Arrays.asList(id, firstName, lastName);
                                    DataBaseMethods.insertIntoDB("Guest", colNames, newVals);
                                }

                                String type = ids[0].isEmpty() ? "G" : "S";
                                List<String> receivedInfo = Arrays.asList(id, type, txtPassSU.getText());
                                txtSucc.setText("Successfully Registered. \nLog in with " + id);
                                DataBaseMethods.insertIntoDB("User", userColNames, receivedInfo);


                        } else lblSUError.setText("Password must contain BOTH letters and numbers.");
                    } else lblSUError.setText("Password must be between 8 and 20 characters.");
                } else lblSUError.setText("Password Fields are empty/not equal");
            } else lblSUError.setText("Names can only contain letters and spaces");


        }

        public void signIn(MouseEvent event) {//////////////////////////////////////////////////////////////////////////////////////
            if(lblShow.getText().equals("Hide"))
                passSi.setText(txtPass.getText());
            else
                txtPass.setText(passSi.getText());

        String usertype = DB.getUserType(txtSINumber.getText());

            if (usertype != null) {         //checks if registered
                if (DB.checkPassword(txtSINumber.getText(), passSi.getText())) {        //checks if password is correct
                    TranslateTransition dropdown = new TranslateTransition(Duration.seconds(1.0), anchorPaneMain);
                    TranslateTransition dropdown1 = new TranslateTransition(Duration.seconds(1.0), anchorPaneCover);

                    if (usertype.equals("E")) {             //checks user type// if admin

                            dropdown.setByY(-1000.0);
                            dropdown1.setByY(-1000.0);
                            dropdown.setOnFinished((event1) -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adminmainmenu.fxml"));
                                    Parent root = loader.load();
                                    AdminController adminController = loader.getController();

                                    adminController.setUserID(txtSINumber.getText());

                                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

                                    Scene scene = new Scene(root);
                                    stage.setTitle("Bibliotheca | Admin");
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });

                        } else {         //if not admin
                            dropdown.setByY(1000.0);
                            dropdown1.setByY(1000.0);
                            dropdown.setOnFinished((event1) -> {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainmenu.fxml"));
                                    Parent root = loader.load();
                                    MainMenuController mainMenuController = loader.getController();

                                    mainMenuController.setUserType(usertype);
                                    mainMenuController.setUserID(txtSINumber.getText());

                                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            });

                        }
                            dropdown.play();
                            dropdown1.play();
                            //
                        } else lblSIError.setText("Incorrect password or username.");       // wrong password

                    } else lblSIError.setText("No Registered User found.");

                }


        public void toggleMode() {//////////////////////////////////////////////////////////////////////////////////////
            lblSIError.setText(" ");
            lblSUError.setText(" ");

            btnHeaderButton.setDisable(true);
            TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(0.5), anchorPaneCover);
            TranslateTransition signIntoCenter = new TranslateTransition(Duration.seconds(0.25), anchorPaneSI);
            TranslateTransition signUptoCenter = new TranslateTransition(Duration.seconds(0.25), anchorPaneSU);

            if (isSIMode) {
                btnHeaderButton.setText("Not new here? Sign In!");
                moveTransition.setByX(-anchorPaneMain.getWidth() + anchorPaneCover.getWidth());
                signIntoCenter.setByX(anchorPaneMain.getWidth() / 2 - anchorPaneSI.getWidth() / 2);
                signIntoCenter.play();
                signIntoCenter.setOnFinished(event -> {
                    anchorPaneSI.setVisible(false);
                    anchorPaneSU.setVisible(true);
                    signUptoCenter.play();
                });
                signUptoCenter.setByX(563 / 2);

            } else {
                btnHeaderButton.setText("New here? Sign Up!");
                moveTransition.setByX(anchorPaneMain.getWidth() - anchorPaneCover.getWidth());
                signUptoCenter.setByX(-anchorPaneMain.getWidth() / 2 + anchorPaneSI.getWidth() / 2);
                signUptoCenter.play();
                signUptoCenter.setOnFinished(event -> {
                    anchorPaneSU.setVisible(false);
                    anchorPaneSI.setVisible(true);
                    signIntoCenter.play();
                });
                signIntoCenter.setByX(-563 / 2);
            }

            moveTransition.play();
            moveTransition.setOnFinished(event -> btnHeaderButton.setDisable(false));
            moveTransition.setInterpolator(Interpolator.LINEAR);
            signIntoCenter.setInterpolator(Interpolator.LINEAR);
            signUptoCenter.setInterpolator(Interpolator.LINEAR);
            isSIMode = !isSIMode;
        }

        public void showPass(MouseEvent event){
            adminController.showPass(event);
        }
    }

