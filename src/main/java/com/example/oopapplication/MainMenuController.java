package com.example.oopapplication;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


import static com.example.oopapplication.DataBaseMethods.getBookTransacts;
import static com.example.oopapplication.HelperMethods.*;


public class MainMenuController {

    private static final List<String> STUDENT_COLUMN_NAMES = Arrays.asList("FirstName", "LastName", "Gender", "Email", "CP_Num", "Address", "BDay");
    private static final List<String> GUEST_COLUMN_NAMES = Arrays.asList("FirstName", "LastName", "Gender", "Email", "CP_Num", "Address", "BDay");
    private static final List<String> BOOK_COL_NAMES = Arrays.asList("Book_ID", "Description", "Author", "Publisher",  "ISBN");

    private static final List<String> RESERVATION_COL_NAMES = Arrays.asList("Reservation_ID", "SY", "Semester", "Student_No", "Book_ID", "Transaction_Status"
            , "Request_DateTime");
    private static final List<String> APPOINTMENT_COL_NAMES = Arrays.asList("Appointment_ID", "SY", "Semester", "Visitor_ID",
            "Slot_ID", "Day", "Date", "Transaction_Status", "Request_DateTime");

    public LocalDateTime currentDateTime = null;
    public DataBaseMethods DB = new DataBaseMethods();

    @FXML
    PasswordField passSU, passSU1;
    @FXML
    TableView<TableModels.Book> tblVwBooks;
    @FXML
    TableColumn<TableModels.Book, String> tblColBookID, tblColDesc, tblColAuthor, tblColPublisher, tblColDatePublished, tblColISBN, tblColStatus;
    @FXML
    TableColumn<TableModels.Book, Double> tblColPrice;
    boolean isEdit = false;
    @FXML
    private AnchorPane anchorPaneRoot, anchorPaneTaskbar, anchorPaneDesktop,
            anchorPaneMiniDashboard, anchorPaneDashboardHead, anchorPaneDashboard,
            anchorPaneMiniAppointments, anchorPaneAppointments, anchorPaneAppointmentsHead,
            anchorPaneMiniBooks, anchorPaneBooks, anchorPaneBooksHead,
            anchorPaneMiniAccount, anchorPaneAccount, anchorPaneAccountHead;
    @FXML
    private GridPane gridPaneBG;
    @FXML
    private Group grpPass1, grpPass2;
    @FXML
    private Button btnLogOut, btnXDashboard, btnXBooks, btnXAppointments, btnXAccount, btnAccountEditCancel, btnAccountSave,
            btnAccDelete,
            btnLegend, btnAdd, btnRemove, btnConfirm, btnFilter, btnDonate, btnAppointmentConfirm;
    @FXML
    private DatePicker datePickerBDay;
    @FXML
    private ComboBox<String> cmbSubject, cmbBoxAccountGender;
    @FXML
    private Label lblClock, lblDate, lblMonday, lblSaturday,
            lblBooksHead, lblDashboardHead, lblAppointmentsHead, lblAccountHead,
            lblBDay, lblAccountID, lblAccountFN, lblAccountLN, lblAge, lblAccountUserType, lblAccountCourse,
            lblBookHead, lblBorrow, lblSearchAuthor, lblSearchTitle, lblSubject, lblConfirmed,
            lblChosenDate, lblChosenTime, lblMInitial,
            lblShowSU, lblShowSU1, lblContext,
            lblClearSelect, lblSelectedBookID;
    @FXML
    private TextField txtAccountID, txtAccountFN, txtAccountLN,
            txtAccountEmail, txtAccountCPNumber, txtAccountAddress, txtAccountUN, txtAccountCourse,
            txtSearchAuthor, txtSearchTitle, txtSearchPublisher, txtSearchISBN,
            txtFieldDate, txtFieldTime, txtMInitial,
            txtPassSU, txtPassSU1;    public String currentSY = getCurrentSYSEM("SY"),
            currentSem = getCurrentSYSEM("Sem");
    @FXML
    private TextArea txtAreaList, txtAreaLegend, txtAreaPendingAppts, txtAreaFine, txtAreaPendingBooks,
            txtAreaApproved
                    ;

    @FXML
    private TableView<TableModels.SlotData> tblVwAppointments;

    @FXML
    private TableColumn<TableModels.SlotData, String> tblColDay, tblColTimeIn, tblColTimeOut;
    @FXML
    private TableColumn<TableModels.SlotData, Integer> tblColOccupiedSlots, tblColSlotLimit;




    private final String[] subjects = {"GEN", "COM", "HIS", "FIN", "FIT", "SCI",
            "ENG", "ART", "PHL", "GEO", "PSY", "POL", "CUL", "MTH", "LAW", "EGR",
            "CLT", "LIT"};

    public String userID = getUserID();
    public String userType = getUserType();
    private final String[] gender = {"M", "F"};
    private final Map<String, String> subjectNames = new HashMap<>();
    private final ObservableList<TableModels.Book> selectedBookIds = FXCollections.observableArrayList();

    public String tblID = getTblID();
    public String getTblID() {
        return this.tblID;
    }

    public void setTblID(String tblID) {
        this.tblID = tblID;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        refresh();
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        if (currentDateTime != null)
            this.currentDateTime = currentDateTime;
        else
            this.currentDateTime = LocalDateTime.now();
    }

    public LocalTime getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = this.currentDateTime.format(formatter);
        return LocalTime.parse(formattedTime, formatter);
    }

    public LocalDate getCurrentDate() {
        return this.currentDateTime.toLocalDate();
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCurrentSYSEM(String choice) {
        if (choice.equals("Sem"))
            return this.currentSem;
        else
            return this.currentSY;
    }

    public void setCurrentSYSEM() {
        this.currentSem = DB.currentSYSEM("Sem");
        System.out.println("Current Sem = " + this.currentSem);
        this.currentSY = DB.currentSYSEM("SY");
        System.out.println("Current SY = " + this.currentSY);
    }

    public String initializeLegend() {
        subjectNames.put("COM", "COMSCI");
        subjectNames.put("HIS", "HISTORY");
        subjectNames.put("FIN", "FINANCIAL");
        subjectNames.put("FIT", "FITNESS & HEALTH");
        subjectNames.put("FIL", "FILIPINO LIT");
        subjectNames.put("SCI", "SCIENCE");
        subjectNames.put("ENG", "ENGLISH");
        subjectNames.put("ART", "ART");
        subjectNames.put("PHL", "PHILOSOPHY");
        subjectNames.put("GEO", "GEOGRAPHY");
        subjectNames.put("PSY", "PSYCHOLOGY");
        subjectNames.put("POL", "POLITICAL SCIENCE");
        subjectNames.put("CUL", "CULINARY");
        subjectNames.put("MTH", "MATH");
        subjectNames.put("LAW", "LAW");
        subjectNames.put("EGR", "ENGINEERING");
        subjectNames.put("CLT", "CULTURAL STUDIES");
        subjectNames.put("LIT", "LITERATURE");
        subjectNames.put("GEN", "GENERAL STUFF");
        Map<String, Integer> subjectCounts = new HashMap<>();


        for (String subject : subjects) {
            if (!subject.isEmpty() || subject != " ") {
                int count = DataBaseMethods.getCountLike("Book", subject, "Book_ID", "");
                subjectCounts.put(subject, count);

            }
        }


        StringBuilder result = new StringBuilder();

        for (String subject : subjects) {
            if (subject != null) {
                String subjectName = subjectNames.get(subject);
                result.append(String.format("%-4s - %-20s \n", subject, subjectName));
            }
        }

        return result.toString();
    }

    private final ObservableList<TableModels.Book> userAppts = FXCollections.observableArrayList();
    private final ObservableList<TableModels.PendingBooks> userPendingBooks = FXCollections.observableArrayList()
            , approvedBooks = FXCollections.observableArrayList()
            ;

    private void refreshTxtAreas(){
        getBookTransacts("vwPendingBookRequests", "PENDING", getTblID(), userPendingBooks, txtAreaPendingBooks);
        getBookTransacts("vwPendingBookRequests", "APPROVED", getTblID(), userPendingBooks, txtAreaApproved);
    }

    public void initialize2(){
        txtAreaLegend.appendText(initializeLegend());
        System.out.println(getCurrentTime());
        System.out.println(getCurrentDate());

        if(getUserType().equalsIgnoreCase("G")){
            txtAreaList.setText("Sorry, as guest you can't borrow books. ");
            txtAreaList.setDisable(true);
            btnAdd.setDisable(true);
            btnRemove.setDisable(true);
            btnConfirm.setDisable(true);

        }
        if(getUserType().equalsIgnoreCase("S")){
        refreshTxtAreas();

        }

        LocalDate monday = getCurrentDate().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        lblMonday.setText(monday + " - ");
        LocalDate saturday = getCurrentDate().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        lblSaturday.setText(String.valueOf(saturday));





    }
    public void initialize() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




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
        tblVwAppointments.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> apptListener(newValue));

        tblColBookID.setCellValueFactory(cellData -> cellData.getValue().getBookIDProperty());
        tblColDesc.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        tblColAuthor.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
        tblColPublisher.setCellValueFactory(cellData -> cellData.getValue().getPublisherProperty());
        tblColDatePublished.setCellValueFactory(cellData -> cellData.getValue().getDatePublishedProperty());
        tblColPrice.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        tblColISBN.setCellValueFactory(cellData -> cellData.getValue().getIsbnProperty());
        tblColStatus.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        TableModels.textWrap(tblColDesc);
        TableModels.textWrap(tblColISBN);
        TableModels.textWrap(tblColAuthor);

        DB.populateBook(tblVwBooks, null);

        tblVwBooks.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> bookListener(newValue));


        lblShowSU.setUserData(txtPassSU);
        txtPassSU.setUserData(passSU);

        lblShowSU1.setUserData(txtPassSU1);
        txtPassSU1.setUserData(passSU1);


    }

    private  void apptListener(TableModels.SlotData newValue){
        if(newValue!=null){
            LocalDate currentDate = getCurrentDate();
            txtFieldDate.setText(String.valueOf(getCurrentDate().with(TemporalAdjusters.next(getDayOfWeekFromString(newValue.getDay())))));

            txtFieldTime.setText(newValue.getTimeIn() + " to " + newValue.getTimeOut());
        }
    }

    private void bookListener(TableModels.Book newValue) {
        if (newValue != null) {
            lblSelectedBookID.setText(newValue.getBookId());
        } else {
            tblVwBooks.getSelectionModel().clearSelection();
            lblSelectedBookID.setText("");
            cmbSubject.setValue("");
            txtSearchTitle.clear();
            txtSearchAuthor.clear();
            txtSearchPublisher.clear();
            txtSearchISBN.clear();
        }
    }

    public void clearBookSelect(MouseEvent event) {
        bookListener(null);

    }

    public void saveEdits(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (lblShowSU.getText().equals("Hide"))
            passSU.setText(txtPassSU.getText());
        else
            txtPassSU.setText(passSU.getText());
        if (lblShowSU1.getText().equals("Hide"))
            passSU1.setText(txtPassSU1.getText());
        else
            txtPassSU1.setText(passSU1.getText());

        if (!passSU.getText().isEmpty() && !passSU1.getText().isEmpty()) {
            if (!passSU.getText().equals(passSU1.getText())) {
                showAlert(warning, "Password Error", "Password Fields are not equal");
                return;
            } else if (!(passSU.getText().length() < 20) || !(passSU.getText().length() > 7)) {
                showAlert(warning, "Password Error", "Password must be between 8 and 20 characters.");
                return;
            }
            if (!InputValidation.isValidPass(passSU1.getText())) {
                showAlert(warning, "Password Error", "Password must contain BOTH letters and numbers.");
            }
        } else {
            String newUn = txtAccountUN.getText();
            if (!newUn.isEmpty()) {
                if (!InputValidation.isValidUserName(newUn)) {
                    showAlert(warning, "Username Unavailable", "Username Invalid. \n" +
                            "Username can only contain letters, underscores, and numbers. \n" +
                            "Username must be between 5 and 30 characters.");
                    return;
                }
                if (DataBaseMethods.getCount("User", newUn, "Username", "AND user_ID != '" + getTblID() + "'") == 1) {
                    showAlert(warning, "Username Unavailable", "Username Taken.");
                    return;
                }
            }


            List<Object> newVals = Arrays.asList(txtAccountFN.getText(), txtAccountLN.getText(), cmbBoxAccountGender.getValue(), txtAccountEmail.getText()
                    , txtAccountCPNumber.getText(), txtAccountAddress.getText(),
                    (datePickerBDay.getValue() != null) ? datePickerBDay.getValue().toString() : null);

            setUserID(txtAccountID.getText());

            if (Objects.equals(getUserType(), "S"))
                DB.updateDB("Student", newUn, getUserID(), STUDENT_COLUMN_NAMES, newVals, "Student_No", "");
            else
                DB.updateDB("Guest", newUn, getUserID(), GUEST_COLUMN_NAMES, newVals, "Guest_ID", "");

            isEdit = false;
            setEditableFields(false);
            grpPass1.setVisible(false);
            grpPass2.setVisible(false);
            btnAccountSave.setVisible(false);
            btnAccDelete.setVisible(false);
            btnAccountEditCancel.setText("Edit Infos");
            refresh();
        }
    }

    public void tglEditCancel(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (!isEdit) {
            btnAccountEditCancel.setText("Cancel Editing");
            setEditableFields(true);
            grpPass1.setVisible(true);
            grpPass2.setVisible(true);
            btnAccountSave.setVisible(true);
            btnAccDelete.setVisible(true);
            isEdit = !isEdit;
        } else {
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

    private void setEditableFields(boolean editable) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        txtAccountUN.setEditable(editable);
        txtAccountFN.setEditable(editable);
        txtAccountLN.setEditable(editable);
        cmbBoxAccountGender.setDisable(!editable);
        txtAccountEmail.setEditable(editable);
        txtAccountCPNumber.setEditable(editable);
        txtAccountAddress.setEditable(editable);
        datePickerBDay.setDisable(!editable);
        if (editable) {
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
        } else {
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

    public void refresh() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DB.setBDay(getUserID(), datePickerBDay);
        DB.toText("User_ID", txtAccountID, getUserID(), null);
        DB.toText("Username", txtAccountUN, getUserID(), null);
        DB.toText("FirstName", txtAccountFN, getUserID(), null);
        DB.toText("LastName", txtAccountLN, getUserID(), null);
        DB.toCmbBox("vwUserInfo", "Gender", getUserID(), cmbBoxAccountGender);
        DB.toText("Email", txtAccountEmail, getUserID(), null);
        DB.toText("CP_Num", txtAccountCPNumber, getUserID(), null);
        DB.toText("Address", txtAccountAddress, getUserID(), null);
        setTblID(txtAccountID.getText());
        if (getUserType().equals("S")) {
            lblAccountUserType.setText("You are a Student!");
            DB.toText(txtAccountID.getText(), txtAccountCourse, getUserID(), "Course");
        } else if (getUserType().equals("G")) {
            lblAccountUserType.setText("You are a Guest!");
            txtAccountCourse.setVisible(false);
            lblAccountCourse.setVisible(false);
        }
    }



    public void openApp(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public void closeApp(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public void ExitAnimation(AnchorPane anchorPane) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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



    public void deleteAccount(MouseEvent event) {
        confirm = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType result = showAlert(confirm, "Confirm Delete?",
                "Delete Account? Your past transactions will be" +
                        " PERMANENTLY DELETED. Are you Sure?");
        if (result == ButtonType.OK) {
            DataBaseMethods.dropDB("USER", getTblID(), "User_ID");
            change("login.fxml", anchorPaneRoot, event);
        } else {
        }
    }

    public void showPass(MouseEvent event) {
        showPassword(event);
    }

    public void LogOut(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        change("login.fxml", anchorPaneRoot, event);
    }





    public void addToList(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableModels.Book selectedBook = tblVwBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            if (!selectedBookIds.contains(selectedBook)) {
                if(selectedBook.getStatus().equalsIgnoreCase("X") || selectedBook.getStatus().equalsIgnoreCase("R"))
                {
                    showAlert(info, "Error Adding", "Selected Book is not available for Borrowing.");
                    return;
                }
                selectedBookIds.add(selectedBook);
                refreshList(selectedBookIds);
            } else {
                showAlert(info, "Error Adding", "Selected Book is already in the list!");
            }
        } else showAlert(info, "Error Adding", "Please select a book!");
    }


    public void refreshList(ObservableList<TableModels.Book> selectedBookIds) {
        txtAreaList.clear();
        for (TableModels.Book Books : selectedBookIds) {
            txtAreaList.appendText("Book ID: " + Books.getBookId() + ", Title: " + Books.getDescription() + "\n\n");
        }
    }

    public void removeFromList(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        TableModels.Book selectedBook = tblVwBooks.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            if (selectedBookIds.contains(selectedBook)) {
                selectedBookIds.remove(selectedBook);
                if (selectedBookIds.isEmpty())
                    bookListener(null);
                refreshList(selectedBookIds);
            } else {
                showAlert(info, "Error Removing", "Selected Book is not in the list!");
            }
        }

    }



    public void filterTable(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var stringList = createValuesList(cmbSubject, txtSearchTitle, txtSearchAuthor,
                txtSearchPublisher, txtSearchISBN);

        DB.populateBook(tblVwBooks, createHashMap(stringList, BOOK_COL_NAMES));

    }





    public void confirm(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (selectedBookIds.isEmpty()){
            showAlert(error, "Transaction Failed", "Selected Book List is Empty!");
            return;
        }
        else {
            if (showAlert(confirm, "Confirm Transaction",
                    "Do you want to reserve these books?") == ButtonType.OK) {
                List<String> books = new ArrayList<>();
                List<String> newVals = new ArrayList<>();
                Iterator<TableModels.Book> iterator = selectedBookIds.iterator();
                String res_ID = null, sy = null, sem = null, student_no = null, book_id = null,
                        transaction_Status = null, request_DateTime = null;


                while (iterator.hasNext()) {
                    newVals.clear();
                    TableModels.Book book = iterator.next();
                    res_ID = DataBaseMethods.getNextID("Book_reservation", "Reservation_ID", 1, "");
                    sy = getCurrentSYSEM("SY");
                    sem = getCurrentSYSEM("Sem");
                    student_no = getTblID();
                    book_id = book.getBookId();
                    books.add(book_id);
                    transaction_Status = "PENDING";
                    request_DateTime = getCurrentDate().toString() + " " + getCurrentTime().toString();
                    iterator.remove();
                    newVals.addAll(Arrays.asList(res_ID, sy, sem, student_no, book_id, transaction_Status, request_DateTime));
                    DataBaseMethods.insertIntoDB("Book_Reservation", RESERVATION_COL_NAMES, newVals);
                    //add to db
                }

                refreshList(selectedBookIds);

                showInfo(info, "Transaction Confirmed", "Your Request has been received, please wait" +
                        " for approval and check your Dashboard for updates.\n\nRequested Books:\n", books);
            }
        }
    }


    public void confirmAppt(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(txtFieldDate.getText().trim().isEmpty()){
            showAlert(error, "Transaction Failed", "Please Select a Slot!");
        }
        else {
            if (showAlert(confirm, "Confirm Transaction",
                    "Do you want to reserve this Slot?") == ButtonType.OK) {
                TableModels.SlotData slot = tblVwAppointments.getSelectionModel().getSelectedItem();
                String appt_id = null, sy = null, sem = null, visitor_ID = null, slot_id = null, day = null,
                        date = null, transaction_Status = null, request_DateTime = null, time = null;
                List<String> newVals = new ArrayList<>();
                appt_id = DataBaseMethods.getNextID("Appointment", "Appointment_ID", 1, "");
                sy = getCurrentSYSEM("SY");
                sem = getCurrentSYSEM("Sem");
                visitor_ID = getTblID();
                slot_id = slot.getID();
                day = slot.getDay();
                date = txtFieldDate.getText();
                time = slot.getTimeIn();
                transaction_Status = "PENDING";
                request_DateTime = getCurrentDate().toString() + " " + getCurrentTime().toString();
                newVals.addAll(Arrays.asList(appt_id, sy, sem, visitor_ID, slot_id, day, date,
                        transaction_Status, request_DateTime));
                DataBaseMethods.insertIntoDB("Appointment", APPOINTMENT_COL_NAMES, newVals);
                showInfo(info, "Transaction Confirmed", "Your Request has been received, please wait" +
                        " for approval and check your Dashboard for updates. Appointment Date: " + date +
                        "Appointment Time: " + time);

            }
        }

    }





}
