package com.example.oopapplication;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainMenuController{

    @FXML   private AnchorPane anchorPaneRoot, anchorPaneTaskbar, anchorPaneDesktop,
            anchorPaneMiniDashboard, anchorPaneDashboardHead,anchorPaneDashboard,
            anchorPaneMiniAppointments, anchorPaneAppointments, anchorPaneAppointmentsHead,
            anchorPaneMiniBooks,  anchorPaneBooks, anchorPaneBooksHead,
            anchorPaneMiniAccount, anchorPaneAccount, anchorPaneAccountHead;

    @FXML  private GridPane gridPaneBG;
    @FXML private Group grpPass1, grpPass2;

    @FXML   private Button btnLogOut, btnXDashboard, btnXBooks, btnXAppointments, btnXAccount, btnAccountEditCancel, btnAccountSave,
            btnAccDelete,
            btnLegend, btnAdd, btnRemove, btnConfirm, btnFilter, btnDonate
            ,btnAppointmentConfirm

            ;

    @FXML private DatePicker datePickerBDay;

    @FXML private ComboBox<String> cmbSubject, cmbBoxAccountGender;


    @FXML private Label lblClock, lblDate, lblBooksHead, lblDashboardHead, lblAppointmentsHead, lblAccountHead,
            lblBDay, lblAccountID, lblAccountFN, lblAccountLN, lblAge, lblAccountUserType, lblAccountCourse,
            lblBookHead, lblBorrow, lblSearchAuthor, lblSearchTitle, lblSubject, lblConfirmed,
            lblChosenDate, lblChosenTime, lblMInitial,
            lblShowSU, lblShowSU1, lblContext;
            ;

    @FXML  private TextField txtAccountID, txtAccountFN, txtAccountLN,
            txtAccountEmail, txtAccountCPNumber, txtAccountAddress, txtAccountUN, txtAccountCourse,
            txtSearchAuthor, txtSearchTitle,
            txtFieldDate, txtFieldTime, txtMInitial,
            txtPassSU, txtPassSU1;

            ;
    @FXML PasswordField passSU, passSU1;
    @FXML private TextArea txtAreaList;

    @FXML private TableView<TableModels.SlotData> tblVwAppointments;
        @FXML private TableColumn<TableModels.SlotData, String> tblColDay, tblColTimeIn, tblColTimeOut;
        @FXML private TableColumn<TableModels.SlotData, Integer> tblColOccupiedSlots,tblColSlotLimit;


    @FXML TableView<TableModels.Book> tblVwBooks;
        @FXML TableColumn<TableModels.Book, String> tblColBookID, tblColDesc, tblColAuthor, tblColPublisher, tblColDatePublished, tblColISBN, tblColStatus;
        @FXML TableColumn<TableModels.Book, Double> tblColPrice;


    private String[] subjects = {null, "GEN", "COM", "HIS", "FIN", "FIT", "SCI",
        "ENG", "ART", "PHL", "GEO", "PSY", "POL", "CUL", "MTH", "LAW", "EGR",
        "CLT", "LIT"};
    private static final List<String> STUDENT_COLUMN_NAMES = Arrays.asList("FirstName", "LastName", "Gender", "Email", "CP_Num", "Address", "BDay");
    private static final List<String> GUEST_COLUMN_NAMES = Arrays.asList("FirstName", "LastName", "Gender", "Email", "CP_Num", "Address", "BDay");

    private String[] gender = {"M", "F"};
    public String currentSY = getCurrentSYSEM("SY"),
            currentSem = getCurrentSYSEM("Sem");
    public String userID = getUserID();
    public String userType = getUserType();
    public String tblID = getTblID();

    public String getTblID(){ return this.tblID; }
        public void setTblID(String tblID){ this.tblID = tblID;}
        public String getUserID() {  return this.userID;   }
        public void setUserID(String userID) {
            this.userID = userID;
            refresh();
        }

        public String getUserType(){ return this.userType; }
        public void setUserType(String userType){
            this.userType = userType;
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

    boolean isEdit = false;
    public DataBaseMethods DB = new DataBaseMethods();
    static AdminController adminController = new AdminController();

    public void initialize() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println();
        setCurrentSYSEM();

        ConnectDB db = new ConnectDB();
        Connection conn = db.Connect();
        ResultSet rs = null;
        PreparedStatement ps = null;
        cmbSubject.getItems().addAll(subjects);
        cmbBoxAccountGender.getItems().addAll(gender);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTimeLabels(lblClock, lblDate, null)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        btnLogOut.setUserData("login.fxml");

        anchorPaneMiniDashboard.setUserData(anchorPaneDashboard);
        btnXDashboard.setUserData(anchorPaneDashboard);
        anchorPaneDashboard.setUserData(anchorPaneMiniDashboard);

        anchorPaneMiniBooks.setUserData(anchorPaneBooks);
        btnXBooks.setUserData(anchorPaneBooks);
        anchorPaneBooks.setUserData(anchorPaneMiniBooks);

        anchorPaneMiniAppointments.setUserData(anchorPaneAppointments);
        btnXAppointments.setUserData(anchorPaneAppointments);
        anchorPaneAppointments.setUserData(anchorPaneMiniAppointments);

        anchorPaneMiniAccount.setUserData(anchorPaneAccount);
        btnXAccount.setUserData(anchorPaneAccount);
        anchorPaneAccount.setUserData(anchorPaneMiniAccount);
        EnterAnimation(anchorPaneAccount);


        tblColDay.setCellValueFactory(cellData -> cellData.getValue().getDayProperty());
        tblColTimeIn.setCellValueFactory(cellData -> cellData.getValue().getTimeInProperty());
        tblColTimeOut.setCellValueFactory(cellData -> cellData.getValue().getTimeOutProperty());
        tblColOccupiedSlots.setCellValueFactory(cellData -> cellData.getValue().getAvailableSlotsProperty().asObject());
        tblColSlotLimit.setCellValueFactory(cellData -> cellData.getValue().getSlotLimitProperty().asObject());
        DB.populateSlot(tblVwAppointments, 0);


        tblColBookID.setCellValueFactory(cellData -> cellData.getValue().getBookIDProperty());
        tblColDesc.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        tblColAuthor.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
        tblColPublisher.setCellValueFactory(cellData -> cellData.getValue().getPublisherProperty());
        tblColDatePublished.setCellValueFactory(cellData -> cellData.getValue().getDatePublishedProperty());
        tblColPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        tblColISBN.setCellValueFactory(cellData -> cellData.getValue().getIsbnProperty());
        tblColStatus.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        DB.populateBook(tblVwBooks);


        lblShowSU.setUserData(txtPassSU);
        txtPassSU.setUserData(passSU);

        lblShowSU1.setUserData(txtPassSU1);
        txtPassSU1.setUserData(passSU1);

    }

    public void saveEdits(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        confirm = new Alert(Alert.AlertType.WARNING);
        if(lblShowSU.getText().equals("Hide"))
            passSU.setText(txtPassSU.getText());
        else
            txtPassSU.setText(passSU.getText());
        if(lblShowSU1.getText().equals("Hide"))
            passSU1.setText(txtPassSU1.getText());
        else
            txtPassSU1.setText(passSU1.getText());
        if (passSU.getText().equals(passSU1.getText())) {
            showAlert(confirm, "Password Error","Password Fields are not equal");
            return;
        }
        else if(!(passSU.getText().length() < 20) || !(passSU.getText().length() > 7)){
            showAlert(confirm, "Password Error","Password must be between 8 and 20 characters.");
            return;
            }
                if(!InputValidation.isValidPass(passSU1.getText())) {
            showAlert(confirm, "Password Error", "Password must contain BOTH letters and numbers.");
            return;
        }
        String newUn = txtAccountUN.getText();
        if(!InputValidation.isValidUserName(newUn)){
            showAlert(confirm, "Username Unavailable", "Username Invalid. \n" +
                    "Username can only contain letters, underscores, and numbers. \n" +
                    "Username must be between 5 and 30 characters.");
            return;
        }
        if(DataBaseMethods.getCount("User", newUn, "Username", "") == 1){
            showAlert(confirm, "Username Unavailable", "Username Taken.");
            return;
        }

        List<Object> newVals = Arrays.asList(txtAccountFN.getText(), txtAccountLN.getText(), cmbBoxAccountGender.getValue(), txtAccountEmail.getText()
        , txtAccountCPNumber.getText(), txtAccountAddress.getText(),
        (datePickerBDay.getValue() != null) ? datePickerBDay.getValue().toString() : null);

        setUserID(txtAccountID.getText());

        if(Objects.equals(getUserType(), "S"))
            DB.updateDB("Student", newUn, getUserID(), STUDENT_COLUMN_NAMES, newVals, "Student_No", "");
        else
            DB.updateDB("Guest", newUn,  getUserID(), GUEST_COLUMN_NAMES, newVals, "Guest_ID", "");

        isEdit = false;
        setEditableFields(false);
        btnAccountSave.setVisible(false);
        btnAccountEditCancel.setText("Edit Infos");
        refresh();
    }
    public void tglEditCancel(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (!isEdit) {
            btnAccountEditCancel.setText("Cancel Editing");
            setEditableFields(true);
            grpPass1.setVisible(true);
            grpPass2.setVisible(true);
            btnAccountSave.setVisible(true);
            btnAccDelete.setVisible(true);
            isEdit = !isEdit;
        }
            else{
                btnAccountEditCancel.setText("Edit Infos");
                setEditableFields(false);
                grpPass1.setVisible(false);
                grpPass2.setVisible(false);
                btnAccountSave.setVisible(false);
                btnAccDelete.setVisible(false);
                isEdit = !isEdit;
                refresh();
            }
        passSU.clear();
        passSU1.clear();
        txtPassSU.clear();
        txtPassSU1.clear();

    }
    private List<Object> createValuesList(TextField... fields) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return Arrays.stream(fields)
                .map(TextField::getText)
                .collect(Collectors.toList());
    }

    private void setEditableFields(boolean editable) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        txtAccountUN.setEditable(editable);
        txtAccountFN.setEditable(editable);
        txtAccountLN.setEditable(editable);
        cmbBoxAccountGender.setDisable(!editable);
        txtAccountEmail.setEditable(editable);
        txtAccountCPNumber.setEditable(editable);
        txtAccountAddress.setEditable(editable);
        datePickerBDay.setDisable(!editable);
        if(editable){
            txtAccountFN.getStyleClass().remove("account-input");
            txtAccountLN.getStyleClass().remove("account-input");
            cmbBoxAccountGender.getStyleClass().remove("account-input");
            txtAccountEmail.getStyleClass().remove("account-input");
            txtAccountCPNumber.getStyleClass().remove("account-input");
            txtAccountAddress.getStyleClass().remove("account-input");
            txtAccountUN.getStyleClass().remove("account-input");
            datePickerBDay.getStyleClass().remove("account-input");
            txtAccountFN.getStyleClass().add("account-editable-input");
            datePickerBDay.getStyleClass().add("account-editable-input");
            txtAccountLN.getStyleClass().add("account-editable-input");
            cmbBoxAccountGender.getStyleClass().add("account-editable-input");
            txtAccountEmail.getStyleClass().add("account-editable-input");
            txtAccountCPNumber.getStyleClass().add("account-editable-input");
            txtAccountAddress.getStyleClass().add("account-editable-input");
            txtAccountUN.getStyleClass().add("account-editable-input");
        }
        else {
            txtAccountFN.getStyleClass().remove("account-editable-input");
            txtAccountLN.getStyleClass().remove("account-editable-input");
            cmbBoxAccountGender.getStyleClass().remove("account-editable-input");
            txtAccountEmail.getStyleClass().remove("account-editable-input");
            txtAccountCPNumber.getStyleClass().remove("account-editable-input");
            txtAccountAddress.getStyleClass().remove("account-editable-input");
            txtAccountUN.getStyleClass().remove("account-editable-input");
            datePickerBDay.getStyleClass().remove("account-editable-input");
            datePickerBDay.getStyleClass().add("account-input");
            txtAccountUN.getStyleClass().add("account-input");
            txtAccountFN.getStyleClass().add("account-input");
            txtAccountLN.getStyleClass().add("account-input");
            cmbBoxAccountGender.getStyleClass().add("account-input");
            txtAccountEmail.getStyleClass().add("account-input");
            txtAccountCPNumber.getStyleClass().add("account-input");
            txtAccountAddress.getStyleClass().add("account-input");
        }
    }

    public void refresh(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DB.setBDay(getUserID(), datePickerBDay);
        DB.toText("User_ID", txtAccountID, getUserID(), null);
        DB.toText("Username", txtAccountUN, getUserID(), null);
        DB.toText("FirstName", txtAccountFN, getUserID(), null);
        DB.toText("LastName", txtAccountLN, getUserID(), null);
        DB.toCmbBox("vwUserInfo", "Gender", getUserID(),cmbBoxAccountGender);
        DB.toText("Email", txtAccountEmail, getUserID(), null);
        DB.toText("CP_Num", txtAccountCPNumber, getUserID(), null);
        DB.toText("Address", txtAccountAddress, getUserID(), null);
        setTblID(txtAccountID.getText());
        if (getUserType().equals("S")){
            lblAccountUserType.setText("You are a Student!");
            DB.toText(txtAccountID.getText(), txtAccountCourse, getUserID(), "Course");
        }
        else if(getUserType().equals("G")){
            lblAccountUserType.setText("You are a Guest!");
            txtAccountCourse.setVisible(false);
            lblAccountCourse.setVisible(false);
        }
    }

    void updateDateTimeLabels(Label lblClock, Label lblDate, Label lblDay) {//////////////////////////////////////////////////////////////////////////////////////
        LocalDateTime now = LocalDateTime.now();

        String dayOfWeek = now.getDayOfWeek().toString();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(timeFormatter);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(dateFormatter);

        lblClock.setText(formattedTime);
        if (lblDay != null){
            lblDate.setText(formattedDate);
            lblDay.setText(dayOfWeek);
        }

        else {
            lblDate.setText(dayOfWeek + ", " + formattedDate);
        }

        String[] styleClasses = lblClock.getStyleClass().toArray(new String[0]);
        lblClock.getStyleClass().setAll(styleClasses);
        lblDate.getStyleClass().setAll(styleClasses);


    }
    public void openApp(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var clickedPane = (AnchorPane) event.getSource();
        var appName = (AnchorPane) clickedPane.getUserData();
        appName.toFront();
        if (appName.isVisible()) {
            return;
        }
        clickedPane.setStyle("-fx-background-color: rgba(244,199,92,0.5);");
        appName.setVisible(true);
        EnterAnimation(appName);

    }

    public void closeApp(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var clickedBtn = (Button) event.getSource();
        var appName = (AnchorPane) clickedBtn.getUserData();
        var miniApp = (AnchorPane) appName.getUserData();
        miniApp.setStyle(null);
        ExitAnimation(appName);

    }


    public void EnterAnimation(AnchorPane toPane) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.25), toPane);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), toPane);
        scaleTransition.setFromX(0.1);
        scaleTransition.setFromY(0.1);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);

        scaleTransition.play();
        translateTransition.play();

    }

    public void ExitAnimation(AnchorPane anchorPane){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.25), anchorPane);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);

        scaleTransition.setToX(0.1);
        scaleTransition.setToY(0.1);
        scaleTransition.play();
        scaleTransition.setOnFinished(event -> {
            anchorPane.setVisible(false);
            anchorPane.toBack();

        });
    }


    public Alert confirm;
    public ButtonType showAlert(Alert alert, String title, String contentText){
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        return alert.showAndWait().orElse(ButtonType.CANCEL);
    }

    public void deleteAccount(){
        confirm = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType result = showAlert(confirm, "Confirm Delete?",
                "Delete Account? Your past transactions will be" +
                               " PERMANENTLY DELETED. Are you Sure?");
        if (result == ButtonType.OK) {
            DataBaseMethods.dropDB("USER", getTblID(), "User_ID");
            change("login.fxml", anchorPaneRoot);
        }
        else return;
    }

    public void showPass(MouseEvent event){
        adminController.showPass(event);
    }
    public void LogOut(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        change("login.fxml", anchorPaneRoot);
    }

    public void change(String file, AnchorPane anchorPaneMain){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Node newContent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            newContent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        anchorPaneMain.getChildren().clear();
        anchorPaneMain.getChildren().add(newContent);
    }


    public void addToList(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableModels.Book selectedBook = tblVwBooks.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            String bookId = selectedBook.getBookId();
            String title = selectedBook.getDescription();

            txtAreaList.appendText("Book ID: " + bookId + ", Title: " + title + "\n");
        }

    }

    public void removeFromList(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableModels.Book selectedBook = tblVwBooks.getSelectionModel().getSelectedItem();
        String currentText = txtAreaList.getText();

        if (selectedBook != null) {
            String entryToRemove = selectedBook.getBookId() + " - " + selectedBook.getDescription() + "\n";
            currentText = currentText.replace(entryToRemove, "");
        }

        txtAreaList.setText(currentText);
        }



    public void filterTable(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String selectedSubject = cmbSubject.getValue();
        String searchTitle = txtSearchTitle.getText().toLowerCase();
        String searchTextArea = txtAreaList.getText().toLowerCase();

        DB.bookDataList.clear();

        for (TableModels.Book book : DB.bookDataList) {
            boolean addToTable = true;

            if (selectedSubject != null && !selectedSubject.isEmpty()) {
                addToTable = book.getBookId().startsWith(selectedSubject.toUpperCase());
            } else if (!searchTitle.isEmpty()) {
                // Filter by title
                addToTable = book.getDescription().toLowerCase().contains(searchTitle);
            } else if (!searchTextArea.isEmpty()) {
                // Filter by textAreaList
                addToTable = searchTextArea.contains(book.getBookId());
            }

            if (addToTable) {
                DB.bookDataList.add(book);
            }
        }
    }


    public void popup(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }


    public void confirm(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //add to db
        lblConfirmed.setText("Your request has been received, please wait for approval.");
    }
}
