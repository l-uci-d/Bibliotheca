package com.example.oopapplication;


import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static com.example.oopapplication.HelperMethods.*;
import static com.example.oopapplication.InputValidation.*;
import static com.example.oopapplication.DataBaseMethods.*;


public class AdminController {

    public final List<String> STUDENT_COLS = Arrays.asList("Student_No", "LastName", "FirstName", "Email",
            "Gender", "Course_Code", "CP_Num", "Address", "BDay", "Status");
    public final List<String> EMPLOYEE_COLS = Arrays.asList("Employee_ID", "LastName", "FirstName", "Email",
            "Gender",  "CP_Num", "Address", "BDay", "Status");
    public final List<String> GUEST_COLS = Arrays.asList("Guest_ID", "LastName", "FirstName", "Email",
            "Gender",  "CP_Num", "Address", "BDay", "Gender", "Status");
    public final List<String> COLLEGE_COLS = Arrays.asList("College_Code", "Description", "Date_Opened", "Date_Closed"
            , "Status");
    public final List<String> COURSE_COLS = Arrays.asList("Course_Code", "College_Code", "Description", "Date_Opened",
            "Date_Closed", "Status");

    public final List<String> BOOK_COLS = Arrays.asList("Book_ID", "Description", "Author", "Publisher", "Date_Published",
            "Price", "ISBN", "Status");
    public static final List<String> BOOK_REQS = Arrays.asList("Reservation_ID", "Book_title", "Book_ID", "Student_No",
        "User_FirstName", "User_LastName", "Request_DateTime", "Transaction_Status", "Approver_ID", "Approval_DateTime",
        "Fine", "isPaid");
    public static final List<String> PENDING_BOOK_COLS = Arrays.asList("Reservation_ID", "Book_ID", "Book_title", "Student_No",
            "User_FirstName", "User_LastName", "Request_DateTime", "Transaction_Status", "Approver_ID", "Approval_DateTime");
    public static final List<String> PENDING_APPTS_COLS = Arrays.asList("Appointment_ID", "Slot_ID", "Date", "Day", "Visitor_ID",
            "FirstName", "LastName", "Request_DateTime", "Transaction_Status", "Approver_ID", "Approval_DateTime");
    public static final List<String> CURRENT_BOOKS_COLS = Arrays.asList("Reservation_ID", "Book_ID", "Book_title", "Date_Reserved",
            "Target_Return_Date", "Student_No", "User_FirstName", "User_LastName", "Transaction_Status", "Approver_ID", "Approval_DateTime");
    public static final List<String> CURRENT_APPT_COLS = Arrays.asList("Appointment_ID", "Slot_ID", "Date", "Day",
            "Time_In", "Time_Out", "Visitor_ID", "FirstName", "LastName", "Transaction_Status", "Approver_ID", "Approval_DateTime");
    public static final List<String> PAST_BOOK_COLS = Arrays.asList("Reservation_ID", "SY", "Semester", "Student_No",
            "Book_ID", "Date_Reserved", "Date_Returned", "Target_Return_Date", "Transaction_Status", "Fine",
            "Librarian_ID", "Librarian_Remark", "Request_DateTime", "Approval_DateTime"
            , "Approver_ID", "isPaid"
    );
    public static final List<String> PAST_APPTS_COL = Arrays.asList("Appointment_ID", "SY", "Semester", "Visitor_ID",
            "Slot_ID", "Day", "Date", "Time_In", "Time_Out", "Transaction_Status",
            "Librarian_ID", "Librarian_Remark", "Request_DateTime", "Approval_DateTime"
            , "Approver_ID");

    final List<String> USER_COLS = Arrays.asList("User_ID", "User_Type", "Password");

    final List<String> COL_APPROVE = Arrays.asList("Transaction_Status", "Approver_ID", "Approval_DateTime");

    final List<String> COL_START = Arrays.asList("Transaction_Status", "Date_Reserved", "Target_Return_Date",
            "Librarian_ID");

    final List<String> COL_START_APPT = Arrays.asList("Transaction_Status", "Time_In", "Librarian_ID");
    final List<String> COL_COMPLETE = Arrays.asList("Transaction_Status", "Date_Returned", "Librarian_Remark");

    final List<String> COL_COMPLETE_APPT = Arrays.asList("Transaction_Status", "Time_Out", "Librarian_Remark");

    final List<String> EMPLOYEE_COLUMN_NAMES = Arrays.asList("FirstName", "LastName", "Gender", "Email", "CP_Num", "Address", "BDay");

    @FXML PasswordField passAccPassword, passAccPassword1;
    @FXML Group grpSetPass, grpConfirmPass, grpTargetReturnDateBook;

    @FXML TableView<TableModels.SYSEM> tblSemester, tblSY;
        @FXML TableColumn<TableModels.SYSEM, String> tblColSemester1, tblColSY1;

    @FXML TableView<TableModels.College> tblCollege;
        @FXML TableColumn<TableModels.College, String> tblColCollegeCode2, tblColCollegeDescription2,
            tblColCollegeStatus2, tblColCollegeDateOpened2, tblColCollegeDateClosed2;

    @FXML TableView<TableModels.Courses> tblCourse;
        @FXML TableColumn<TableModels.Courses, String> tblColCourseCode2, tblColCourseDescription2,
            tblColCourseCollegeCode2, tblColCourseStatus2, tblColCourseDateOpened2, tblColCourseDateClosed2;

    @FXML TableView<TableModels.Student> tblStudentList;
        @FXML TableColumn<TableModels.Student, String> tblColListStudentNo1, tblColStudentLN1, tblColStudentFN1,
                tblColStudentEmail1, tblColStudentGender1, tblColCourseCode1, tblColStudentCPNumber1,
                tblColStudentAddress1, tblColStudentBDay1, tblColStudentStatus1;

    @FXML TableView<TableModels.Employee> tblEmployeeList;
        @FXML TableColumn<TableModels.Employee, String> tblColEmployeeID2, tblColEmployeeLN2, tblColEmployeeFN2,
                tblColEmployeeEmail2, tblColEmployeeGender2, tblColEmployeeCPNumber2, tblColEmployeeAddress2,
                tblColEmployeeBDay2, tblColEmployeeStatus2;

    @FXML TableView<TableModels.Guest> tblGuestList;
        @FXML TableColumn<TableModels.Guest, String> tblColGuestID3, tblColGuestLN3, tblColGuestFN3, tblColGuestEmail3,
                tblColGuestGender3, tblColGuestCPNumber3, tblColGuestAddress3, tblColGuestBDay3, tblColGuestStatus3;

    @FXML TableView<TableModels.SlotData> tblSlot;
        @FXML TableColumn<TableModels.SlotData, String> tblColSlotID3, tblColSlotDay3, tblColSlotTimeIn3,
                tblColSlotTimeOut3;
        @FXML TableColumn<TableModels.SlotData, Integer> tblColSlotLimit3;

    @FXML TableView<TableModels.Book> tblBookList;
        @FXML TableColumn<TableModels.Book, String> tblColBookID4, tblColDesc4, tblColAuthor4, tblColPublisher4,
                tblColDatePublished4, tblColISBN4, tblColStatus4;
        @FXML TableColumn<TableModels.Book, Double> tblColPrice4;

    @FXML TableView<TableModels.PendingBooks> tblBookReservations;
        @FXML TableColumn<TableModels.PendingBooks, String> tblColReservationID1, tblColBookID1,
                tblColBookTitle1, tblColStudentNo1, tblColBookFN1, tblColBookLN1, tblColReqDate1,
                tblColReqStatus1, tblColBookApprover1, tblColBookApproveTime1;

    @FXML TableView<TableModels.PendingAppts> tblApptReservation;       //tblApptReservations
        @FXML TableColumn<TableModels.PendingAppts, String> tblColApptID1, tblColSlotID1, tblColDate1, tblColDay1,
                tblColVisitorID1, tblColApptFN1, tblColApptLN1, tblColApptReqDate1, tblColApptReqStatus1,
                tblColApptApprover1, tblColApptApproveTime1;

    @FXML TableView<TableModels.PastBooks> tblPastBooks;
        @FXML TableColumn<TableModels.PastBooks, String> tblColReservationID3, tblColBookSY3, tblColBookSemester3,
                tblColStudentNo3, tblColBookID3, tblColDateReserved3, tblColBookDateReturned3, tblColTargetDate3,
                tblColReservation_Status3, tblColBookLibrarian3, tblColBookRemark3, tblColBookRequestTime3,
                tblColBookApprovalTime3, tblColBookApproverID3, tblColBookFine3, tblColBookPaid;

    @FXML TableView<TableModels.CurrentBooks> tblCurrentBooks;
        @FXML TableColumn<TableModels.CurrentBooks, String> tblColReservationID2, tblColBookID2, tblColBookTitle2,
                tblColDateReserved2, tblColTargetDate2, tblColStudentNo2, tblColBookFN2, tblColBookLN2,
                tblColBookStatus2, tblColBookApproverID2, tblColBookApproval2;

    @FXML TableView<TableModels.CurrentAppts> tblCurrentAppts;
        @FXML TableColumn<TableModels.CurrentAppts, String> tblColApptID2, tblColSlotID2,
                tblColDate2, tblColDay2, tblColTimeIn2, tblColTimeOut2, tblColVisitorID2,
                tblColApptFN2, tblColApptLN2, tblColApptStatus2, tblColApptApproverID2, tblColApptApproval2;
    @FXML TableView<TableModels.PastAppts> tblPastAppts;
        @FXML TableColumn<TableModels.PastAppts, String> tblColApptID3, tblColApptSY3, tblColApptSem3,
                tblColVisitorID3, tblColSlotID, tblColApptDay3, tblColDate3, tblColTimeIn3, tblColTimeOut3, tblColApptStatus3, tblColApptLibrarian3, tblColApptRemark3, tblColApptRequestDate3,
                tblColApptApprovalTime3, tblColApptApproverID3;

    @FXML private AnchorPane
            anchorPaneRoot, anchorPaneTaskbar, anchorPaneBG,
            anchorPaneMiniBooks, anchorPaneMiniPeople, anchorPaneMiniReality, anchorPaneMiniAccount,
            anchorPaneBooks, anchorPanePeople, anchorPaneReality, anchorPaneAccount;

    @FXML private Button
            btnApptApprove1,btnBookApprove1, btnBookDecline1, btnApptDecline1,
            btnLogOut, btnStudentListAdd1, btnStudentListSearch1, btnStudentListDelete1, btnStudentListEdit1,
            btnCollegeAdd2, btnCollegeEdit2, btnCollegeDelete2,
            btnCourseAdd2, btnCourseEdit2, btnCourseDelete2,
            btnSlotAdd3, btnSlotEdit3, btnSlotDelete3,
            btnSetDate, btnSetTime,
            btnAccountEdit, btnAccountCancel, btnAccountSave, btnXBooks, btnXPeople, btnXAccount, btnXReality;

    @FXML private TabPane
            tabPaneBooks, tabPanePeople, tabPaneReality, tabPaneAccount;

    @FXML private Label
            lblEmployeeListSelectedID2, lblPastBookSelectedID, lblGuestListSelectedID3,
            lblSelectedCollegeCodeX,
            lblSelectedSem1,
            lblSelectedSY1,
            lblBookSelectedID2, lblBookSelectedID2X,
            lblClock, lblDate, lblCurrentDate, lblCurrentTime, lblCurrentDay,
            lblPassword, lblPassword1, lblShow, lblShow1, lblAccountPassWarning,
            lblBookSelectedID4, lblSelectedID3, lblStudentListSelectedID1,
            lblApptSelectedID1, lblBookSelectedID1,
            lblBookSelectedID1X, lblApptSelectedID1X,
            lblApptSelectedID2X, lblApptSelectedID2,
            lblApptSelectedID3X, lblBookSelectedID3X, lblBookSelectedID4X,
            lblAccountAddress, lblAccountBirthday, lblAccountCPNumber, lblAccountEmail, lblAccountFN, lblAccountGender, lblAccountID,
            lblAccountLN, lblAccountUN;
    @FXML private TextField
            txtBookRemark, txtBookFine, txtApptRemark, txtSlotAvailableSlot,
            txtAccPassword1, txtAccPassword, txtCourseCode3, txtCourseDescription3,
            txtFieldBookDesc1, txtFieldBookAuth1, txtFieldBookPub1, txtFieldBookPrice1, txtFieldBookISBN1,
            txtUsername4, txtUserLN4, txtUserFN4,
            txtStudentListLN1, txtStudentListFN1, txtStudentListEmail1, txtStudentListCPNumber1, txtStudentListAddress1,
            txtSlotTimeOut3, txtSlotTimeIn3, txtSetTime,
            txtSelectedSY1, txtSelectedSEM1,
            txtGuestLN3, txtGuestFN3, txtGuestEmail3, txtGuestCPNumber3, txtGuestAddress3,
            txtFieldBookAuth, txtEmployeeLN2, txtEmployeeListAddress2, txtEmployeeFN2, txtEmployeeEmail2, txtEmployeeCPNumber2,
            txtCollegeCode2, txtCollegeDescription2,
            txtAccountID, txtAccountUN, txtAccountFN, txtAccountLN, txtAccountEmail, txtAccountCPNumber,
            txtAccountAddress;

    @FXML private ComboBox<String> cmbBoxAccountGender, cmbBoxBookStatus1, cmbBoxCollegeStatus2, cmbBoxGuestStatus3,
            cmbBoxCourseStatus2, cmbBoxEmployeeGender2, cmbBoxGuestGender3, cmbBoxStudentListCourseCode1,
            cmbBoxStudentListStatus1, cmbBoxUserType4, combBoxStudentListGender1, comboBoxEmployeeStatus2,
            cmbBoxCourseStatus3, cmbBoxCollegeCode3, cmbBoxBookSubject;
            ;


    @FXML private DatePicker datePickerBook, datePickerEmployeeBirthday2, datePickerGuestBirthday3,
            datePickerCollegeDateOpened2, datePickerCollegeDateClosed2,
            datePickerBirthday, txtSetDate, datePickerBookDatePublished1, datePickerStudentListBirthday1
            , datePickerCourseDateOpened3, datePickerCourseDateClosed3

            ;
    @FXML private TextArea
            terminalSemSY, terminalCollege, terminalCourses;    private String userID = getUserID();
    private final String[] statusBooks = {"A", "R", "X"};
    private final String[] status = {"A", "I"};
    private final String[] gender = {"M", "F"};

    DataBaseMethods DB = new DataBaseMethods();

    public String tblID = getTblID();
    public String getTblID() {
        return this.tblID;
    }

    public void setTblID(String tblID) {
        this.tblID = tblID;
    }
    public ObservableList<TableModels.Student> studentList = FXCollections.observableArrayList();
    public ObservableList<TableModels.Book> bookList = FXCollections.observableArrayList();
    public ObservableList<TableModels.Employee> employeeList = FXCollections.observableArrayList();
    public ObservableList<TableModels.Guest> guestList = FXCollections.observableArrayList();
    public ObservableList<TableModels.College> collegeDataList = FXCollections.observableArrayList();
    public ObservableList<TableModels.PendingBooks> pendingBooksList = FXCollections.observableArrayList();
    public ObservableList<TableModels.PendingAppts> pendingApptsList = FXCollections.observableArrayList();
    public ObservableList<TableModels.PastBooks> pastBooksList = FXCollections.observableArrayList();
    public ObservableList<TableModels.PastAppts> pastApptsList = FXCollections.observableArrayList();

    public ObservableList<TableModels.Courses> courseDataList = FXCollections.observableArrayList();

    public ObservableList<TableModels.CurrentBooks> currentBooksList = FXCollections.observableArrayList();
    public ObservableList<TableModels.CurrentAppts> currentApptsList = FXCollections.observableArrayList();
    public void refreshCourse(){
        populate("Course", COURSE_COLS, courseDataList, tblCourse,
                TableModels.Courses.class, "");
        populateCmbBox("College", "College_Code", cmbBoxCollegeCode3);
        populateCmbBox("College", "Status", cmbBoxCourseStatus3);
    }
    public void refreshCollege(){
        populate("College", COLLEGE_COLS, collegeDataList, tblCollege,
                TableModels.College.class, "");
        populateCmbBox("College", "Status", cmbBoxCollegeStatus2);
    }
    public void refreshPendingBooks(){
        populate("vwPendingBookRequests", PENDING_BOOK_COLS, pendingBooksList, tblBookReservations,
                TableModels.PendingBooks.class, " order by Transaction_Status");
    }

    public void refreshPendingAppts(){
        populate("vwPendingAppointments", PENDING_APPTS_COLS, pendingApptsList, tblApptReservation,
                TableModels.PendingAppts.class, " order by Transaction_Status");
    }

    public void refreshCurrentBooks(){
        populate("vwCurrentBookRequests", CURRENT_BOOKS_COLS, currentBooksList, tblCurrentBooks,
                TableModels.CurrentBooks.class, " WHERE Transaction_Status LIKE 'ongoing%';");
    }

    public void refreshCurrentAppts(){
        populate("vwCurrentAppointments", CURRENT_APPT_COLS, currentApptsList, tblCurrentAppts
                , TableModels.CurrentAppts.class, "");
    }
    public void refreshPastBooks(){
        populate("Book_Reservation", PAST_BOOK_COLS, pastBooksList, tblPastBooks, TableModels.PastBooks.class
                , " Where Transaction_Status like 'complete%' or Transaction_Status = 'declined';");
    }

    public void refreshPastAppts(){
        populate("Appointment", PAST_APPTS_COL, pastApptsList, tblPastAppts, TableModels.PastAppts.class
                , " Where Transaction_Status like 'complete%' or Transaction_Status = 'declined';");
    }
    public void refreshEmployee() {
        populate("Employee", EMPLOYEE_COLS, employeeList, tblEmployeeList, TableModels.Employee.class, "");
        cmbBoxEmployeeGender2.getItems().clear();
        comboBoxEmployeeStatus2.getItems().clear();
        populateCmbBox("Employee","Gender",cmbBoxEmployeeGender2);
        populateCmbBox("Employee","Status",comboBoxEmployeeStatus2);
    }
    public void refreshSYSem() {
        DB.populateSYSEM(tblSemester, "Semester");
        DB.populateSYSEM(tblSY, "SY");
    }

    public void refreshSlot(){
        DB.populateSlot(tblSlot, 1);
    }

    private void refreshStudent(){
        DataBaseMethods.populate("Student", STUDENT_COLS, studentList, tblStudentList,
                TableModels.Student.class, "");
    }
    private void refreshGuest(){
        DB.populateGuests(tblGuestList);
        cmbBoxGuestGender3.getItems().clear();
        cmbBoxGuestStatus3.getItems().clear();
        populateCmbBox("Guest", "Gender", cmbBoxGuestGender3);
        populateCmbBox("Guest", "Status", cmbBoxGuestStatus3);
    }

    private void refreshBook(){
        DB.populateBook(tblBookList, null);
        cmbBoxBookSubject.getItems().clear();
        populateCmbBox("Book", "LEFT(Book_ID, 3)",cmbBoxBookSubject );
    }

    public String currentSY = getCurrentSYSEM("SY"),
            currentSem = getCurrentSYSEM("Sem");




    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
        refreshAccount();
    }

    public String getCurrentSYSEM(String choice) {
        if (choice.equals("Sem"))
            return this.currentSem;
        else
            return this.currentSY;
    }

    public void setCurrentSYSEM() {
        this.currentSem = DB.currentSYSEM("Sem");
        lblSelectedSem1.setText(this.currentSem);
        this.currentSY = DB.currentSYSEM("SY");
        lblSelectedSY1.setText(this.currentSY);
    }

    public void initialize() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DataBaseMethods.populateCmbBox("Student", "Course_Code", cmbBoxStudentListCourseCode1);
        cmbBoxBookStatus1.getItems().addAll(statusBooks);
        cmbBoxAccountGender.getItems().addAll(gender);
        combBoxStudentListGender1.getItems().addAll(gender);
        cmbBoxStudentListStatus1.getItems().addAll(status);

        if(getCurrentDateTime() == null)
            setCurrentDateTime(null);
        setCurrentSYSEM();


        //tblSemester, tblSY        List of Semesters/SY        TableModels.SYSEM
        tblColSemester1.setCellValueFactory(cellData -> cellData.getValue().sysemProperty());
        tblColSY1.setCellValueFactory(cellData -> cellData.getValue().sysemProperty());

        //populate
        refreshSYSem();
        //listener
        tblSemester.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> semListener(newValue));
        tblSY.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> syListener(newValue));

        //tblCollege                List of Colleges                TableModels.College
        tblColCollegeCode2.setCellValueFactory(cellData -> cellData.getValue().getCollegeCodeProperty());
        tblColCollegeDescription2.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        tblColCollegeStatus2.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        tblColCollegeDateOpened2.setCellValueFactory(celLData -> celLData.getValue().getDateOpenedProperty());
        tblColCollegeDateClosed2.setCellValueFactory(celLData -> celLData.getValue().getDateClosedProperty());

        //cell + column wrap
        TableModels.textWrap(tblColCollegeDescription2);

        //populate
//        final  List<String> COLLEGE_COLS = Arrays.asList("College_Code", "Description", "Date_Opened", "Date_Closed"
//        , "Status");
        //ObservableList<TableModels.College> collegeDataList = FXCollections.observableArrayList();
        refreshCollege();

        //listener
        tblCollege.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> collegeListener(newValue));

        //tblCourse                 List of Courses             TableModels.Courses     extends College
        tblColCourseCode2.setCellValueFactory(cellData -> cellData.getValue().getCourseCodeProperty());
        tblColCourseDescription2.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        tblColCourseCollegeCode2.setCellValueFactory(cellData -> cellData.getValue().getCollegeCodeProperty());
        tblColCourseStatus2.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());
        tblColCourseDateOpened2.setCellValueFactory(celLData -> celLData.getValue().getDateOpenedProperty());
        tblColCourseDateClosed2.setCellValueFactory(celLData -> celLData.getValue().getDateClosedProperty());

        //cell + column wraps
        TableModels.textWrap(tblColCourseDescription2);

        //populate
//        final  List<String> COURSE_COLS = Arrays.asList("Course_Code", "College_Code", "Description", "Date_Opened",
//                "Date_Closed" , "Status");
        //ObservableList<TableModels.Courses> courseDataList = FXCollections.observableArrayList();
        refreshCourse();

        //listener
        tblCourse.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> courseListener(newValue));

        //tblStudentList            List of Students            TableModels.Student   extends PeopleInfo
        tblColListStudentNo1.setCellValueFactory(cellData -> cellData.getValue().getStudentNoProperty());
        tblColStudentLN1.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColStudentFN1.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColStudentEmail1.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        tblColStudentGender1.setCellValueFactory(cellData -> cellData.getValue().getGenderProperty());
        tblColCourseCode1.setCellValueFactory(cellData -> cellData.getValue().getCourseCodeProperty());
        tblColStudentCPNumber1.setCellValueFactory(cellData -> cellData.getValue().getCpNumberProperty());
        tblColStudentAddress1.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        tblColStudentBDay1.setCellValueFactory(cellData -> cellData.getValue().getBDayProperty());
        tblColStudentStatus1.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        //cell + column wraps
        TableModels.textWrap(tblColStudentAddress1);

        //populate
//        final  List<String> STUDENT_COLS = Arrays.asList("Student_No", "LastName", "FirstName", "Email",
//                "Gender" , "Course_Code", "CP_Num", "Address", "BDay", "Status");
        //ObservableList<TableModels.Student> studentList = FXCollections.observableArrayList();
        refreshStudent();


        //listener
        tblStudentList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> studentListener(newValue));


        //tblEmployeeList           List of Employees             TableModels.Employee  extends PeopleInfo
        tblColEmployeeID2.setCellValueFactory(cellData -> cellData.getValue().getEmployeeIDProperty());
        tblColEmployeeLN2.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColEmployeeFN2.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColEmployeeEmail2.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        tblColEmployeeGender2.setCellValueFactory(cellData -> cellData.getValue().getGenderProperty());
        tblColEmployeeCPNumber2.setCellValueFactory(cellData -> cellData.getValue().getCpNumberProperty());
        tblColEmployeeAddress2.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        tblColEmployeeBDay2.setCellValueFactory(cellData -> cellData.getValue().getBDayProperty());
        tblColEmployeeStatus2.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        //cell + column wraps
        TableModels.textWrap(tblColEmployeeAddress2);

        //populate
        refreshEmployee();

        //listener
        tblEmployeeList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> employeeListener(newValue));

        //tblGuestList              List of Guests          TableModels.Guest   extends PeopleInfo
        tblColGuestID3.setCellValueFactory(cellData -> cellData.getValue().getGuestIDProperty());
        tblColGuestLN3.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColGuestFN3.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColGuestEmail3.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
        tblColGuestGender3.setCellValueFactory(cellData -> cellData.getValue().getGenderProperty());
        tblColGuestCPNumber3.setCellValueFactory(cellData -> cellData.getValue().getCpNumberProperty());
        tblColGuestAddress3.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        tblColGuestBDay3.setCellValueFactory(cellData -> cellData.getValue().getBDayProperty());
        tblColGuestStatus3.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        //cell + column wraps
        TableModels.textWrap(tblColGuestAddress3);

        //populate
        refreshGuest();


        //listener
        tblGuestList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> guestListener(newValue));

        //tblSlot                   Slot Infos              TableModels.SlotData
        tblColSlotID3.setCellValueFactory(cellData -> cellData.getValue().getSlotIDProperty());
        tblColSlotDay3.setCellValueFactory(cellData -> cellData.getValue().getDayProperty());
        tblColSlotTimeIn3.setCellValueFactory(cellData -> cellData.getValue().getTimeInProperty());
        tblColSlotTimeOut3.setCellValueFactory(cellData -> cellData.getValue().getTimeOutProperty());
        tblColSlotLimit3.setCellValueFactory(cellData -> cellData.getValue().getSlotLimitProperty().asObject());

        //cell + column wraps

        //populate
        refreshSlot();

        //listener
        tblSlot.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> slotListener(newValue));


        //tblBookList           List of Books                   TableModels.Book
        tblColBookID4.setCellValueFactory(cellData -> cellData.getValue().getBookIDProperty());
        tblColDesc4.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        tblColAuthor4.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
        tblColPublisher4.setCellValueFactory(cellData -> cellData.getValue().getPublisherProperty());
        tblColDatePublished4.setCellValueFactory(cellData -> cellData.getValue().getDatePublishedProperty());
        tblColPrice4.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty().asObject());
        tblColISBN4.setCellValueFactory(cellData -> cellData.getValue().getIsbnProperty());
        tblColStatus4.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        //cell + column wraps
        TableModels.textWrap(tblColPublisher4);
        TableModels.textWrap(tblColAuthor4);
        TableModels.textWrap(tblColDesc4);

        //populate
        refreshBook();

        //listener
        tblBookList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> bookListener(newValue));


        //tblBookReservations       Pending Book Reservations       TableModels.PendingBooks
        tblColReservationID1.setCellValueFactory(cellData -> cellData.getValue().getReservationIDProperty());
        tblColBookID1.setCellValueFactory(cellData -> cellData.getValue().getBookIDProperty());
        tblColBookTitle1.setCellValueFactory(cellData -> cellData.getValue().getBookTitleProperty());
        tblColReqStatus1.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());
        tblColStudentNo1.setCellValueFactory(cellData -> cellData.getValue().getStudent_NoProperty());
        tblColBookFN1.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColBookLN1.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColReqDate1.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());

        tblColBookApprover1.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getApprover_IDProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColBookApproveTime1.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getApprover_DateTimePropertyBook();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        //cell + column wraps
        TableModels.wrapColumnHeader(tblColReservationID1);
        TableModels.wrapColumnHeader(tblColReqStatus1);
        TableModels.wrapColumnHeader(tblColReqDate1);
        TableModels.wrapColumnHeader(tblColStudentNo1);
        TableModels.wrapColumnHeader(tblColBookApproveTime1);
        TableModels.textWrap(tblColBookTitle1);
        TableModels.textWrap(tblColBookApproveTime1);

        //populate
        //      final  List<String> BOOK_REQS_COLS
        //ObservableList<TableModels.PendingBooks> pendingBooks = FXCollections.observableArrayList();
        refreshPendingBooks();

        //listener
        tblBookReservations.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pendingBooksListener(newValue));


        //tblApptReservation        Pending Appointments            TableModels.PendingAppts
        tblColApptID1.setCellValueFactory(cellData -> cellData.getValue().getApptIDProperty());
        tblColSlotID1.setCellValueFactory(cellData -> cellData.getValue().getSlotIDProperty());
        tblColDay1.setCellValueFactory(cellData -> cellData.getValue().getDayProperty());
        tblColDate1.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        tblColVisitorID1.setCellValueFactory(cellData -> cellData.getValue().getVisitor_IDProperty());
        tblColApptFN1.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColApptLN1.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColApptReqDate1.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
        tblColApptReqStatus1.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());
        tblColApptApprover1.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getApprover_IDPropertyAppts();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });
        tblColApptApproveTime1.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getApproval_DateTimeProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });
        //cell + column wraps
        TableModels.wrapColumnHeader(tblColApptApproveTime1);
        TableModels.wrapColumnHeader(tblColApptReqStatus1);
        TableModels.wrapColumnHeader(tblColVisitorID1);
        TableModels.wrapColumnHeader(tblColApptReqDate1);
        TableModels.textWrap(tblColBookTitle2);
        TableModels.textWrap(tblColBookTitle2);
        TableModels.textWrap(tblColBookTitle2);


        tblApptReservation.getColumns().forEach(column -> column.setReorderable(false));

        //populate
        //        final  List<String> PENDING_APPTS_COLS
        //        ObservableList<TableModels.PendingAppts> pendingAppts = FXCollections.observableArrayList();
        refreshPendingAppts();

        //listener
        tblApptReservation.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pendingApptsListener(newValue));


        //tblCurrentBooks           Currently Borrowed Books            TableModels.Book
        tblColReservationID2.setCellValueFactory(cellData -> cellData.getValue().getReservationIDProperty());
        tblColBookID2.setCellValueFactory(cellData -> cellData.getValue().getBookIDProperty());
        tblColBookTitle2.setCellValueFactory(cellData -> cellData.getValue().getBookTitleProperty());
        tblColDateReserved2.setCellValueFactory(cellData -> cellData.getValue().getDate_ReservedProperty());
        tblColTargetDate2.setCellValueFactory(cellData -> cellData.getValue().getTarget_Return_DateProperty());
        tblColStudentNo2.setCellValueFactory(cellData -> cellData.getValue().getStudent_NoProperty());
        tblColBookFN2.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColBookLN2.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColBookStatus2.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());
        tblColBookApproverID2.setCellValueFactory(cellData -> cellData.getValue().getApprover_IDProperty());
        tblColBookApproval2.setCellValueFactory(cellData -> cellData.getValue().getApprover_DateTimePropertyBook());

        //cell + column wraps
        TableModels.wrapColumnHeader(tblColReservationID2);
        TableModels.wrapColumnHeader(tblColDateReserved2);
        TableModels.wrapColumnHeader(tblColTargetDate2);
        TableModels.wrapColumnHeader(tblColStudentNo2);
        TableModels.wrapColumnHeader(tblColBookApproval2);
        TableModels.textWrap(tblColBookTitle2);
        TableModels.textWrap(tblColBookStatus2);
        TableModels.textWrap(tblColBookFN2);
        tblCurrentBooks.getColumns().forEach(column -> column.setReorderable(false));


        //populate
        //        final  List<String> CURRENT_BOOKS_COLS
        //        ObservableList<TableModels.CurrentBooks> currentBooks = FXCollections.observableArrayList();
        refreshCurrentBooks();

        //listener
        tblCurrentBooks.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> currentBooksListener(newValue));


        //tblCurrentAppts           Ongoing Appointments            TableModels.CurrentAppts
        tblColApptID2.setCellValueFactory(cellData -> cellData.getValue().getApptIDProperty());
        tblColSlotID2.setCellValueFactory(cellData -> cellData.getValue().getSlotIDProperty());
        tblColDate2.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        tblColDay2.setCellValueFactory(cellData -> cellData.getValue().getDayProperty());
        tblColTimeIn2.setCellValueFactory(cellData -> cellData.getValue().getTime_InProperty());
        tblColTimeOut2.setCellValueFactory(cellData -> cellData.getValue().getTime_OutProperty());
        tblColVisitorID2.setCellValueFactory(cellData -> cellData.getValue().getVisitor_IDProperty());
        tblColApptFN2.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColApptLN2.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColApptStatus2.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());
        tblColApptApproverID2.setCellValueFactory(cellData -> cellData.getValue().getApprover_IDPropertyAppts());
        tblColApptApproval2.setCellValueFactory(cellData -> cellData.getValue().getApprover_DateTimeProperty());
        //cell + column wraps
        TableModels.textWrap(tblColApptApproval2);
        TableModels.textWrap(tblColDate2);
        TableModels.textWrap(tblColApptStatus2);
        TableModels.wrapColumnHeader(tblColApptApproval2);

        //populate
        //        final  List<String> CURRENT_APPT_COLS
        //        ObservableList<TableModels.CurrentAppts> currentAppts = FXCollections.observableArrayList();
        refreshCurrentAppts();


        //listener
        tblCurrentAppts.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> currentApptsListener(newValue));


        //tblPastBooks              Past Book Transactions              TableModels.PastBooks extends PendingBooks
        tblColReservationID3.setCellValueFactory(cellData -> cellData.getValue().getReservationIDProperty());
        tblColBookSY3.setCellValueFactory(cellData -> cellData.getValue().getSYProperty());
        tblColBookSemester3.setCellValueFactory(cellData -> cellData.getValue().getSemesterProperty());
        tblColStudentNo3.setCellValueFactory(cellData -> cellData.getValue().getStudent_NoProperty());
        tblColBookID3.setCellValueFactory(cellData -> cellData.getValue().getBookIDProperty());
        tblColBookFine3.setCellValueFactory(cellData -> cellData.getValue().getFineProperty());

        tblColDateReserved3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getDate_ReservedProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColBookDateReturned3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getDate_ReturnedProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColTargetDate3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getTarget_Return_DateProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColReservation_Status3.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());

        tblColBookLibrarian3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getLibrarian_IDProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColBookRemark3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getLibrarian_RemarkProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColBookRequestTime3.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
        tblColBookApprovalTime3.setCellValueFactory(cellData -> cellData.getValue().getApprover_DateTimePropertyBook());
        tblColBookApproverID3.setCellValueFactory(cellData -> cellData.getValue().getApprover_IDProperty());

        tblColBookPaid.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getisPaidProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        //cell + column wraps
        TableModels.wrapColumnHeader(tblColReservationID3);
        TableModels.wrapColumnHeader(tblColBookSemester3);
        TableModels.wrapColumnHeader(tblColStudentNo3);
        TableModels.wrapColumnHeader(tblColDateReserved3);
        TableModels.wrapColumnHeader(tblColBookDateReturned3);
        TableModels.wrapColumnHeader(tblColTargetDate3);
        TableModels.wrapColumnHeader(tblColReservation_Status3);
        TableModels.wrapColumnHeader(tblColBookLibrarian3);
        TableModels.wrapColumnHeader(tblColBookRemark3);
        TableModels.wrapColumnHeader(tblColBookRequestTime3);
        TableModels.wrapColumnHeader(tblColBookApprovalTime3);
        TableModels.wrapColumnHeader(tblColBookApproverID3);
        TableModels.wrapColumnHeader(tblColBookPaid);
        TableModels.wrapColumnHeader(tblColBookFine3);
        TableModels.textWrap(tblColBookSY3);
        TableModels.textWrap(tblColBookRemark3);
        TableModels.textWrap(tblColBookRequestTime3);
        TableModels.textWrap(tblColBookApprovalTime3);
        TableModels.textWrap(tblColBookFine3);
        TableModels.textWrap(tblColBookPaid);
        tblPastBooks.getColumns().forEach(column -> column.setReorderable(false));


        //populate
//        final  List<String> PAST_BOOK_COLS
//        ObservableList<TableModels.PastBooks> pastBooksList = FXCollections.observableArrayList();
        refreshPastBooks();

        //listener
        tblPastBooks.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> pastBooksListener(newValue));

        //tblPastAppts              Past Appointments           TableModels.PastAppts   extends CurrentAppts
        tblColApptID3.setCellValueFactory(cellData -> cellData.getValue().getApptIDProperty());
        tblColApptSY3.setCellValueFactory(cellData -> cellData.getValue().getSYProperty());
        tblColApptSem3.setCellValueFactory(cellData -> cellData.getValue().getSemesterProperty());
        tblColVisitorID3.setCellValueFactory(cellData -> cellData.getValue().getVisitor_IDProperty());
        tblColSlotID.setCellValueFactory(cellData -> cellData.getValue().getSlotIDProperty());
        tblColApptDay3.setCellValueFactory(cellData -> cellData.getValue().getDayProperty());
        tblColDate3.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());

        tblColTimeIn3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getTime_InProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColTimeOut3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getTime_OutProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColApptStatus3.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());

        tblColApptLibrarian3.setCellValueFactory(cellData -> {
            StringProperty property = cellData.getValue().getLibrarian_IDProperty();
            return new SimpleStringProperty(property.get() != null ? property.get() : "N/A");
        });

        tblColApptRemark3.setCellValueFactory(cellData -> cellData.getValue().getLibrarian_RemarkProperty());
        tblColApptRequestDate3.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
        tblColApptApprovalTime3.setCellValueFactory(cellData -> cellData.getValue().getApproval_DateTimeProperty());
        tblColApptApproverID3.setCellValueFactory(cellData -> cellData.getValue().getApprover_IDProperty());

        //cell + column wraps
        TableModels.wrapColumnHeader(tblColApptID3);
        TableModels.wrapColumnHeader(tblColApptSem3);
        TableModels.wrapColumnHeader(tblColVisitorID3);
        TableModels.wrapColumnHeader(tblColApptLibrarian3);
        TableModels.wrapColumnHeader(tblColApptRemark3);
        TableModels.wrapColumnHeader(tblColApptRequestDate3);
        TableModels.wrapColumnHeader(tblColApptApprovalTime3);
        TableModels.wrapColumnHeader(tblColApptApproverID3);
        TableModels.textWrap(tblColApptSY3);
        TableModels.textWrap(tblColApptRequestDate3);
        TableModels.textWrap(tblColApptApprovalTime3);
        TableModels.textWrap(tblColApptLibrarian3);
        TableModels.textWrap(tblColApptStatus3);
        TableModels.textWrap(tblColDate3);
        TableModels.textWrap(tblColTimeIn3);
        TableModels.textWrap(tblColTimeOut3);

        //populate
//        final  List<String> PAST_APPTS_COL
        refreshPastAppts();



        //listener

        //set user data
        anchorPaneMiniPeople.setUserData(anchorPanePeople);
        btnXPeople.setUserData(anchorPanePeople);
        anchorPanePeople.setUserData(anchorPaneMiniPeople);

        anchorPaneMiniBooks.setUserData(anchorPaneBooks);
        btnXBooks.setUserData(anchorPaneBooks);
        anchorPaneBooks.setUserData(anchorPaneMiniBooks);

        anchorPaneMiniReality.setUserData(anchorPaneReality);
        btnXReality.setUserData(anchorPaneReality);
        anchorPaneReality.setUserData(anchorPaneMiniReality);

        anchorPaneMiniAccount.setUserData(anchorPaneAccount);
        btnXAccount.setUserData(anchorPaneAccount);
        anchorPaneAccount.setUserData(anchorPaneMiniAccount);
        EnterAnimation(anchorPaneAccount);

        lblShow1.setUserData(txtAccPassword1);
        txtAccPassword1.setUserData(passAccPassword1);

        lblShow.setUserData(txtAccPassword);
        txtAccPassword.setUserData(passAccPassword);


        //update time and date labels
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event ->
                updateDateTimeLabels(lblClock, lblDate, null, getCurrentDateTime())));
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event ->
                updateDateTimeLabels(lblCurrentTime, lblCurrentDate, lblCurrentDay, getCurrentDateTime())));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
        timeline.play();

    }



    public void courseListener(TableModels.Courses currentCourses) {
        if (currentCourses != null) {
            txtCourseCode3.setText(currentCourses.getCourseCode());
            txtCourseCode3.setEditable(false);
            cmbBoxCourseStatus3.setValue(currentCourses.getStatus());
            cmbBoxCollegeCode3.setValue(currentCourses.getCollegeCode());
            txtCourseDescription3.setText(currentCourses.getDescription());
            datePickerCourseDateOpened3.setValue(LocalDate.parse(currentCourses.getDateOpened()));
            datePickerCourseDateClosed3.setValue(LocalDate.parse(currentCourses.getDateClosed()));

        } else {
            tblCourse.getSelectionModel().clearSelection();
            txtCourseCode3.clear();
            txtCourseCode3.setEditable(true);
            cmbBoxCourseStatus3.setValue(null);
            cmbBoxCollegeCode3.setValue(null);
            txtCourseDescription3.clear();
            datePickerCourseDateOpened3.setValue(null);
            datePickerCourseDateClosed3.setValue(null);

        }
    }
    public void clearCourseListener(){ courseListener(null);}

    public void addCourse(){
        TableModels.Courses course = tblCourse.getSelectionModel().getSelectedItem();
        if(course == null){
            try {
                List<String> newCourseVal = validateCourse(null);
                if(showAlert(confirm, "Add Course", "Are you sure you want to add " +
                        newCourseVal.get(0) + "?") == ButtonType.OK) {
                    insertIntoDB("Course", COURSE_COLS, newCourseVal);
                    showInfo(info, "Successfully Added Course", "Successfully Added new Course.");
                    refreshCourse();
                }
            } catch (Exception e) {
                return;
            }
        }else showInfo(warning, "Error Adding Course", "Please clear selection" +
                " first.");
    }
    public void editCourse(){
        TableModels.Courses course = tblCourse.getSelectionModel().getSelectedItem();
        if(course == null){
            List<String> oldCourseVal = validateCourse(course);
            if(showAlert(confirm, "Edit Course", "Are you sure you want to edit the information of " +
                    oldCourseVal.get(0) + "?") == ButtonType.OK){
                try {
                    List<String> newCourseVal = validateCourse(null);
                    if (!Objects.equals(oldCourseVal, newCourseVal)) {
                        updateDB("College", COLLEGE_COLS, newCourseVal, "College_Code", oldCourseVal.get(0), "");
                        showInfo(info, "Successfully Edited College", "Successfully Edited new College Code.");
                        refreshCourse();
                    } else showInfo(info, "Error Editing", "No Edits Detected.");
                } catch (Exception e) {
                    return;
                }
            }
        }else showInfo(warning, "Error Editing College", "Please select a record" +
                " first.");
    }
    public void deleteCourse(){
        TableModels.Courses course = tblCourse.getSelectionModel().getSelectedItem();
        if(course != null){
            if(showAlert(confirm, "Delete Course", "Are you sure you want to delete " +
                    "Course Code " + course.getCollegeCode() + "? This will also delete " +
                    "any students under it.") == ButtonType.OK){
                dropDB("College", course.getCollegeCode(), "College_code");
                refreshCourse();
            }
            else return;
        }else showInfo(warning, "Error Deleting Course", "Please select a record" +
                " first.");
    }

    public void collegeListener(TableModels.College currentCollege) {
        if (currentCollege != null) {
            txtCollegeCode2.setText(currentCollege.getCollegeCode());
            txtCollegeDescription2.setText(currentCollege.getDescription());
            cmbBoxCollegeStatus2.setValue(currentCollege.getStatus());
            datePickerCollegeDateClosed2.setValue(LocalDate.parse(currentCollege.getDateClosed()));
            datePickerCollegeDateOpened2.setValue(LocalDate.parse(currentCollege.getDateOpened()));
        } else {
            tblCollege.getSelectionModel().clearSelection();
            txtCollegeCode2.clear();
            txtCollegeDescription2.clear();
            cmbBoxCollegeStatus2.setValue(null);
            datePickerCollegeDateClosed2.setValue(null);
            datePickerCollegeDateOpened2.setValue(null);
        }
    }
    public void clearCollegeListener(){ collegeListener(null);}

    public void addCollege(){
        TableModels.College college = tblCollege.getSelectionModel().getSelectedItem();
        if(college == null){
                try {
                    List<String> newCollegeVal = validateCollege(null);
                    if(showAlert(confirm, "Add College", "Are you sure you want to add " +
                            newCollegeVal.get(0) + "?") == ButtonType.OK) {
                        insertIntoDB("College", COLLEGE_COLS, newCollegeVal);
                        showInfo(info, "Successfully Added College", "Successfully Added new College.");
                        refreshCollege();
                    }
                } catch (Exception e) {
                    return;
                }

        }else showInfo(warning, "Error Adding College", "Please clear selection" +
                " first.");
    }

    public void editCollege(){
        TableModels.College college = tblCollege.getSelectionModel().getSelectedItem();
        if(college != null){
            List<String> oldCollegeVal = validateCollege(college);
            if(showAlert(confirm, "Edit College", "Are you sure you want to edit the information of " +
                    oldCollegeVal.get(0) + "?") == ButtonType.OK){

                try {
                    List<String> newCollegeVal = validateCollege(null);
                    if (!Objects.equals(oldCollegeVal, newCollegeVal)) {
                        updateDB("College", COLLEGE_COLS, newCollegeVal, "College_Code", oldCollegeVal.get(0), "");
                        showInfo(info, "Successfully Edited College", "Successfully Edited new College Code.");
                        refreshCollege();
                    } else showInfo(info, "Error Editing", "No Edits Detected.");
                } catch (Exception e) {
                    return;
                }
            }
        }else showInfo(warning, "Error Editing College", "Please select a record" +
                " first.");
    }

    public void deleteCollege(){
        TableModels.College college = tblCollege.getSelectionModel().getSelectedItem();
        if(college != null){
            if(showAlert(confirm, "Delete College", "Are you sure you want to delete" +
                    "College Code " + college.getCollegeCode() + "? This will also delete " +
                    "any courses under it.") == ButtonType.OK){
                dropDB("College", college.getCollegeCode(), "College_code");
                refreshCollege();
                refreshCourse();
            }
            else return;
        }else showInfo(warning, "Error Deleting College", "Please select a record" +
                " first.");
    }




    public void guestListener(TableModels.Guest currentGuest) {
        if (currentGuest != null) {
            lblGuestListSelectedID3.setText(currentGuest.getGuestID());
            txtGuestFN3.setText(currentGuest.getFirstName());
            txtGuestLN3.setText(currentGuest.getLastName());
            txtGuestEmail3.setText(currentGuest.getEmail());
            cmbBoxGuestGender3.setValue(currentGuest.getGender());
            txtGuestCPNumber3.setText(currentGuest.getCP_Num());
            txtGuestAddress3.setText(currentGuest.getAddress());
            cmbBoxGuestStatus3.setValue(currentGuest.getStatus());
            if(currentGuest.getBDay() != null)
                datePickerGuestBirthday3.setValue(LocalDate.parse(currentGuest.getBDay()));
        } else {
            tblGuestList.getSelectionModel().clearSelection();
            lblGuestListSelectedID3.setText("");
            txtGuestFN3.clear();
            txtGuestLN3.clear();
            txtGuestEmail3.clear();
            cmbBoxGuestGender3.setValue(null);
            txtGuestCPNumber3.clear();
            txtGuestAddress3.clear();
            cmbBoxGuestStatus3.setValue(null);
            datePickerGuestBirthday3.setValue(null);
        }
    }
    public void clearGuestListener(){ guestListener(null);}



    public void employeeListener(TableModels.Employee currentEmployee) {
        if (currentEmployee != null) {
            lblEmployeeListSelectedID2.setText(currentEmployee.getEmployeeID());
            txtEmployeeFN2.setText(currentEmployee.getFirstName());
            txtEmployeeLN2.setText(currentEmployee.getLastName());
            txtEmployeeEmail2.setText(currentEmployee.getEmail());
            txtEmployeeCPNumber2.setText(currentEmployee.getCP_Num());
            txtEmployeeListAddress2.setText(currentEmployee.getAddress());
            cmbBoxEmployeeGender2.setValue(currentEmployee.getGender());
            comboBoxEmployeeStatus2.setValue(currentEmployee.getStatus());
            if(currentEmployee.getBDay() != null)
                datePickerEmployeeBirthday2.setValue(LocalDate.parse(currentEmployee.getBDay()));
        } else {
            tblEmployeeList.getSelectionModel().clearSelection();
            lblEmployeeListSelectedID2.setText("");
            txtEmployeeFN2.clear();
            txtEmployeeLN2.clear();
            txtEmployeeEmail2.clear();
            txtEmployeeCPNumber2.clear();
            txtEmployeeListAddress2.clear();
            cmbBoxEmployeeGender2.setValue(null);
            comboBoxEmployeeStatus2.setValue(null);
            datePickerEmployeeBirthday2.setValue(null);
        }
    }
    public void clearEmployeeListener(){ employeeListener(null);}

    public void semListener(TableModels.SYSEM sem) {
        if (sem != null) {
            txtSelectedSEM1.setText(sem.getSy());
        } else {
            tblSemester.getSelectionModel().clearSelection();
            txtSelectedSEM1.clear(); } }
    public void clearSemListener(){semListener(null);}

    public void activateSEM(){
        if(!txtSelectedSEM1.getText().isEmpty()) {
            var result = showAlert(confirm, "Set Semester", "Are you sure to set the semester to '"
                    + txtSelectedSEM1.getText() + "' ?");
            if (result == ButtonType.OK) {
                updateSYSEM("active_sem", txtSelectedSEM1.getText());
                setCurrentSYSEM();
                refreshSYSem();
                showInfo(info, "Semester set Successful", "Successfully set the active semester");
            }
        }  else showAlert(warning, "Error Activating Semester", "Please select a Semester.");
    }
    public void addSem(){
        TableModels.SYSEM Sem = tblSemester.getSelectionModel().getSelectedItem();
        if(Sem == null) {
            if(!txtSelectedSEM1.getText().isEmpty()){
                if(txtSelectedSEM1.getText().length() == 1){
                    var result = showAlert(confirm, "Add Semester", "Are you sure you want to add" +
                            " semester '" + txtSelectedSEM1.getText() + "'?");
                    if (result == ButtonType.OK) {
                        insertIntoDB("Semester", Arrays.asList("Semester"),
                                Arrays.asList(txtSelectedSEM1.getText()));
                        refreshSYSem();
                        showInfo(info, "Semester set Successful", "Successfully set the active semester");
                    }
                }else showAlert(warning, "Error Adding Semester", "Invalid length, use only 1 character.");
            }else showAlert(warning, "Error Adding Semester", "Please input Semester name");
        } else showAlert(warning, "Error Adding Semester", "Please clear selection first.");
    }
    public void editSem(){
        TableModels.SYSEM Sem = tblSemester.getSelectionModel().getSelectedItem();

        if(Sem != null) {
            var result = showAlert(confirm, "Edit Semester", "Are you sure you want" +
                    " to set '" + Sem.getSy() +  "' to '" + txtSelectedSEM1.getText() + "'?");
            if (result == ButtonType.OK) {
                updateDB("Semester", Arrays.asList("Semester"), Arrays.asList(txtSelectedSEM1.getText())
                , "Semester", Sem.getSy(), "");
                refreshSYSem();
                showInfo(info, "Semester set Successful", "Successfully set the active semester");
            }

        }  else showAlert(warning, "Error Editing Semester", "Please select a record first.");
    }
    public void deleteSem(){
        TableModels.SYSEM sem = tblSemester.getSelectionModel().getSelectedItem();
        if(sem != null){
            if(showAlert(confirm, "Delete Sem", "Are you sure you want to delete " +
                    "Semester " + sem.getSy() + "? This will also delete " +
                    "any transactions under it.") == ButtonType.OK){
                dropDB("Semester", sem.getSy(), "Semester");
                refreshSYSem();
            }
            else return;
        }else showInfo(warning, "Error Deleting Semester", "Please select a record" +
                " first.");
    }

    public void syListener(TableModels.SYSEM sy) {
        if (sy != null) {
            txtSelectedSY1.setText(sy.getSy());
        } else {
            tblSY.getSelectionModel().clearSelection();
            txtSelectedSY1.clear();  }  }
    public void clearSyListener(){syListener(null);}

    public void activateSY(){
        if(!txtSelectedSY1.getText().isEmpty()) {
            var result = showAlert(confirm, "Set SY", "Are you sure to set the SY to '"
                    + txtSelectedSY1.getText() + "' ?");
            if (result == ButtonType.OK) {
                updateSYSEM("active_sy", txtSelectedSY1.getText());
                setCurrentSYSEM();
                showInfo(info, "SY set Successful", "Successfully set the active SY");
            }
        } else showAlert(warning, "Error Activating SY", "Please select an SY.");
    }

    public void addSY(){
        TableModels.SYSEM SY = tblSY.getSelectionModel().getSelectedItem();
        if(SY == null) {
            if(!txtSelectedSY1.getText().isEmpty()){
                if(txtSelectedSY1.getText().length() <=10 || isValidSY(txtSelectedSY1.getText())){
                    var result = showAlert(confirm, "Add SY", "Are you sure you want to add" +
                            " SY '" + txtSelectedSY1.getText() + "'?");
                    if (result == ButtonType.OK) {
                        insertIntoDB("SY", Arrays.asList("SY"),
                                Arrays.asList(txtSelectedSY1.getText()));
                        refreshSYSem();
                        showInfo(info, "SY Add Successful", "Successfully added SY");
                    }
                }else showAlert(warning, "Error Adding SY", "Invalid length, use only 9 characters of numbers" +
                        "and dashes.");
            }else showAlert(warning, "Error Adding SY", "Please input SY name");
        } else showAlert(warning, "Error Adding SY", "Please clear selection first.");
    }
    public void editSY(){
        TableModels.SYSEM SY = tblSY.getSelectionModel().getSelectedItem();

        if(SY != null) {
            var result = showAlert(confirm, "Edit SY", "Are you sure you want" +
                    " to set '" + SY.getSy() +  "' to '" + txtSelectedSY1.getText() + "'?");
            if (result == ButtonType.OK) {
                updateDB("SY", Arrays.asList("SY"), Arrays.asList(txtSelectedSY1.getText())
                        , "SY", SY.getSy(), "");
                refreshSYSem();
                showInfo(info, "SY Edit Successful", "Successfully edited SY");
            }

        }  else showAlert(warning, "Error Editing SY", "Please select a record first.");
    }

    public void deleteSY(){
        TableModels.SYSEM SY = tblSY.getSelectionModel().getSelectedItem();
        if(SY != null){
            if(showAlert(confirm, "Delete SY", "Are you sure you want to delete " +
                    "SY " + SY.getSy() + "? This will also delete " +
                    "any transactions under it.") == ButtonType.OK){
                dropDB("SY", SY.getSy(), "SY");
                refreshSYSem();
            }
            else return;
        }else showInfo(warning, "Error Deleting SY", "Please select a record" + " first.");
    }

    public void pastBooksListener(TableModels.PastBooks currentPastBook) {
        if (currentPastBook != null) {
            lblPastBookSelectedID.setText(currentPastBook.getReservation_ID());
        } else {
            tblPastBooks.getSelectionModel().clearSelection();
            lblPastBookSelectedID.setText("");
        }
    }
    public void clearPastBooks(){ pastBooksListener(null);}


    public void currentBooksListener(TableModels.CurrentBooks currentBooks) {
        if (currentBooks != null) {
            lblBookSelectedID2.setText(currentBooks.getReservation_ID());
        } else {
            tblCurrentBooks.getSelectionModel().clearSelection();
            lblBookSelectedID2.setText("");
        }
    }
    public void clearCurrentBooks(){ currentBooksListener(null);}

    public void currentApptsListener(TableModels.CurrentAppts currentAppts) {
        if (currentAppts != null) {
            lblApptSelectedID2.setText(currentAppts.getAppointment_ID());
        } else {
            tblCurrentAppts.getSelectionModel().clearSelection();
            lblApptSelectedID2.setText("");
        }
    }
    public void clearCurrentAppts(){ currentApptsListener(null);}


    public void pendingApptsListener(TableModels.PendingAppts pendingAppts) {
        if (pendingAppts != null) {
            lblApptSelectedID1.setText(pendingAppts.getAppointment_ID());
            if(pendingAppts.getTransaction_Status().equalsIgnoreCase("PENDING")){
                btnApptApprove1.setText("Approve");
                btnApptDecline1.setDisable(false);
            }
            else{
                btnApptApprove1.setText("Start");
                btnApptDecline1.setDisable(true);
            }
        } else {
            tblApptReservation.getSelectionModel().clearSelection();
            lblApptSelectedID1.setText("");
        }
    }
    public void clearPendingAppts(){ pendingApptsListener(null);}


    public void pendingBooksListener(TableModels.PendingBooks pendingBooks) {
        if (pendingBooks != null) {
            lblBookSelectedID1.setText(pendingBooks.getReservation_ID());
            if(pendingBooks.getTransaction_Status().equalsIgnoreCase("PENDING")){
                btnBookApprove1.setText("Approve");
                btnBookDecline1.setDisable(false);
                grpTargetReturnDateBook.setVisible(false);
            }
            else{
                btnBookDecline1.setDisable(true);
                btnBookApprove1.setText("Start");
                grpTargetReturnDateBook.setVisible(true);
            }
        } else {
            tblBookReservations.getSelectionModel().clearSelection();
            lblBookSelectedID1.setText("");
            datePickerBook.setValue(null);
        }
    }
    public void clearPendingBooks(){ pendingBooksListener(null);}


    public void slotListener(TableModels.SlotData slot) {////////////////////////////////////////////////////////////////////
        if (slot != null) {
            lblSelectedID3.setText(slot.getID() + ", " + slot.getDay());
            txtSlotTimeIn3.setText(slot.getTimeIn());
            txtSlotTimeOut3.setText(slot.getTimeOut());
        } else {
            lblSelectedID3.setText("");
            txtSlotTimeOut3.clear();
            txtSlotTimeIn3.clear();
        }
    }

    public void clearSlotListener(){ slotListener(null);}

    public void editSlot(){
        TableModels.SlotData slot = tblSlot.getSelectionModel().getSelectedItem();
        if(slot != null){
            if(isValidLimit(txtSlotAvailableSlot.getText())){
                if(showAlert(confirm, "Confirm Edit", "Are you sure you want to set the " +
                        "slot limit of slot" + slot.getID() + " - " + slot.getDay() + "to " +
                        txtSlotAvailableSlot.getText() + "?")  == ButtonType.OK){
                    updateDB("Slot", Arrays.asList("Slot_Limit"),
                            Arrays.asList(txtSlotAvailableSlot.getText()), "Slot_ID", slot.getID(),
                            " AND DAY = " + slot.getDay());
                    showInfo(info, "Success", "Successfully edited slot limit.");
                    refreshSlot();
                }
            }else showAlert(warning, "Error editing slots", "Please enter a valid number.");

        }else showAlert(warning, "Error editing slots", "Please select a slot first.");
    }

    public void clearBookListener(){ bookListener(null);}
    public void bookListener(TableModels.Book book) {/////////////////////////////////////////////////////////////////////
        if (book != null) {
            lblBookSelectedID4.setText(book.getBookId());
            txtFieldBookDesc1.setText(book.getDescription());
            txtFieldBookAuth1.setText(book.getAuthor());
            txtFieldBookPub1.setText(book.getPublisher());
            txtFieldBookPrice1.setText(Double.toString(book.getPrice()));
            txtFieldBookISBN1.setText(book.getIsbn());
            datePickerBookDatePublished1.setValue(LocalDate.parse(book.getDatePublished()));
            cmbBoxBookStatus1.setValue(book.getStatus());
            cmbBoxBookSubject.setValue(book.getBookId().substring(0, 3));
        } else {
            tblBookList.getSelectionModel().clearSelection();
            lblBookSelectedID4.setText("");
            txtFieldBookDesc1.clear();
            txtFieldBookAuth1.clear();
            txtFieldBookPub1.clear();
            txtFieldBookPrice1.clear();
            txtFieldBookISBN1.clear();
            datePickerBookDatePublished1.setValue(null);
            cmbBoxBookStatus1.setValue(null);
            cmbBoxBookSubject.setValue(null);
        }
    }
    public void addBook(MouseEvent event) {
        TableModels.Book book = tblBookList.getSelectionModel().getSelectedItem();
        if(book == null){
            try {
                List<String> newBookVal = validateBook(null);
                if(showAlert(confirm, "Confirm Add", "Are you sure you want to add a new book?")
                == ButtonType.OK){
                    String newBookID = getNextID("Book", "Book_ID", 1, cmbBoxBookSubject.getValue());
                    newBookVal.set(0, newBookID);
                    insertIntoDB("Book", BOOK_COLS, newBookVal);
                    showInfo(info, "Successfully Added", "Successfully Added Book ID. " +
                            newBookID);
                    refreshBook();
                }

            } catch (Exception e){
                return;
            }
        }
        else showAlert(error, "Error Adding", "Please clear selection first!");
    }

    public void editBook(MouseEvent event){
        TableModels.Book book = tblBookList.getSelectionModel().getSelectedItem();
        if(book != null) {
            List<String> oldBookVal = validateBook(book);
            System.out.println(oldBookVal);
            try {
                List<String> newBookVal = validateBook(null);

                if (!Objects.equals(oldBookVal, newBookVal)) {
                    updateDB("Book", BOOK_COLS, newBookVal, "Book_ID",
                            newBookVal.get(0), "");
                    refreshBook();
                    showInfo(info, "Successfully Edited Book", "Successfully " +
                            "Edited Book information.");
                } else showInfo(info, "Error Editing", "No Edits Detected.");

            } catch (RuntimeException e) {
                return;
            }
        }else showInfo(info, "Error Editing", "Please select a record!");
    }

    public void searchBook(MouseEvent event) {
        TableModels.Book book = tblBookList.getSelectionModel().getSelectedItem();
        if(book == null){
            var stringList = createValuesList(cmbBoxBookSubject, txtFieldBookDesc1, txtFieldBookAuth1, txtFieldBookPub1,
                    datePickerBookDatePublished1, txtFieldBookPrice1,
                    txtFieldBookISBN1 ,cmbBoxBookStatus1);

            DB.populateBook(tblBookList, createHashMap(stringList, BOOK_COLS));
        }
        else showAlert(error, "Error Searching", "Please clear selection first!");
    }

    public void deleteBook(MouseEvent event){
        TableModels.Book book = tblBookList.getSelectionModel().getSelectedItem();
        if(book != null){
            if(!book.getStatus().equalsIgnoreCase("R")){
                if (showAlert(confirm, "Delete Book", "Are you sure you want to delete" +
                        " Book " + book.getBookId() + "? Users will not be able to see" +
                        " this book in the app anymore.") == ButtonType.OK) {
                    dropDB("Book", book.getBookId(), "Book_ID");
                    refreshBook();
                } else return;
            }else showInfo(warning, "Error Deleting", "Book is currently being reserved, please" +
                    " complete transaction first.");
        } else showInfo(info, "Error Deleting", "Please select a record!");
    }



    public void studentListener(TableModels.Student student) {

        if (student != null) {
            lblStudentListSelectedID1.setText(student.getStudent_No());
            txtStudentListFN1.setText(student.getFirstName());
            txtStudentListLN1.setText(student.getLastName());
            txtStudentListEmail1.setText(student.getEmail());
            txtStudentListCPNumber1.setText(student.getCP_Num());
            txtStudentListAddress1.setText(student.getAddress());
            cmbBoxStudentListStatus1.setValue(student.getStatus());
            combBoxStudentListGender1.setValue(student.getGender());
            cmbBoxStudentListCourseCode1.setValue(student.getCourse_Code());
            datePickerStudentListBirthday1.setValue(LocalDate.parse(student.getBDay()));
        } else {
            tblStudentList.getSelectionModel().clearSelection();
            refreshStudent();
            lblStudentListSelectedID1.setText(" ");
            txtStudentListFN1.clear();
            txtStudentListLN1.clear();
            txtStudentListEmail1.clear();
            txtStudentListCPNumber1.clear();
            txtStudentListAddress1.clear();
            combBoxStudentListGender1.setValue(null);
            cmbBoxStudentListCourseCode1.setValue(null);
            datePickerStudentListBirthday1.setValue(null);
        }    }

    public void clearStudent(MouseEvent event){
        studentListener(null);
    }

    public void tglEdit() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (btnAccountEdit.isVisible()) {
            btnAccountEdit.setVisible(false);
            lblPassword.setVisible(true);
            lblPassword1.setVisible(true);
            grpSetPass.setVisible(true);
            grpConfirmPass.setVisible(true);
            btnAccountCancel.setVisible(true);
            btnAccountSave.setVisible(true);
        } else {
            btnAccountEdit.setVisible(true);
            lblPassword.setVisible(false);
            lblPassword1.setVisible(false);
            grpSetPass.setVisible(false);
            grpConfirmPass.setVisible(false);
            btnAccountCancel.setVisible(false);
            btnAccountSave.setVisible(false);
        }
        setEditableFields(!btnAccountEdit.isVisible());

    }

    public void approveBook(MouseEvent event){
        List<String> newVals = Arrays.asList("APPROVED", getTblID(), getCurrentDateTime().format(formatter));
        TableModels.PendingBooks selectedReservation = tblBookReservations.getSelectionModel().getSelectedItem();
        if(selectedReservation != null){
            String pk = selectedReservation.getReservation_ID();
            if(selectedReservation.getTransaction_Status().equalsIgnoreCase("PENDING")){
                if (showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                        " to APPROVE " + selectedReservation.getUser_FirstName() + "'s " +
                        "request? This will DECLINE all other transactions with the same book." +
                        " Proceed?") == ButtonType.OK) {

                    updateDB("Book_Reservation", COL_APPROVE, newVals, "Reservation_ID",
                            pk, "");
                    newVals.set(0, "DECLINED");
                    updateDB("Book_Reservation", COL_APPROVE,
                            newVals, "BOOK_ID", selectedReservation.getBook_ID(),
                            " AND Transaction_Status = 'PENDING'");
                    updateDB("Book", Arrays.asList("Status"),Arrays.asList("R"), "Book_ID",
                            selectedReservation.getBook_ID(), "" );

                }
            } else {
                if (datePickerBook.getValue() == null) {
                    showAlert(warning, "Error Starting Transaction", "Please fill up Target" +
                            " Return Date.");
                    } else {
                    if (showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                            " to START " + selectedReservation.getUser_FirstName() + "'s " +
                            "transaction?") == ButtonType.OK) {
                        newVals = Arrays.asList("ONGOING", getCurrentDate().toString(), datePickerBook.getValue().toString(), getTblID());
                        updateDB("Book_Reservation", COL_START, newVals, "Reservation_ID",
                                pk, "");
                    }
                }
            }
        }else showAlert(error, "Error Approving", "Please select record first!");
        refreshPendingBooks();
        refreshCurrentBooks();
        refreshPastBooks();
    }

    public void declineBook(MouseEvent event){
        List<String> newVals = Arrays.asList("DECLINED", getTblID(), getCurrentDateTime().format(formatter));
        TableModels.PendingBooks selectedReservation = tblBookReservations.getSelectionModel().getSelectedItem();
        if(selectedReservation != null){
            String pk = selectedReservation.getReservation_ID();
            if(showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                    " to DECLINE " + selectedReservation.getUser_FirstName() + "'s " +
                    "request?") == ButtonType.OK)
                updateDB("Book_Reservation", COL_APPROVE,
                        newVals, "Reservation_ID", pk,"");

            refreshPendingBooks();
            refreshCurrentBooks();
            refreshPastBooks();

        }else showAlert(error, "Error Approving", "Please select record first!");

    }
    public void completeBook(){
        TableModels.CurrentBooks selectedReservation = tblCurrentBooks.getSelectionModel().getSelectedItem();
        if(selectedReservation != null){
            String pk = selectedReservation.getReservation_ID();

            if(isValidFine(txtBookFine.getText())){
                if (showAlert(confirm, "Confirm Completion", "Are you sure you want" +
                        " to COMPLETE " + selectedReservation.getUser_FirstName() + "'s " +
                        "request with the correct Remark and Fine?") == ButtonType.OK) {

                    List<String> newVals = Arrays.asList("COMPLETE", getCurrentDateTime().format(formatter),
                            txtBookRemark.getText());
                    updateDB("Book_Reservation", COL_COMPLETE,
                            newVals, "Reservation_ID", pk, "");
                    if(!txtBookFine.getText().trim().isEmpty())
                        updateFine(txtBookFine.getText(), pk);
                    updateDB("Book", Arrays.asList("Status"),Arrays.asList("A"), "Book_ID",
                            selectedReservation.getBook_ID(), "" );
                    refreshPendingBooks();
                    refreshCurrentBooks();
                    refreshPastBooks();
                }
            } else showAlert(error, "Error Approving", "Please enter valid fine!");

        }else showAlert(error, "Error Approving", "Please select record first!");
        refreshPendingBooks();

    }

    public void bookPaid(){
        TableModels.PastBooks selectedReservation = tblPastBooks.getSelectionModel().getSelectedItem();
        if(selectedReservation != null){
            String pk = selectedReservation.getReservation_ID();
            if(selectedReservation.getIsPaid() != null){
                if (showAlert(confirm, "Confirm Fine Payment", "Are you sure you want" +
                        " to CONFIRM the payment of " + selectedReservation.getUser_FirstName() + "'s " +
                        "Fine?") == ButtonType.OK)

                {updateIsPaid("1 ", pk);}

            }else showAlert(error, "Error Approving", "Please select a record with a fine");
            refreshPastBooks();
        }else showAlert(error, "Error Approving", "Please select record first!");

    }
    public void bookUnpaid(){
        TableModels.PastBooks selectedReservation = tblPastBooks.getSelectionModel().getSelectedItem();
        if(selectedReservation != null){
            String pk = selectedReservation.getReservation_ID();
            if(selectedReservation.getIsPaid() != null) {

                if (showAlert(confirm, "Confirm Fine Payment", "Are you sure you want" +
                        " to set the Fine of " + selectedReservation.getUser_FirstName() + " " +
                        "to UNPAID? This will disable their ability to make transactions.") == ButtonType.OK)

                { updateIsPaid("0 ", pk); }

            }else showAlert(error, "Error Approving", "Please select a record with a fine");
            refreshPastBooks();
        }else showAlert(error, "Error Approving", "Please select record first!"); }

    public void approveAppt(MouseEvent event){
        List<String> newVals = Arrays.asList("APPROVED", getTblID(), getCurrentDateTime().format(formatter));
        TableModels.PendingAppts selectedAppt = tblApptReservation.getSelectionModel().getSelectedItem();
        if(selectedAppt != null){
            String pk = selectedAppt.getAppointment_ID();
            if(selectedAppt.getTransaction_Status().equalsIgnoreCase("PENDING")) {
                if (showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                        " to APPROVE " + selectedAppt.getFirstName() + "'s " +
                        "request? This will DECLINE all other transactions with the same SLOT if its full." +
                        " Proceed?") == ButtonType.OK) {
                    updateDB("Appointment", COL_APPROVE, newVals, "Appointment_ID",
                            pk, "");
                    if (Integer.parseInt(selectFromDB("vwSlots", "Available_Slots", ""
                    )) == 0) {
                        newVals.set(0, "DECLINED");
                        updateDB("Appointment", COL_APPROVE, newVals, "Appointment_ID",
                                pk, "");
                    }}
            }else{
                if (showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                        " to START " + selectedAppt.getFirstName() + "'s " +
                        "transaction?") == ButtonType.OK) {
                    newVals = Arrays.asList("ONGOING", getCurrentTime().toString(), getTblID());
                    updateDB("Appointment", COL_START_APPT, newVals, "Appointment_ID",
                            pk, "");
                }}
            refreshPendingAppts();
            refreshCurrentAppts();
            refreshPastAppts();
        }else showAlert(error, "Error Approving", "Please select record first!");   }

    public void declineAppt(MouseEvent event){
        List<String> newVals = Arrays.asList("DECLINED", getTblID(), getCurrentDateTime().format(formatter));
        TableModels.PendingAppts selectedAppt = tblApptReservation.getSelectionModel().getSelectedItem();
        if(selectedAppt!= null){
            String pk = selectedAppt.getAppointment_ID();
            if(showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                    " to DECLINE " + selectedAppt.getFirstName() + "'s " +
                    "request?") == ButtonType.OK)

                updateDB("Appointment_ID", COL_APPROVE,
                        newVals, "Appointment_ID", pk,"");
            refreshPendingAppts();
            refreshCurrentAppts();
            refreshPastAppts();
        }else showAlert(error, "Error Approving", "Please select record first!");
    }


    public void completeAppt(){

        TableModels.CurrentAppts selectedAppt = tblCurrentAppts.getSelectionModel().getSelectedItem();
        if(selectedAppt != null){
            String pk = selectedAppt.getAppointment_ID();

            if(showAlert(confirm, "Confirm Approval", "Are you sure you want" +
                    " to COMPLETE " + selectedAppt.getFirstName() + "'s " +
                    "request with the correct remark?") == ButtonType.OK){

                List<String> newVals = Arrays.asList("COMPLETE", getCurrentTime().format(timeFormatter),
                        txtApptRemark.getText());
                updateDB("Appointment", COL_COMPLETE_APPT,
                        newVals, "Appointment_ID", pk,"");
            }

            refreshPendingAppts();
            refreshCurrentAppts();
            refreshPastAppts();
        }else showAlert(error, "Error Approving", "Please select record first!");

        refreshPendingBooks();

    }



    public void addStudent(MouseEvent event) {
        if(tblStudentList.getSelectionModel().getSelectedItem() == null){
            try {
                List<String> newStudentVal = validateStudents(null);
                String newStudentID = getNextID("Student", "Student_No", 2, "");
                newStudentVal.add(0, newStudentID);
                DataBaseMethods.insertIntoDB("Student", STUDENT_COLS, newStudentVal);
                showInfo(info, "Successfully Added", "Successfully Added Student No. " +
                        newStudentID);
                refreshStudent();
            } catch (Exception e){
                return;
            }
        }
        else showAlert(error, "Error Adding", "Please clear selection first!");
    }

    public void editStudent(MouseEvent event){
        TableModels.Student student = tblStudentList.getSelectionModel().getSelectedItem();
        if(student != null) {
            List<String> oldstudentVal = validateStudents(student);

                List<String> newStudentVal = validateStudents(null);
                System.out.println(oldstudentVal);
                System.out.println(newStudentVal);
                if (!Objects.equals(oldstudentVal, newStudentVal)) {
                    List<String> stud_cols = STUDENT_COLS.subList(1, STUDENT_COLS.size());
                    updateDB("Student", stud_cols, newStudentVal, "Student_NO",
                            student.getStudent_No(), "");
                    refreshStudent();
                    showInfo(info, "Successfully Edited Student", "Successfully " +
                            "Edited Student record.");
                } else showInfo(info, "Error Editing", "No Edits Detected.");
            }else showInfo(info, "Error Editing", "Please select a record!");
        }


    public void searchStudent(MouseEvent event) {
        if(tblStudentList.getSelectionModel().getSelectedItem() == null){
            var stringList = createValuesList(txtStudentListLN1, txtStudentListFN1, txtStudentListEmail1,
                    combBoxStudentListGender1, cmbBoxStudentListCourseCode1,
                    txtStudentListCPNumber1 ,txtStudentListAddress1,
                    datePickerStudentListBirthday1, cmbBoxStudentListStatus1
            );
        List<String> stud_cols = STUDENT_COLS.subList(1, STUDENT_COLS.size());
            System.out.println(createHashMap(stringList, stud_cols));
            System.out.println(conditionMaker(createHashMap(stringList, stud_cols)));
            populate("Student", STUDENT_COLS, studentList, tblStudentList,
                    TableModels.Student.class, conditionMaker(createHashMap(stringList, stud_cols)));
        }
        else showAlert(error, "Error Searching", "Please clear selection first!");

    }

    public void deleteStudent(MouseEvent event){
        TableModels.Student student = tblStudentList.getSelectionModel().getSelectedItem();
        if(student != null){
            if(showAlert(confirm, "Delete Student", "Are you sure you want to delete " +
                "Student " + student.getStudent_No() + "'s Record? This will also delete " +
                "their user account.") == ButtonType.OK){
                    dropDB("Student", student.getStudent_No(), "Student_No");
                    dropDB("User", student.getStudent_No(), "User_ID");
                    refreshStudent();
            }
            else return;
        }
        else showInfo(info, "Error Deleting", "Please select a record!");
    }

    public void registerStudent(){
        TableModels.Student student = tblStudentList.getSelectionModel().getSelectedItem();
        if(student != null){
            if(getCount("User", student.getStudent_No(), "User_ID", "") != 1){
                try {
                    String pass = showInput("Enter Password for " + student.getStudent_No());
                    if (isValidPass(pass) && !pass.isEmpty()) {
                        insertIntoDB("User", USER_COLS,
                                Arrays.asList(student.getStudent_No(), "S", pass));
                        showInfo(info, "Success", "Successfully registered new User." +
                                "\nUser ID: " + student.getStudent_No() +
                                "\nPassword: " + pass);
                    }
                }catch (RuntimeException e)
                { showAlert(warning, "Error Registering", "Password is empty/invalid.");}
            }else showAlert(warning, "Error Registering", "User is already registered.");
        }else showInfo(info, "Error Deleting", "Please select a record!");
    }


    public void addEmployee(MouseEvent event) {
        TableModels.Employee employee = tblEmployeeList.getSelectionModel().getSelectedItem();
        if(employee == null){
            try {
                List<String> newEmployeeVal = validateEmployee(null);
                String newEmployeeID = getNextID("Employee", "Employee_ID", 2, "");
                newEmployeeVal.add(0, newEmployeeID);
                DataBaseMethods.insertIntoDB("Employee", EMPLOYEE_COLS, newEmployeeVal);
                showInfo(info, "Successfully Added", "Successfully Added Librarian No. " +
                        newEmployeeID);
                refreshEmployee();
            } catch (Exception e){
                return;
            }
        }
        else showAlert(error, "Error Adding", "Please clear selection first!");
    }

    public void editEmployee(MouseEvent event){
        TableModels.Employee employee = tblEmployeeList.getSelectionModel().getSelectedItem();
        if(employee != null){
            List<String> oldEmployeeVal = validateEmployee(employee);

            List<String> newEmployeeVal = validateEmployee(null);
            System.out.println(oldEmployeeVal);
            System.out.println(newEmployeeVal);
            if (!Objects.equals(oldEmployeeVal, newEmployeeVal)) {
                List<String> emp_cols = EMPLOYEE_COLS.subList(1, EMPLOYEE_COLS.size());
                updateDB("Employee", emp_cols, newEmployeeVal, "Employee_ID",
                        employee.getEmployeeID(), "");
                refreshEmployee();
                showInfo(info, "Successfully Edited Employee", "Successfully " +
                        "Edited Employee record.");
            } else showInfo(info, "Error Editing", "No Edits Detected.");
        }else showInfo(info, "Error Editing", "Please select a record!");
    }


    public void searchEmployee(MouseEvent event) {
        if(tblEmployeeList.getSelectionModel().getSelectedItem() == null){
            var stringList = createValuesList(txtEmployeeFN2, txtEmployeeLN2, cmbBoxEmployeeGender2,
                    txtEmployeeEmail2, txtEmployeeCPNumber2, txtEmployeeListAddress2
                    ,comboBoxEmployeeStatus2, datePickerEmployeeBirthday2
            );
            List<String> emp_cols = EMPLOYEE_COLS.subList(1, EMPLOYEE_COLS.size());
            System.out.println(createHashMap(stringList, emp_cols));
            System.out.println(conditionMaker(createHashMap(stringList, emp_cols)));
            populate("Employee", emp_cols, employeeList, tblEmployeeList,
                    TableModels.Employee.class, conditionMaker(createHashMap(stringList, emp_cols)));
        }
        else showAlert(error, "Error Searching", "Please clear selection first!");

    }

    public void deleteEmployee(MouseEvent event){
        TableModels.Employee employee = tblEmployeeList.getSelectionModel().getSelectedItem();
        if(employee != null){
            if(Objects.equals(employee.getEmployeeID(), getTblID())) {
                if (showAlert(confirm, "Delete Employee", "Are you sure you want to delete " +
                        "your Record? This will also delete your user account.") == ButtonType.OK) {
                    dropDB("Employee", employee.getEmployeeID(), "Employee_ID");
                    dropDB("User", employee.getEmployeeID(), "User_ID");
                    change("FXMLS/login.fxml", anchorPaneRoot, event);
                }
            }else
            {
                if (showAlert(confirm, "Delete Employee", "Are you sure you want to delete " +
                        "Employee " +
                        employee.getEmployeeID() + "'s Record? This will also delete " +
                        "their user account.") == ButtonType.OK) {
                    dropDB("Employee", employee.getEmployeeID(), "Employee_ID");
                    dropDB("User", employee.getEmployeeID(), "User_ID");
                    refreshEmployee();
                }
            }

        }
        else showInfo(info, "Error Deleting", "Please select a record!");
    }
    public void registerEmployee(){
        TableModels.Employee employee = tblEmployeeList.getSelectionModel().getSelectedItem();
        if(employee != null){
            if(getCount("User", employee.getEmployeeID(), "User_ID", "") != 1){
                try {
                    String pass = showInput("Enter Password for " + employee.getEmployeeID());
                    if (isValidPass(pass) && !pass.isEmpty()) {
                        insertIntoDB("User", USER_COLS,
                                Arrays.asList(employee.getEmployeeID(), "E", pass));
                        showInfo(info, "Success", "Successfully registered new User." +
                                "\nUser ID: " + employee.getEmployeeID() +
                                "\nPassword: " + pass);
                    }
                }catch (RuntimeException e)
                { showAlert(warning, "Error Registering", "Password is empty/invalid.");}
            }else showAlert(warning, "Error Registering", "User is already registered.");
            return;
        }else showInfo(info, "Error Deleting", "Please select a record!");
    }

    public void addGuest(MouseEvent event) {
        TableModels.Guest guest = tblGuestList.getSelectionModel().getSelectedItem();
        if(guest == null){
            try {
                List<String> newGuestVal = validateGuest(null);
                String newGuestID = getNextID("Guest", "Guest_ID", 2, "");
                newGuestVal.add(0, newGuestID);
                DataBaseMethods.insertIntoDB("Guest", GUEST_COLS, newGuestVal);
                showInfo(info, "Successfully Added", "Successfully Added Guest No. " +
                        newGuestID);
                refreshGuest();
            } catch (Exception e){
                return;
            }
        } else showAlert(error, "Error Adding", "Please clear selection first!");
    }

    public void editGuest(MouseEvent event){
        TableModels.Guest guest = tblGuestList.getSelectionModel().getSelectedItem();
        if(guest != null){
            List<String> oldGuestVal = validateGuest(guest);

            List<String> newGuestVal = validateGuest(null);
            if (!Objects.equals(oldGuestVal, newGuestVal)) {
                List<String> guest_col = GUEST_COLS.subList(1, EMPLOYEE_COLS.size());
                updateDB("Guest", guest_col, newGuestVal, "Guest_ID",
                        guest.getGuestID(), "");
                refreshEmployee();
                showInfo(info, "Successfully Edited Guest", "Successfully " +
                        "Edited Guest record.");
            } else showInfo(info, "Error Editing", "No Edits Detected.");
        }else showInfo(info, "Error Editing", "Please select a record!");
    }


    public void searchGuest(MouseEvent event) {
        if(tblStudentList.getSelectionModel().getSelectedItem() == null){
            var stringList = createValuesList(txtGuestLN3, txtGuestFN3, txtGuestEmail3,
                    txtGuestCPNumber3, txtGuestAddress3, datePickerGuestBirthday3,
                    cmbBoxGuestGender3, cmbBoxGuestStatus3
            );
            List<String> guest_col = GUEST_COLS.subList(1, EMPLOYEE_COLS.size());
            populate("Guest", guest_col, guestList, tblGuestList,
                    TableModels.Guest.class, conditionMaker(createHashMap(stringList, guest_col)));
        }
        else showAlert(error, "Error Searching", "Please clear selection first!");
    }
    public void deleteGuest(MouseEvent event){
        TableModels.Guest guest = tblGuestList.getSelectionModel().getSelectedItem();
        if(guest != null){
            if(showAlert(confirm, "Delete Guest", "Are you sure you want to delete " +
                    "Guest " +
                    guest.getGuestID() + "'s Record? This will also delete " +
                    "their user account.") == ButtonType.OK){
                dropDB("Guest", guest.getGuestID(), "Guest_ID");
                dropDB("User", guest.getGuestID(), "User_ID");
                refreshGuest();
            }
        }
        else showInfo(info, "Error Deleting", "Please select a record!");
    }

    public void registerGuest(){
        TableModels.Guest guest = tblGuestList.getSelectionModel().getSelectedItem();
        if(guest != null){
            if(getCount("User", guest.getGuestID(), "User_ID", "") != 1){
                try {
                    String pass = showInput("Enter Password for " + guest.getGuestID());
                    if (isValidPass(pass) && !pass.isEmpty()) {
                        insertIntoDB("User", USER_COLS,
                                Arrays.asList(guest.getGuestID(), "G", pass));
                        showInfo(info, "Success", "Successfully registered new User." +
                                "\nUser ID: " + guest.getGuestID() +
                                "\nPassword: " + pass);
                    }
                }catch (RuntimeException e)
                { showAlert(warning, "Error Registering", "Password is empty/invalid.");}
            }else showAlert(warning, "Error Registering", "User is already registered.");
        }else showInfo(info, "Error Deleting", "Please select a record!");
    }


    public void saveEdit() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (lblShow.getText().equals("Hide"))
            passAccPassword.setText(txtAccPassword.getText());
        else
            txtAccPassword.setText(passAccPassword.getText());
        if (lblShow1.getText().equals("Hide"))
            passAccPassword1.setText(txtAccPassword1.getText());
        else
            txtAccPassword1.setText(passAccPassword1.getText());


        System.out.println(passAccPassword.getText());
        System.out.println(txtAccPassword.getText());
        System.out.println(passAccPassword1.getText());
        System.out.println(txtAccPassword1.getText());

        if (!passAccPassword.getText().isEmpty() && !passAccPassword1.getText().isEmpty()) {
            if (!passAccPassword.getText().equals(passAccPassword1.getText())) {
                showAlert(warning, "Password Error", "Password Fields are not equal");
                return;
            } else if (!(passAccPassword.getText().length() < 20) || !(passAccPassword.getText().length() > 7)) {
                showAlert(warning, "Password Error", "Password must be between 8 and 20 characters.");
                return;
            }
            if (!InputValidation.isValidPass(passAccPassword1.getText())) {
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
                newUn = txtAccountUN.getText();

                List<Object> newVals = Arrays.asList(txtAccountFN.getText(), txtAccountLN.getText(), cmbBoxAccountGender.getValue(), txtAccountEmail.getText()
                        , txtAccountCPNumber.getText(), txtAccountAddress.getText(), datePickerBirthday.getValue().toString());
                setUserID(txtAccountID.getText());

                System.out.println("New Vals = " + newVals);
                DB.updateDB("Employee", newUn, getUserID(), EMPLOYEE_COLUMN_NAMES, newVals,
                        "Employee_ID", passAccPassword1.getText());

                tglEdit();
                refreshAccount();
                passAccPassword.clear();
                passAccPassword1.clear();
                txtAccPassword.clear();
                txtAccPassword1.clear();
                lblAccountPassWarning.setVisible(false);
            }

    }

    public void cancelEdit() {/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        refreshAccount();
        lblAccountPassWarning.setVisible(false);
        passAccPassword.clear();
        passAccPassword1.clear();
        txtAccPassword.clear();
        txtAccPassword1.clear();
        tglEdit();
    }

    public void refreshAccount() {///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        DB.setBDay(getUserID(), datePickerBirthday);
        DB.toText("User_ID", txtAccountID, getUserID(), null);
        DB.toText("Username", txtAccountUN, getUserID(), null);
        DB.toText("FirstName", txtAccountFN, getUserID(), null);
        DB.toText("LastName", txtAccountLN, getUserID(), null);
        DB.toCmbBox("vwUserInfo", "Gender", getUserID(), cmbBoxAccountGender);
        DB.toText("Email", txtAccountEmail, getUserID(), null);
        DB.toText("CP_Num", txtAccountCPNumber, getUserID(), null);
        DB.toText("Address", txtAccountAddress, getUserID(), null);
        setTblID(txtAccountID.getText());
    }
    public void setDate() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = showAlert(confirm, "Set Time", "Are you sure you want to set the date to '"
                + txtSetDate.getValue().toString() + "' ?");
        if(result == ButtonType.OK){
            setCurrentDate(LocalDate.parse(txtSetDate.getValue().toString()));
        }
    }
    public void resetTime(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        var result = showAlert(confirm, "Set Time", "Are you sure reset the time" +
                "to the current one?");
        if(result == ButtonType.OK){
            setCurrentDateTime(null);
        }
    }



    public void setTime() {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(isValidTime(txtSetTime.getText())){
            var result = showAlert(confirm, "Set Time", "Are you sure to set the time to '"
                    + txtSetTime.getText() + "' ?");
            if( result == ButtonType.OK){
                setCurrentTime(LocalTime.parse(txtSetTime.getText()));
                showInfo(info, "Time set Successful", "Successfully set the time");
            }
        }else {
            showInfo(warning, "Error Setting Time" ,"Please input a valid 24-hour time" +
                    " in the format of HH:MM (no spaces)");
            txtSetTime.clear();
        }
    }

    private void setEditableFields(boolean editable) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        txtAccountUN.setEditable(editable);
        txtAccountFN.setEditable(editable);
        txtAccountLN.setEditable(editable);
        cmbBoxAccountGender.setDisable(!editable);
        txtAccountEmail.setEditable(editable);
        txtAccountCPNumber.setEditable(editable);
        txtAccountAddress.setEditable(editable);
        datePickerBirthday.setDisable(!editable);
        txtAccountUN.setEditable(editable);
        txtAccountFN.setEditable(editable);
        txtAccountLN.setEditable(editable);
        cmbBoxAccountGender.setDisable(!editable);
        txtAccountEmail.setEditable(editable);
        txtAccountCPNumber.setEditable(editable);
        txtAccountAddress.setEditable(editable);
        datePickerBirthday.setDisable(!editable);
        if (editable) {
            txtAccountFN.getStyleClass().remove("input-editable");
            txtAccountLN.getStyleClass().remove("input-editable");
            cmbBoxAccountGender.getStyleClass().remove("input-editable");
            txtAccountEmail.getStyleClass().remove("input-editable");
            txtAccountCPNumber.getStyleClass().remove("input-editable");
            txtAccountAddress.getStyleClass().remove("input-editable");
            txtAccountUN.getStyleClass().remove("input-editable");
            datePickerBirthday.getStyleClass().remove("input-editable");
            txtAccountID.getStyleClass().remove("input-editable");
            txtAccountFN.getStyleClass().add("input");
            datePickerBirthday.getStyleClass().add("input");
            txtAccountLN.getStyleClass().add("input");
            cmbBoxAccountGender.getStyleClass().add("input");
            txtAccountEmail.getStyleClass().add("input");
            txtAccountCPNumber.getStyleClass().add("input");
            txtAccountAddress.getStyleClass().add("input");
            txtAccountUN.getStyleClass().add("input");
            txtAccountID.getStyleClass().add("input-uneditable");
        } else {
            txtAccountFN.getStyleClass().remove("input");
            txtAccountID.getStyleClass().remove("input-uneditable");
            txtAccountLN.getStyleClass().remove("input");
            cmbBoxAccountGender.getStyleClass().remove("input");
            txtAccountEmail.getStyleClass().remove("input");
            txtAccountCPNumber.getStyleClass().remove("input");
            txtAccountAddress.getStyleClass().remove("input");
            txtAccountUN.getStyleClass().remove("input");
            datePickerBirthday.getStyleClass().remove("input");
            datePickerBirthday.getStyleClass().add("input-editable");
            txtAccountUN.getStyleClass().add("input-editable");
            txtAccountFN.getStyleClass().add("input-editable");
            txtAccountLN.getStyleClass().add("input-editable");
            cmbBoxAccountGender.getStyleClass().add("input-editable");
            txtAccountEmail.getStyleClass().add("input-editable");
            txtAccountCPNumber.getStyleClass().add("input-editable");
            txtAccountAddress.getStyleClass().add("input-editable");
            txtAccountID.getStyleClass().add("input-editable");
        }
    }

    public void LogOut(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        change("FXMLS/login.fxml", anchorPaneRoot, event);
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

    public void showPass(MouseEvent event) {////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        showPassword(event);
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<String> validateCollege(TableModels.College college){
        if(college == null){
            int errorcount = 0;
            List<String> newCollege =
                    extractValues(txtCollegeCode2, txtCollegeDescription2,
                            datePickerCollegeDateOpened2, datePickerCollegeDateClosed2,
                            cmbBoxCollegeStatus2);
            if(!isValidCode(newCollege.get(0), "College", "College_Code")){
                showAlert(warning, "Invalid Input",
                        "Invalid CODE. Please enter a valid College Code.");
                errorcount = 1;
            }
            if(!isValidDescription(newCollege.get(1))){
                showAlert(warning, "Invalid Input",
                        "Invalid DESCRIPTION. Please enter a valid description.");
                errorcount = 1;
            }

            if (errorcount==1)
                throw new RuntimeException();

            return newCollege;

        }
        else return Arrays.asList(college.getCollegeCode(), college.getDescription(), college.getStatus(),
                college.getDateOpened(), college.getDateClosed());
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<String> validateCourse(TableModels.Courses course){
        if(course == null){
            int errorcount = 0;
            List<String> newCourse =
                    extractValues(txtCourseCode3, cmbBoxCollegeCode3, txtCourseDescription3,
                            datePickerCourseDateOpened3, datePickerCourseDateClosed3, cmbBoxCourseStatus3);
            if(!isValidCode(newCourse.get(0), "Course", "Course_Code")){
                showAlert(warning, "Invalid Input",
                        "Invalid CODE. Please enter a valid Course Code.");
                errorcount = 1;
            }
            if(!isValidDescription(newCourse.get(1))){
                showAlert(warning, "Invalid Input",
                        "Invalid DESCRIPTION. Please enter a valid description.");
                errorcount = 1;
            }

            if (errorcount==1)
                throw new RuntimeException();

            return newCourse;

        }
        else return Arrays.asList(course.getCourseCode(), course.getCollegeCode(),
                course.getDescription(), course.getDateOpened(), course.getDateClosed(),
                course.getStatus());

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<String> validateStudents(TableModels.Student student){
        if (student == null) {
            int errorcount = 0;
            List<String> newStudentVal =
                    extractValues(txtStudentListLN1, txtStudentListFN1, txtStudentListEmail1, combBoxStudentListGender1,
                            cmbBoxStudentListCourseCode1, txtStudentListCPNumber1, txtStudentListAddress1,
                            datePickerStudentListBirthday1, cmbBoxStudentListStatus1);

            if (!isValidName(newStudentVal.get(0)) || !isValidName(newStudentVal.get(1))) {
                showAlert(warning, "Invalid Input", "Invalid NAME. Please enter a valid name.");
                errorcount = 1;
            }

            if (!isValidEmail(newStudentVal.get(2))) {
                showAlert(warning, "Invalid Input", "Invalid EMAIL. Please enter a valid email address.");
                errorcount = 1;
            }

            if (!isValidNumber(newStudentVal.get(5))) {
                showAlert(warning, "Invalid Input", "Invalid CP NUMBER. Please enter a valid number.");
                errorcount = 1;
            }
            if (!isValidAddress(newStudentVal.get(6))) {
                showAlert(warning, "Invalid Input", "Invalid ADDRESS. Please enter a valid address.");
                errorcount = 1;
            }
            if (errorcount == 1) {
                throw new RuntimeException();
            }
            return newStudentVal;
        }
        else{
            return Arrays.asList( student.getLastName(), student.getFirstName(),  student.getEmail(), student.getGender(), student.getCourse_Code(),
                    student.getCP_Num(), student.getAddress(), student.getBDay(), student.getStatus());
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<String> validateBook(TableModels.Book book){
        if (book == null) {
            int errorcount = 0;
            List<String> newBookVal =
                    extractValues(txtFieldBookDesc1, txtFieldBookAuth1, txtFieldBookPub1,
                            datePickerBookDatePublished1, txtFieldBookPrice1, txtFieldBookISBN1, cmbBoxBookStatus1
                    );
            System.out.println("Extracted");
            System.out.println(newBookVal);

            if (!isValidDescription(newBookVal.get(0))) {
                showAlert(warning, "Invalid Input", "Invalid DESCRIPTION. Please enter a valid Description.");
                errorcount = 1;
            }

            if (!isValidDescription(newBookVal.get(1))) {
                showAlert(warning, "Invalid Input", "Invalid NAME. Please enter a valid name of author.");
                errorcount = 1;
            }

            if (!isValidDescription(newBookVal.get(2))) {
                showAlert(warning, "Invalid Input", "Invalid PUBLISHER. Please enter a valid publisher.");
                errorcount = 1;
            }
            if (!isValidNumber(newBookVal.get(4))) {
                showAlert(warning, "Invalid Input", "Invalid PRICE. Please enter a valid price.");
                errorcount = 1;
            }

            if (!isValidISBN(newBookVal.get(5))) {
                showAlert(warning, "Invalid Input", "Invalid ISBN. Please enter a valid ISBN.");
                errorcount = 1;
            }
            if (errorcount == 1) {
                throw new RuntimeException();
            }
            newBookVal.add(0, lblBookSelectedID4.getText());
            return newBookVal;
        }
        else{
            return Arrays.asList(book.getBookId(), book.getDescription(), book.getAuthor(),
                    book.getPublisher(), book.getDatePublished(), book.getPrice().toString(),
                    book.getIsbn(), book.getStatus());
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<String> validateEmployee(TableModels.Employee employee){
        if (employee == null) {
            int errorcount = 0;
            List<String> newEmployeeVal =
                    extractValues(txtEmployeeFN2, txtEmployeeLN2, txtEmployeeEmail2,
                            cmbBoxEmployeeGender2, txtEmployeeCPNumber2, txtEmployeeListAddress2,
                            datePickerEmployeeBirthday2, comboBoxEmployeeStatus2
                    );

            if (!isValidName(newEmployeeVal.get(0)) || !isValidName(newEmployeeVal.get(1))) {
                showAlert(warning, "Invalid Input", "Invalid NAME. Please enter a valid name.");
                errorcount = 1;
            }

            if (!isValidEmail(newEmployeeVal.get(2))) {
                showAlert(warning, "Invalid Input", "Invalid EMAIL. Please enter a valid email address.");
                errorcount = 1;
            }

            if (!isValidNumber(newEmployeeVal.get(4))) {
                showAlert(warning, "Invalid Input", "Invalid CP NUMBER. Please enter a valid number.");
                errorcount = 1;
            }
            if (!isValidAddress(newEmployeeVal.get(5))) {
                showAlert(warning, "Invalid Input", "Invalid ADDRESS. Please enter a valid address.");
                errorcount = 1;
            }
            if (errorcount == 1) {
                throw new RuntimeException();
            }

            return newEmployeeVal;
        }
        else{
            return Arrays.asList( employee.getFirstName(), employee.getLastName(), employee.getEmail(),
                    employee.getGender(), employee.getCP_Num(), employee.getAddress(),
                    employee.getBDay(), employee.getStatus()) ;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<String> validateGuest(TableModels.Guest guest){
        if (guest == null) {
            int errorcount = 0;
            List<String> newEmployeeVal =
                    extractValues(txtGuestLN3, txtGuestFN3, txtGuestEmail3,
                            txtGuestCPNumber3, txtGuestAddress3, datePickerGuestBirthday3,
                            cmbBoxGuestGender3, cmbBoxGuestStatus3
                    );

            if (!isValidName(newEmployeeVal.get(0)) || !isValidName(newEmployeeVal.get(1))) {
                showAlert(warning, "Invalid Input", "Invalid NAME. Please enter a valid name.");
                errorcount = 1;
            }

            if (!isValidEmail(newEmployeeVal.get(2))) {
                showAlert(warning, "Invalid Input", "Invalid EMAIL. Please enter a valid email address.");
                errorcount = 1;
            }

            if (!isValidNumber(newEmployeeVal.get(4))) {
                showAlert(warning, "Invalid Input", "Invalid CP NUMBER. Please enter a valid number.");
                errorcount = 1;
            }
            if (!isValidAddress(newEmployeeVal.get(5))) {
                showAlert(warning, "Invalid Input", "Invalid ADDRESS. Please enter a valid address.");
                errorcount = 1;
            }
            if (errorcount == 1) {
                throw new RuntimeException();
            }

            return newEmployeeVal;
        }
        else{
            return Arrays.asList( guest.getFirstName(), guest.getLastName(), guest.getEmail(),
                    guest.getGender(), guest.getCP_Num(), guest.getAddress(),
                    guest.getBDay(), guest.getStatus()) ;
        }
    }
}