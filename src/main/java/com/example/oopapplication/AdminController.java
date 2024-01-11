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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class AdminController {


    @FXML       //
    private AnchorPane anchorPaneRoot, anchorPaneTaskbar, anchorPaneBG,
            anchorPaneMiniBooks, anchorPaneMiniPeople, anchorPaneMiniReality, anchorPaneMiniAccount,
            anchorPaneBooks, anchorPanePeople, anchorPaneReality, anchorPaneAccount

            ;

    @FXML       //
    private Button btnLogOut,
            btnCollegeAdd2, btnCollegeEdit2, btnCollegeDelete2, btnCollegeUndo2, btnCollegeSave2,

            btnCourseAdd2, btnCourseEdit2, btnCourseDelete2, btnCourseUndo2, btnCourseSave2,

            btnSlotAdd3, btnSlotEdit3, btnSlotDelete3, btnSlotUndo3, btnSlotSave3,

            btnSetDate, btnSetTime,

            btnAccountEdit, btnAccountCancel, btnAccountSave

            , btnXBooks, btnXPeople, btnXAccount, btnXReality

            ;

    @FXML       //
    private TabPane tabPaneBooks, tabPanePeople, tabPaneReality, tabPaneAccount

            ;

    @FXML       //
    private Label lblClock, lblDate, lblCurrentDate, lblCurrentTime, lblCurrentDay,

            lblPassword, lblPassword1, lblShow, lblShow1, lblAccountPassWarning,
            lblBookSelectedID4, lblSelectedID3 , lblStudentListSelectedID1,
            lblApptSelectedID1, lblBookSelectedID1,
            lblBookSelectedID1X, lblApptSelectedID1X,
            lblApptSelectedID2X, lblBookSelectedID2X,
            lblApptSelectedID3X, lblBookSelectedID3X, lblBookSelectedID4X,
            lblAccountAddress, lblAccountBirthday, lblAccountCPNumber, lblAccountEmail, lblAccountFN, lblAccountGender, lblAccountID,
            lblAccountLN, lblAccountUN
            ;

    @FXML       //
    private TextField

            txtAccPassword1, txtAccPassword,

            txtFieldBookDesc1, txtFieldBookAuth1, txtFieldBookPub1,txtFieldBookPrice1, txtFieldBookISBN1,
            txtUsername4, txtUserLN4, txtUserFN4,
            txtStudentListLN1, txtStudentListFN1, txtStudentListEmail1, txtStudentListCPNumber1, txtStudentListAddress1,

            txtSlotTimeOut3, txtSlotTimeIn3, txtSetTime,

            txtSelectedSY1, txtSelectedSEM1,

            txtGuestLN3, txtGuestFN3, txtGuestEmail3, txtGuestCPNumber3, txtGuestAddress3,
            txtFieldBookAuth,
            txtEmployeeLN2, txtEmployeeListAddress2, txtEmployeeFN2, txtEmployeeEmail2, txtEmployeeCPNumber2,
            txtCollegeCode2, txtCollegeDescription2,

            txtAccountID, txtAccountUN, txtAccountFN, txtAccountLN,  txtAccountEmail, txtAccountCPNumber,
                txtAccountAddress
            ;

    @FXML private ComboBox<String> cmbBoxAccountGender, cmbBoxBookStatus1, cmbBoxCollegeStatus2, cmbBoxCourseStatus2
            , cmbBoxEmployeeGender2, cmbBoxGuestGender3, cmbBoxStudentListCourseCode1, cmbBoxStudentListStatus1
            , cmbBoxUserType4, combBoxStudentListGender1, comboBoxEmployeeStatus2;


    @FXML       //
    private DatePicker datePickerCollegeDateOpened2, datePickerCollegeDateClosed2,
            datePickerBirthday, txtSetDate, datePickerBookDatePublished1, datePickerStudentListBirthday1

            ;

    @FXML PasswordField passAccPassword, passAccPassword1;

    @FXML Group grpSetPass, grpConfirmPass;

    @FXML private TextArea terminalSemSY, terminalCollege, terminalCourses;


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

////    @FXML TableView<TableModels.Users> tblUserList;
////        @FXML TableColumn<TableModels.Users, String> tblColUserID4, tblColUsername4, tblColUserLN4,
////                tblColUserFN4;
//        @FXML TableColumn<TableModels.Users, Double> tblColUserFine;

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
                tblColReqStatus1;

    @FXML TableView<TableModels.PendingAppts> tblApptReservation;
        @FXML TableColumn<TableModels.PendingAppts, String> tblColApptID1, tblColSlotID1, tblColDate1, tblColDay1,
                tblColVisitorID1, tblColApptFN1, tblColApptLN1, tblColApptReqDate1, tblColApptReqStatus1;

    @FXML TableView<TableModels.Book> tblAccountBookReservations;
        @FXML TableColumn<TableModels.Book, String> tblColAccountBookReservationID2, tblColAccountBookRequestStatus2,
                tblColAccountBookApprovalDate2, tblColAccountBookSY2, tblColAccountBookSem2, tblColAccountBookStudentNo2,
                tblColAccountBookID2, tblColAccountBookDateReserved2, tblColAccountBookDateReturned2, tblColAccountBookRemark2;
        @FXML TableColumn<TableModels.Book, Double> tblColAccountBookFine2;

    @FXML TableView<TableModels.PendingAppts> tblAccountPastAppts;
        @FXML TableColumn<TableModels.PendingAppts, String> tblColAccountApptID2, tblColAccountApptRequestStatus2,
                tblColAccountApptApprovalDate2, tblColAccountApptApprovalSY2, tblColAccountApptSem2, tblColAccountApptVisitorID2,
                tblColAccountApptSlotID2, tblColAccountApptDay2, tblColAccountApptDate2, tblColAccountApptTimeIn2,
                tblColAccountApptTimeOut2, tblColAccountApptRemark2;

    @FXML TableView<TableModels.PastBooks> tblPastBooks;
        @FXML TableColumn<TableModels.PastBooks, String> tblColReservationID3, tblColBookSY3, tblColBookSemester3,
                tblColStudentNo3, tblColBookID3, tblColDateReserved3, tblColBookDateReturned3, tblColTargetDate3,
                tblColReservation_Status3, tblColBookLibrarian3, tblColBookRemark3, tblColBookRequestTime3,
                tblColBookApprovalTime3, tblColBookApproverID3, tblColBookFine3;

    @FXML TableView<TableModels.CurrentBooks> tblCurrentBooks;
        @FXML TableColumn<TableModels.CurrentBooks, String> tblColReservationID2, tblColBookID2, tblColBookTitle2,
                tblColDateReserved2, tblColTargetDate2, tblColStudentNo2, tblColBookFN2, tblColBookLN2,
                tblColBookStatus2, tblColBookApproval2;

    @FXML TableView<TableModels.CurrentAppts> tblCurrentAppts;
        @FXML TableColumn<TableModels.CurrentAppts, String> tblColApptID2, tblColSlotID2,
                tblColDate2, tblColDay2, tblColTimeIn2, tblColTimeOut2, tblColVisitorID2,
                tblColApptFN2, tblColApptLN2, tblColApptStatus2, tblColApptApproval2;

    @FXML TableView<TableModels.PastAppts> tblPastAppts;
        @FXML TableColumn<TableModels.PastAppts, String> tblColApptID3, tblColApptSY3, tblColApptSem3,
                tblColVisitorID3, tblColSlotID, tblColApptDay3, tblColDate3, tblColTimeIn3, tblColTimeOut3
                , tblColApptStatus3, tblColApptLibrarian3, tblColApptRemark3, tblColApptRequestDate3,
                tblColApptApprovalTime3, tblColApptApproverID3;


    public  final  List<String> STUDENT_COLS = Arrays.asList("Student_No", "LastName", "FirstName", "Email",
            "Gender" , "Course_Code", "CP_Num", "Address", "BDay", "Status");
    public final  List<String> COLLEGE_COLS = Arrays.asList("College_Code", "Description", "Date_Opened", "Date_Closed"
            , "Status");
    public final  List<String> COURSE_COLS = Arrays.asList("Course_Code", "College_Code", "Description", "Date_Opened",
            "Date_Closed" , "Status");
    public final  List<String> BOOK_REQS_COLS = Arrays.asList("Reservation_ID", "Book_ID", "Book_title",  "Student_No",
            "User_FirstName", "User_LastName", "Request_DateTime", "Transaction_Status");
    public  final  List<String> PENDING_APPTS_COLS = Arrays.asList("Appointment_ID", "Slot_ID", "Date", "Day", "Visitor_ID",
            "FirstName", "LastName", "Request_DateTime", "Transaction_Status");
    public final  List<String> CURRENT_BOOKS_COLS = Arrays.asList("Reservation_ID", "Book_ID", "Book_title",  "Date_Reserved",
            "Target_Return_Date", "Student_No", "User_FirstName", "User_LastName", "Transaction_Status", "Approval_DateTime");

    public final  List<String> CURRENT_APPT_COLS = Arrays.asList("Appointment_ID", "Slot_ID", "Date",  "Day",
        "Time_In", "Time_Out", "Visitor_ID", "FirstName", "LastName", "Transaction_Status", "Approval_DateTime");

    public final  List<String> PAST_BOOK_COLS = Arrays.asList("Reservation_ID", "SY", "Semester",  "Student_No",
            "Book_ID", "Date_Reserved", "Date_Returned", "Target_Return_Date", "Transaction_Status", "Fine",
            "Librarian_ID", "Librarian_Remark", "Request_DateTime", "Approval_DateTime"
            , "Approver_ID"
    );
    private final  List<String> PAST_APPTS_COL = Arrays.asList("Appointment_ID", "SY", "Semester",  "Visitor_ID",
            "Slot_ID", "Day", "Date", "Time_In", "Time_Out", "Transaction_Status",
            "Librarian_ID", "Librarian_Remark", "Request_DateTime", "Approval_DateTime"
            , "Approver_ID");
    private String[] statusBooks = {"A", "R", "X"};
    private String[] status = {"A", "I"};
    private String[] gender = {"M", "F"};

    DataBaseMethods DB = new DataBaseMethods();

    MainMenuController mainMenuController = new MainMenuController();

    private String userID = getUserID();

    public String getUserID() {  return this.userID;     }

    public void setUserID(String userID) {  this.userID = userID;
    refreshAccount();}

    public Savepoint savepoint1;
    public String currentSY = getCurrentSYSEM("SY"),
            currentSem = getCurrentSYSEM("Sem");

    public String getCurrentSYSEM(String choice){
        if(choice.equals("Sem"))
            return this.currentSem;
        else
            return this.currentSY;
    }

    public void setCurrentSYSEM(){
        this.currentSem = DB.currentSYSEM("Sem");
        this.currentSY = DB.currentSYSEM("SY");
    }
    ConnectDB db = new ConnectDB();
    Connection conn = db.Connect();
    ResultSet rs = null;
    PreparedStatement ps = null;

    Alert warningError = new Alert((Alert.AlertType.WARNING));
    public void initialize(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        DataBaseMethods.populateCmbBox("Student", "Course_Code", cmbBoxStudentListCourseCode1);
        cmbBoxBookStatus1.getItems().addAll(statusBooks);
        cmbBoxAccountGender.getItems().addAll(gender);
        combBoxStudentListGender1.getItems().addAll(gender);
        cmbBoxStudentListStatus1.getItems().addAll(status);

        setCurrentSYSEM();


    //tblSemester, tblSY        List of Semesters/SY        TableModels.SYSEM
        tblColSemester1.setCellValueFactory(cellData -> cellData.getValue().sysemProperty());
        tblColSY1.setCellValueFactory(cellData -> cellData.getValue().sysemProperty());

        //populate
        DB.populateSYSEM(tblSemester, "Semester");
        DB.populateSYSEM(tblSY, "SY");

        //listener


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
        ObservableList<TableModels.College> collegeDataList = FXCollections.observableArrayList();
        DataBaseMethods.populate("College", COLLEGE_COLS, collegeDataList,tblCollege, TableModels.College.class,"");

        //listener


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
        ObservableList<TableModels.Courses> courseDataList = FXCollections.observableArrayList();
        DataBaseMethods.populate("Course", COURSE_COLS, courseDataList, tblCourse, TableModels.Courses.class, "");

        //listener

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
        ObservableList<TableModels.Student> studentList = FXCollections.observableArrayList();
        DataBaseMethods.populate("Student", STUDENT_COLS, studentList, tblStudentList,
                TableModels.Student.class, "");


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
        DB.populateEmployees(tblEmployeeList);

        //listener



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
        DB.populateGuests(tblGuestList);

        //listener



    //tblUserList               List of Registered Users     TableModels.Users  extends PeopleInfo
//        tblColUserID4.setCellValueFactory(cellData -> cellData.getValue().getUserIDProperty());
//        //tblColUserType4.setCellValueFactory(cellData -> cellData.getValue().getUserTypeProperty());
//        tblColUsername4.setCellValueFactory(cellData -> cellData.getValue().getUsernameProperty());
//        tblColUserLN4.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
//        tblColUserFN4.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
//
//        //cell + column wraps
//
//
//        //populate
//        DB.populateUsers(tblUserList);
//
//        //listener



    //tblSlot                   Slot Infos              TableModels.SlotData
        tblColSlotID3.setCellValueFactory(cellData -> cellData.getValue().getSlotIDProperty());
        tblColSlotDay3.setCellValueFactory(cellData ->cellData.getValue().getDayProperty());
        tblColSlotTimeIn3.setCellValueFactory(cellData -> cellData.getValue().getTimeInProperty());
        tblColSlotTimeOut3.setCellValueFactory(cellData -> cellData.getValue().getTimeOutProperty());
        tblColSlotLimit3.setCellValueFactory(cellData -> cellData.getValue().getSlotLimitProperty().asObject());

        //cell + column wraps

        //populate
        DB.populateSlot(tblSlot, 1);

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
        DB.populateBook(tblBookList);

        //listener
        tblBookList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> bookListener(newValue));


    //tblBookReservations       Pending Book Reservations       TableModels.PendingBooks
        tblColReservationID1.setCellValueFactory(cellData->cellData.getValue().getReservationIDProperty());
        tblColBookID1.setCellValueFactory(cellData-> cellData.getValue().getBookIDProperty());
        tblColBookTitle1.setCellValueFactory(cellData -> cellData.getValue().getBookTitleProperty());
        tblColReqStatus1.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());
        tblColStudentNo1.setCellValueFactory(cellData -> cellData.getValue().getStudent_NoProperty());
        tblColBookFN1.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblColBookLN1.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblColReqDate1.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());


        //cell + column wraps
        TableModels.wrapColumnHeader(tblColReservationID1);
        TableModels.wrapColumnHeader(tblColReqStatus1);
        TableModels.wrapColumnHeader(tblColReqDate1);
        TableModels.wrapColumnHeader(tblColStudentNo1);
        TableModels.textWrap(tblColBookTitle1);

        //populate
//        final  List<String> BOOK_REQS_COLS = Arrays.asList("Reservation_ID", "Book_ID", "Book_title",  "Student_No",
//                "User_FirstName", "User_LastName", "Request_DateTime", "Transaction_Status");
        ObservableList<TableModels.PendingBooks> pendingBooks = FXCollections.observableArrayList();
        DataBaseMethods.populate("vwPendingBookRequests", BOOK_REQS_COLS, pendingBooks, tblBookReservations,
                TableModels.PendingBooks.class, "");

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
        tblColApptReqDate1.setCellValueFactory(cellData-> cellData.getValue().getDateTimeProperty());
        tblColApptReqStatus1.setCellValueFactory(cellData ->cellData.getValue().getTransaction_StatusProperty());

        //cell + column wraps


        //populate
//        final  List<String> PENDING_APPTS_COLS = Arrays.asList("Appointment_ID", "Slot_ID", "Date", "Day", "Visitor_ID",
//                "FirstName", "LastName", "Request_DateTime", "Transaction_Status");
        ObservableList<TableModels.PendingAppts> pendingAppts = FXCollections.observableArrayList();
        DataBaseMethods.populate("vwPendingAppointments", PENDING_APPTS_COLS, pendingAppts, tblApptReservation,
                TableModels.PendingAppts.class, "");

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
        tblColBookApproval2.setCellValueFactory(cellData -> cellData.getValue().getApproval_DateTimeProperty());

        //cell + column wraps
        TableModels.wrapColumnHeader(tblColReservationID2);
        TableModels.textWrap(tblColBookTitle2);
        TableModels.textWrap(tblColBookStatus2);
        tblCurrentBooks.getColumns().forEach(column -> column.setReorderable(false));


        //populate
//        final  List<String> CURRENT_BOOKS_COLS = Arrays.asList("Reservation_ID", "Book_ID", "Book_title",  "Date_Reserved",
//                "Target_Return_Date", "Student_No", "User_FirstName", "User_LastName", "Transaction_Status", "Approval_DateTime");
        ObservableList<TableModels.CurrentBooks> currentBooks = FXCollections.observableArrayList();
        DataBaseMethods.populate("vwCurrentBookRequests",CURRENT_BOOKS_COLS, currentBooks, tblCurrentBooks,
                TableModels.CurrentBooks.class, " WHERE Transaction_Status LIKE 'ongoing%';" );

        //listener



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

        //cell + column wraps


        //populate
//        final  List<String> CURRENT_APPT_COLS = Arrays.asList("Appointment_ID", "Slot_ID", "Date",  "Day",
//                "Time_In", "Time_Out", "Visitor_ID", "FirstName", "LastName", "Transaction_Status", "Approval_DateTime");
        ObservableList<TableModels.CurrentAppts> currentAppts = FXCollections.observableArrayList();
        DataBaseMethods.populate("vwCurrentAppointments", CURRENT_APPT_COLS, currentAppts, tblCurrentAppts
        , TableModels.CurrentAppts.class, "");


        //listener



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
        tblColBookApprovalTime3.setCellValueFactory(cellData -> cellData.getValue().getApproval_DateTimeProperty());
        tblColBookApproverID3.setCellValueFactory(cellData -> cellData.getValue().getApprover_IDProperty());

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
        TableModels.textWrap(tblColBookSY3);
        TableModels.textWrap(tblColBookRemark3);
        TableModels.textWrap(tblColBookRequestTime3);
        TableModels.textWrap(tblColBookApprovalTime3);


        //populate
//        final  List<String> PAST_BOOK_COLS = Arrays.asList("Reservation_ID", "SY", "Semester",  "Student_No",
//                "Book_ID", "Date_Reserved", "Date_Returned", "Target_Return_Date", "Transaction_Status", "Fine",
//                "Librarian_ID", "Librarian_Remark", "Request_DateTime", "Approval_DateTime"
//                , "Approver_ID"
//                );
        ObservableList<TableModels.PastBooks> pastBooksList = FXCollections.observableArrayList();
        DataBaseMethods.populate("Book_Reservation", PAST_BOOK_COLS, pastBooksList, tblPastBooks, TableModels.PastBooks.class
                , " Where Transaction_Status like 'completed%' or Transaction_Status = 'declined';");

        //listener



    //tblPastAppts              Past Appointments           TableModels.PastAppts   extends CurrentAppts
        tblColApptID3.setCellValueFactory(cellData -> cellData.getValue().getApptIDProperty());
        tblColApptSY3.setCellValueFactory(cellData -> cellData.getValue().getSYProperty());
        tblColApptSem3.setCellValueFactory(cellData -> cellData.getValue().getSemesterProperty());
        tblColVisitorID3.setCellValueFactory(cellData -> cellData.getValue().getVisitor_IDProperty());
        tblColSlotID.setCellValueFactory(cellData -> cellData.getValue().getSlotIDProperty());
        tblColApptDay3.setCellValueFactory(cellData -> cellData.getValue().getDayProperty());
        tblColDate3.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
        tblColTimeIn3.setCellValueFactory(cellData -> cellData.getValue().getTime_InProperty());
        tblColTimeOut3.setCellValueFactory(cellData -> cellData.getValue().getTime_OutProperty());
        tblColApptStatus3.setCellValueFactory(cellData -> cellData.getValue().getTransaction_StatusProperty());
        tblColApptLibrarian3.setCellValueFactory(cellData -> cellData.getValue().getLibrarian_IDProperty());
        tblColApptRemark3.setCellValueFactory(cellData -> cellData.getValue().getLibrarian_RemarkProperty());
        tblColApptRequestDate3.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
        tblColApptApprovalTime3.setCellValueFactory(cellData -> cellData.getValue().getApproval_DateTimeProperty());
        tblColApptApproverID3.setCellValueFactory(cellData -> cellData.getValue().getApprover_IDProperty());

        //cell + column wraps


        //populate
//        final  List<String> PAST_APPTS_COL = Arrays.asList("Appointment_ID", "SY", "Semester",  "Visitor_ID",
//                "Slot_ID", "Day", "Date", "Time_In", "Time_Out", "Transaction_Status",
//                "Librarian_ID", "Librarian_Remark", "Request_DateTime", "Approval_DateTime"
//                , "Approver_ID");

        ObservableList<TableModels.PastAppts> pastApptsCols = FXCollections.observableArrayList();
        DataBaseMethods.populate("Appointment", PAST_APPTS_COL, pastApptsCols, tblPastAppts, TableModels.PastAppts.class
                , " Where Transaction_Status like 'completed%' or Transaction_Status = 'declined';");


        //listener



    //tblAccountBookReservations        Account Past Book Transactions      TableModels.Book


        //cell + column wraps


        //populate


        //listener



    //tblAccountPastAppts              Past Appointments            TableModels.PendingAppts


        //cell + column wraps


        //populate


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
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> mainMenuController.updateDateTimeLabels(lblClock, lblDate, null)));
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event -> mainMenuController.updateDateTimeLabels(lblCurrentTime, lblCurrentDate, lblCurrentDay)));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
        timeline.play();



    }

    public void pendingApptsListener(TableModels.PendingAppts pendingAppts){
        if(pendingAppts != null){
            lblApptSelectedID1.setText(pendingAppts.getAppointment_ID());
        }
        else{
            lblApptSelectedID1.setText("");
        }
    }
    public void pendingBooksListener(TableModels.PendingBooks pendingBooks){
        if(pendingBooks != null){
            lblBookSelectedID1.setText(pendingBooks.getReservation_ID());
        }
        else{
            lblBookSelectedID1.setText("");
        }
    }

    public void slotListener(TableModels.SlotData slot){////////////////////////////////////////////////////////////////////
        if(slot != null){
                lblSelectedID3.setText(slot.getID() + ", " + slot.getDay());
                txtSlotTimeIn3.setText(slot.getTimeIn());
                txtSlotTimeOut3.setText(slot.getTimeOut());
            }
            else{
                lblSelectedID3.setText("");
                txtSlotTimeOut3.clear();
                txtSlotTimeIn3.clear();
            }
    }
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
        } else {

            lblBookSelectedID4.setText("");
            txtFieldBookDesc1.clear();
            txtFieldBookAuth1.clear();
            txtFieldBookPub1.clear();
            txtFieldBookPrice1.clear();
            txtFieldBookISBN1.clear();
            datePickerBookDatePublished1.setValue(null);
            cmbBoxBookStatus1.setValue(null);
        }
    }

    public void studentListener(TableModels.Student student){
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

            lblStudentListSelectedID1.setText(" ");
            txtStudentListFN1.clear();
            txtStudentListLN1.clear();
            txtStudentListEmail1.clear();
            txtStudentListCPNumber1.clear();
            txtStudentListAddress1.clear();

            combBoxStudentListGender1.setValue(null);
            cmbBoxStudentListCourseCode1.setValue(null);

            datePickerStudentListBirthday1.setValue(null);

        }

    }
    public void tglEdit(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(btnAccountEdit.isVisible()){
            btnAccountEdit.setVisible(false);

            lblPassword.setVisible(true);
            lblPassword1.setVisible(true);
            grpSetPass.setVisible(true);
            grpConfirmPass.setVisible(true);
            btnAccountCancel.setVisible(true);
            btnAccountSave.setVisible(true);
        }
        else{
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

    public void addtoDB(MouseEvent event){
        lblStudentListSelectedID1.setText("Adding...");

        Map<String, Object> studentDataMap = createHashMap(
                STUDENT_COLS,
                createValuesList(
                        lblStudentListSelectedID1, txtStudentListFN1,
                        txtStudentListLN1, txtStudentListEmail1, combBoxStudentListGender1,
                        cmbBoxStudentListCourseCode1, txtStudentListCPNumber1, txtStudentListAddress1,
                        datePickerStudentListBirthday1, cmbBoxStudentListStatus1)
        );

            if (!containsNullValue(studentDataMap)) {
                try {
                    if(conn.getAutoCommit()){
                        conn.setAutoCommit(false);
                        savepoint1 = new Savepoint() {
                            @Override
                            public int getSavepointId() throws SQLException {
                                return 0;
                            }

                            @Override
                            public String getSavepointName() throws SQLException {
                                return null;
                            }
                        };
                        savepoint1 = conn.setSavepoint();

                        System.out.println(savepoint1.getSavepointId());
                        System.out.println(savepoint1.getSavepointName());
                        System.out.println(savepoint1);

                    }

                    }catch (SQLException e) {

                    }

                    lblStudentListSelectedID1.setText(DataBaseMethods.getNextID("Student", "Student_No", 2, ""));
                    DataBaseMethods.insertIntoDB("Student", createHashMap(STUDENT_COLS, createValuesList(
                            lblStudentListSelectedID1, txtStudentListFN1,
                            txtStudentListLN1, txtStudentListEmail1, combBoxStudentListGender1,
                            cmbBoxStudentListCourseCode1, txtStudentListCPNumber1, txtStudentListAddress1
                            , datePickerStudentListBirthday1, cmbBoxStudentListStatus1)
                    ));


            } else {
                showWarningAlert(warningError, "Warning!!!",
                        "Please Fill out all the Fields");
            }




    }

    public void saveEdit(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (!passAccPassword1.getText().equals(passAccPassword.getText()) || !txtAccPassword.getText().equals(txtAccPassword1.getText())){
            lblAccountPassWarning.setVisible(true);
        }
        else {
            final List<String> EMPLOYEE_COLUMN_NAMES = Arrays.asList("FirstName", "LastName", "Gender", "Email", "CP_Num", "Address", "BDay");

            String newUn = txtAccountUN.getText();

            List<Object> newVals = Arrays.asList(txtAccountFN.getText(), txtAccountLN.getText(), cmbBoxAccountGender.getValue(), txtAccountEmail.getText()
                    , txtAccountCPNumber.getText(), txtAccountAddress.getText(), datePickerBirthday.getValue().toString());
            setUserID(txtAccountID.getText());

            System.out.println("New Vals = " + newVals);
            DB.updateDB("Employee", newUn, getUserID(), EMPLOYEE_COLUMN_NAMES, newVals, "Employee_ID", passAccPassword1.getText());
            tglEdit();
            refreshAccount();
            passAccPassword.clear();
            passAccPassword1.clear();
            txtAccPassword.clear();
            txtAccPassword1.clear();
            lblAccountPassWarning.setVisible(false);
        }
    }

    public void cancelEdit(){/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        refreshAccount();
        lblAccountPassWarning.setVisible(false);
        passAccPassword.clear();
        passAccPassword1.clear();
        txtAccPassword.clear();
        txtAccPassword1.clear();
        tglEdit();
    }
    public void refreshAccount(){///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DB.setBDay(getUserID(), datePickerBirthday);
        DB.toText("User_ID", txtAccountID, getUserID(), null);
        DB.toText("Username", txtAccountUN, getUserID(), null);
        DB.toText("FirstName", txtAccountFN, getUserID(), null);
        DB.toText("LastName", txtAccountLN, getUserID(), null);
        DB.toCmbBox("vwUserInfo", "Gender", getUserID(),cmbBoxAccountGender);
        DB.toText("Email", txtAccountEmail, getUserID(), null);
        DB.toText("CP_Num", txtAccountCPNumber, getUserID(), null);
        DB.toText("Address", txtAccountAddress, getUserID(), null);

    }

    public void setDate(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return;

    }
    public void setTime(){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return;
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
    }

    public void LogOut(MouseEvent event){////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.change("login.fxml", anchorPaneRoot);
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

    public void showPass(MouseEvent event){
        var label = (Label) event.getSource();
        var str = label.getText();
        var txtField = (TextField) label.getUserData();
        var passField = (PasswordField) txtField.getUserData();

        if(str.equals("Show")){
            txtField.setText(passField.getText());
            txtField.setVisible(true);
            passField.setVisible(false);
            label.toFront();
            label.setText("Hide");
        }
        else {
            txtField.setVisible(false);
            passField.setVisible(true);
            passField.setText(txtField.getText());
            label.setText("Show");
        }
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


    public static Map<String, Object> createHashMap(List<String> keys, List<?> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values lists must have the same size");
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }


    public static List<Object> createValuesList(Control... controls) {
        return Arrays.stream(controls)
                .map(AdminController::extractValue)
                .collect(Collectors.toList());
    }

    private static Object extractValue(Control control) {
        if (control instanceof TextField) {
            return ((TextField) control).getText();
        } else if (control instanceof ComboBox) {
            return ((ComboBox<?>) control).getValue();
        } else if (control instanceof Label) {
            return ((Label) control).getText();
        } else if (control instanceof DatePicker) {
            return ((DatePicker) control).getValue();
        } else {
            // Handle other control types or throw an exception if needed
            throw new IllegalArgumentException("Unsupported control type: " + control.getClass());
        }
    }

    private boolean containsNullValue(Map<String, Object> map) {
        return map.values().stream().anyMatch(value ->
                value == null || (value instanceof String && ((String) value).trim().isEmpty()));

    }

    private void showWarningAlert(Alert alert, String title, String ContentText){
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(ContentText);
        Optional<ButtonType> result = alert.showAndWait();
    }


}
