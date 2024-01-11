package com.example.oopapplication;
import javafx.beans.property.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class TableModels {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class SYSEM {
        private final StringProperty sysem;

        public SYSEM(String sysem) {
            this.sysem = new SimpleStringProperty(sysem);
        }

        public StringProperty sysemProperty() {
            return sysem;
        }

        public String getSy() {
            return sysem.get();
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class PeopleInfo{
        private final StringProperty LastName, FirstName, Email, Gender, CP_Num, Address, BDay, Status;

        public PeopleInfo(String lastName, String firstName, String email, String gender, String CP_Num, String address, String bDay, String status){
            this.LastName = new SimpleStringProperty(lastName);
            this.FirstName = new SimpleStringProperty(firstName);
            this.Email = new SimpleStringProperty(email);
            this.Gender = new SimpleStringProperty(gender);
            this.CP_Num = new SimpleStringProperty(CP_Num);
            this.Address = new SimpleStringProperty(address);
            this.BDay = new SimpleStringProperty(bDay);
            this.Status = new SimpleStringProperty(status);
        }

        public PeopleInfo(){ this (null, null, null,
                null, null, null, null, null);}
        public void setLastName(String LastName){this.LastName.set(LastName);}
        public void setFirstName(String FirstName){this.FirstName.set(FirstName);}
        public void setEmail(String Email){this.Email.set(Email);}
        public void setGender(String Gender){this.Gender.set(Gender);}
        public void setCP_Num(String CP_Num){this.CP_Num.set(CP_Num);}
        public void setAddress(String Address){this.Address.set(Address);}
        public void setBDay(String BDay){this.BDay.set(BDay);}
        public void setStatus(String Status){this.Status.set(Status);}


        public StringProperty getLastNameProperty(){ return LastName; }
        public StringProperty getFirstNameProperty(){ return FirstName; }
        public StringProperty getEmailProperty(){ return Email; }
        public StringProperty getGenderProperty(){ return Gender; }
        public StringProperty getCpNumberProperty(){ return CP_Num; }
        public StringProperty getAddressProperty(){ return Address; }
        public StringProperty getBDayProperty(){ return BDay; }
        public StringProperty getStatusProperty(){ return Status; }

    public String getLastName(){ return LastName.get(); }
    public String getFirstName(){ return FirstName.get(); }
    public String getEmail(){ return Email.get(); }
    public String getGender(){ return Gender.get(); }
    public String getCP_Num(){ return CP_Num.get(); }
    public String getAddress(){ return Address.get(); }
    public String getBDay(){ return BDay.get(); }
    public String getStatus(){ return Status.get(); }

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Student extends PeopleInfo {
    private final StringProperty Student_No;
    private final StringProperty Course_Code;
    public Student(String Student_No, String lastName, String firstName, String email, String gender, String Course_Code, String cpNumber, String address, String bDay, String status) {
        super(lastName, firstName, email, gender, cpNumber, address, bDay, status);
        this.Student_No = new SimpleStringProperty(Student_No);
        this.Course_Code = new SimpleStringProperty(Course_Code);
    }

    public Student(){this(null, null, null, null, null,
            null, null, null, null, null);}
    public void setStudent_No(String Student_No){ this.Student_No.set(Student_No);}
    public void setCourse_Code(String Course_Code){ this.Course_Code.set(Course_Code);}
    public StringProperty getStudentNoProperty(){ return Student_No; }
    public StringProperty getCourseCodeProperty(){ return Course_Code;}
    public String getStudent_No(){ return Student_No.get(); }
    public String getCourse_Code(){ return Course_Code.get();}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Employee extends PeopleInfo{
    private final StringProperty employeeID;
    public Employee(String employeeID, String lastName, String firstName, String email, String gender, String cpNumber, String address, String bDay, String status) {
        super(lastName, firstName, email, gender, cpNumber, address, bDay, status);
        this.employeeID = new SimpleStringProperty(employeeID);
    }
    public  StringProperty getEmployeeIDProperty(){ return employeeID; }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Guest extends PeopleInfo{
    private final StringProperty guestID;
    public Guest(String guestID, String lastName, String firstName, String email, String gender, String cpNumber, String address, String bDay, String status) {
        super(lastName, firstName, email, gender, cpNumber, address, bDay, status);
        this.guestID = new SimpleStringProperty(guestID);
    }
    public StringProperty getGuestIDProperty(){ return guestID;}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Users extends PeopleInfo {
    private final StringProperty userID, username, password, userType;

    public Users(String userID, String username, String password, String lastName, String firstName,
                 String email, String gender, String cpNumber, String address, String bDay, String status, String userType) {
        super(lastName, firstName, email, gender, cpNumber, address, bDay, status);
        this.userID = new SimpleStringProperty(userID);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.userType = new SimpleStringProperty(userType);
    }
    public StringProperty getUserIDProperty(){ return userID; }
    public StringProperty getUsernameProperty(){ return username; }
    public StringProperty getPasswordProperty(){ return password; }
    public StringProperty getUserTypeProperty() { return userType; }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class PendingBooks{
        private final StringProperty Reservation_ID, Book_ID, Book_title, Student_No,
                User_FirstName, User_LastName, Request_DateTime, Transaction_Status;

    public PendingBooks(String Reservation_ID, String Book_ID, String Book_title,
                        String Student_No, String User_FirstName, String User_LastName,
                        String Request_DateTime, String Transaction_Status) {
        this.Reservation_ID = new SimpleStringProperty(Reservation_ID);
        this.Book_ID = new SimpleStringProperty(Book_ID);
        this.Book_title = new SimpleStringProperty(Book_title);
        this.Student_No = new SimpleStringProperty(Student_No);
        this.User_FirstName = new SimpleStringProperty(User_FirstName);
        this.User_LastName = new SimpleStringProperty(User_LastName);
        this.Request_DateTime = new SimpleStringProperty(Request_DateTime);
        this.Transaction_Status = new SimpleStringProperty(Transaction_Status);

    }

    public PendingBooks() {
        this(null, null, null, null, null, null, null, null);
    }

    public void setReservation_ID(String reservation_ID) { this.Reservation_ID.set(reservation_ID);  }
    public void setBook_ID(String Book_ID) { this.Book_ID.set(Book_ID);}
    public void setBook_title(String Book_title) { this.Book_title.set(Book_title);}
    public void setStudent_No(String Student_No) { this.Student_No.set(Student_No);}
    public void setUser_FirstName(String User_FirstName) { this.User_FirstName.set(User_FirstName);}
    public void setUser_LastName(String User_LastName) { this.User_LastName.set(User_LastName);}
    public void setRequest_DateTime(String Request_DateTime) { this.Request_DateTime.set(Request_DateTime);}
    public void setTransaction_Status(String Transaction_Status) { this.Transaction_Status.set(Transaction_Status);}

    public StringProperty getReservationIDProperty(){ return this.Reservation_ID;}
    public StringProperty getBookIDProperty(){ return this.Book_ID;}
    public StringProperty getBookTitleProperty(){ return this.Book_title;}
    public StringProperty getStudent_NoProperty(){ return this.Student_No;}
    public StringProperty getFirstNameProperty(){ return this.User_FirstName;}
    public StringProperty getLastNameProperty(){ return this.User_LastName;}
    public StringProperty getDateTimeProperty(){ return this.Request_DateTime;}
    public String getTransaction_Status(){ return this.Transaction_Status.get();}

    public String getReservation_ID(){ return this.Reservation_ID.get();}
    public String getBook_ID(){ return this.Book_ID.get();}
    public String getBook_title(){ return this.Book_title.get();}
    public String getStudent_No(){ return this.Student_No.get();}
    public String getUser_FirstName(){ return this.User_FirstName.get();}
    public String getUser_LastName(){ return this.User_LastName.get();}
    public String getRequest_DateTime(){ return this.Request_DateTime.get();}

    public StringProperty getTransaction_StatusProperty(){ return this.Transaction_Status;}


    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static class PendingAppts{
    private final StringProperty Appointment_ID, Slot_ID, Date, Day, Visitor_ID,
            FirstName, LastName, Request_DateTime, Transaction_Status;

    public PendingAppts(String Appointment_ID, String Slot_ID, String Date, String Day,
                        String Visitor_ID, String firstName, String lastName,
                        String request_DateTime, String Transaction_Status) {
        this.Appointment_ID = new SimpleStringProperty(Appointment_ID);
        this.Slot_ID = new SimpleStringProperty(Slot_ID);
        this.Date = new SimpleStringProperty(Date);
        this.Day = new SimpleStringProperty(Day);
        this.Visitor_ID = new SimpleStringProperty(Visitor_ID);
        this.FirstName = new SimpleStringProperty(firstName);
        this.LastName = new SimpleStringProperty(lastName);
        this.Request_DateTime = new SimpleStringProperty(request_DateTime);
        this.Transaction_Status = new SimpleStringProperty(Transaction_Status);
         }

    public PendingAppts() {
        this(null, null, null, null, null, null, null, null, null);
    }
    public StringProperty getApptIDProperty(){ return this.Appointment_ID;}
    public StringProperty getSlotIDProperty(){ return this.Slot_ID;}
    public StringProperty getDateProperty(){ return this.Date; }
    public StringProperty getDayProperty(){ return this.Day;}
    public StringProperty getVisitor_IDProperty(){ return this.Visitor_ID;}
    public StringProperty getFirstNameProperty(){ return this.FirstName;}
    public StringProperty getLastNameProperty(){ return this.LastName;}
    public StringProperty getDateTimeProperty(){ return this.Request_DateTime;}
    public StringProperty getTransaction_StatusProperty(){ return this.Transaction_Status;}


    public String getAppointment_ID(){ return this.Appointment_ID.get();}
    public String getSlot_ID(){ return this.Slot_ID.get();}
    public String getDate(){ return this.Date.get(); }
    public String getDay(){ return this.Day.get();}
    public String getVisitor_ID(){ return this.Visitor_ID.get();}
    public String getFirstName(){ return this.FirstName.get();}
    public String getLastName(){ return this.LastName.get();}
    public String getRequest_DateTime(){ return this.Request_DateTime.get();}
    public String getTransaction_Status(){ return this.Transaction_Status.get();}


    public void setAppointment_ID(String Appointment_ID) { this.Appointment_ID.set(Appointment_ID);  }
    public void setSlot_ID(String Slot_ID) { this.Slot_ID.set(Slot_ID);}
    public void setDate(String Date) {this.Date.set(Date);}
    public void setDay(String Day) { this.Day.set(Day);}
    public void setVisitor_ID(String Visitor_ID) { this.Visitor_ID.set(Visitor_ID);}
    public void setFirstName(String FirstName) { this.FirstName.set(FirstName);}
    public void setLastName(String LastName) { this.LastName.set(LastName);}
    public void setRequest_DateTime(String Request_DateTime) { this.Request_DateTime.set(Request_DateTime);}
    public void setTransaction_Status(String Transaction_Status) { this.Transaction_Status.set(Transaction_Status);}

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class CurrentAppts extends  PendingAppts{
        private final StringProperty Time_In, Time_Out, Approval_DateTime;


        public CurrentAppts(
                String Appointment_ID,
                String Slot_ID,
                String Date,
                String Day,
                String Visitor_ID,
                String firstName,
                String lastName,
                String Transaction_Status,
                String Request_DateTime,
                String timeIn, String timeOut, String Approval_DateTime) {
            super(Appointment_ID, Slot_ID, Date, Day,
                    Visitor_ID, firstName, lastName,
                    Request_DateTime, Transaction_Status);
            Time_In = new SimpleStringProperty(timeIn);
            Time_Out = new SimpleStringProperty(timeOut);
            this.Approval_DateTime = new SimpleStringProperty(Approval_DateTime);
        }

        CurrentAppts(){ this(null, null, null, null,
                null, null, null, null,
                null, null, null, null);}
        public void setTime_In(String time){this.Time_In.set(time);}
        public void setTime_Out(String Time_Out){this.Time_Out.set(Time_Out);}
        public void setApproval_DateTime(String Approval_DateTime){ this.Approval_DateTime.set(Approval_DateTime);}
        public StringProperty getTime_InProperty(){ return this.Time_In;}
        public StringProperty getTime_OutProperty(){ return this.Time_Out;}
        public StringProperty getApproval_DateTimeProperty(){ return this.Approval_DateTime;}
        public String getTime_In(){ return this.Time_In.get();}
        public String getTime_Out(){ return this.Time_Out.get();}
        public String getApproval_DateTime(){return this.Approval_DateTime.get();}
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class CurrentBooks extends PendingBooks{
    private final StringProperty Date_Reserved, Target_Return_Date, Approval_DateTime;

    public CurrentBooks(String Reservation_ID,
                        String Book_ID,
                        String Book_title,
                        String Student_No,
                        String User_FirstName,
                        String User_LastName,
                        String Request_DateTime,
                        String dateReserved,
                        String targetReturnDate, String Transaction_Status, String Approval_DateTime) {
        super(Reservation_ID, Book_ID, Book_title, Student_No,
                User_FirstName, User_LastName, Request_DateTime, Transaction_Status);
        Date_Reserved = new SimpleStringProperty(dateReserved);
        Target_Return_Date = new SimpleStringProperty(targetReturnDate);
        this.Approval_DateTime = new SimpleStringProperty(Approval_DateTime);
    }
    public CurrentBooks() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    public StringProperty getApproval_DateTimeProperty(){ return this.Approval_DateTime;}

    public StringProperty getDate_ReservedProperty(){ return this.Date_Reserved;}

    public String getDate_Reserved(){ return this.Date_Reserved.get();}
    public String getApproval_DateTime(){ return this.Approval_DateTime.get();}

    public void setDate_Reserved(String Date_Reserved) { this.Date_Reserved.set(Date_Reserved);  }

    public StringProperty getTarget_Return_DateProperty(){ return this.Target_Return_Date;}

    public String getTarget_Return_Date(){ return this.Target_Return_Date.get();}

    public void setTarget_Return_Date(String Target_Return_Date) { this.Target_Return_Date.set(Target_Return_Date);  }

    public void  setApproval_DateTime(String Approval_DateTime){ this.Approval_DateTime.set(Approval_DateTime);}

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class PastBooks extends PendingBooks{
        private final StringProperty SY, Semester, Date_Reserved, Date_Returned, Target_Return_Date
                , Librarian_ID, Librarian_Remark, Approval_DateTime, Approver_ID, Fine;


    public PastBooks(String Reservation_ID, String Book_ID, String Book_title,
                     String Student_No, String User_FirstName, String User_LastName,
                     String Request_DateTime,
                     String sy, String semester, String dateReserved,
                     String dateReturned, String targetReturnDate, String Transaction_Status,
                     String librarianId, String librarianRemark, String approvalDateTime,
                     String approverId, String fine
                      ) {
        super(Reservation_ID, Book_ID, Book_title, Student_No,
                User_FirstName, User_LastName, Request_DateTime, Transaction_Status);
        this.SY = new SimpleStringProperty(sy);
        this.Semester = new SimpleStringProperty(semester);
        this.Date_Reserved = new SimpleStringProperty(dateReserved);
        this.Date_Returned = new SimpleStringProperty(dateReturned);
        this.Target_Return_Date = new SimpleStringProperty(targetReturnDate);
        this.Librarian_ID = new SimpleStringProperty(librarianId);
        this.Librarian_Remark = new SimpleStringProperty(librarianRemark);
        this.Approval_DateTime = new SimpleStringProperty(approvalDateTime);
        this.Approver_ID = new SimpleStringProperty(approverId);
        this.Fine = new SimpleStringProperty(fine);
    }


    public PastBooks() {
        this(null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null
        );

    }

    public void setSY(String SY){this.SY.set(SY); }
    public void setSemester(String Semester){this.Semester.set(Semester); }
    public void setDate_Reserved(String Date_Reserved){this.Date_Reserved.set(Date_Reserved); }
    public void setDate_Returned(String Date_Returned){this.Date_Returned.set(Date_Returned); }
    public void setTarget_Return_Date(String Target_Return_Date){this.Target_Return_Date.set(Target_Return_Date); }
    public void setLibrarian_ID(String Librarian_ID){this.Librarian_ID.set(Librarian_ID);}
    public void setLibrarian_Remark(String Librarian_Remark){this.Librarian_Remark.set(Librarian_Remark);}
    public void setApproval_DateTime(String Approval_DateTime){this.Approval_DateTime.set(Approval_DateTime);}
    public void setApprover_ID(String Approver_ID){this.Approver_ID.set(Approver_ID);}
    public void setFine(String Fine ){this.Fine.set(Fine);}

    public StringProperty getSYProperty(){ return this.SY;}
    public StringProperty getSemesterProperty(){ return this.Semester;}
    public StringProperty getDate_ReservedProperty(){ return this.Date_Reserved;}
    public StringProperty getDate_ReturnedProperty(){ return this.Date_Returned;}
    public StringProperty getTarget_Return_DateProperty(){ return this.Target_Return_Date;}
    public StringProperty getLibrarian_IDProperty(){ return this.Librarian_ID;}
    public StringProperty getLibrarian_RemarkProperty(){ return this.Librarian_Remark;}
    public StringProperty getApproval_DateTimeProperty(){ return this.Approval_DateTime;}
    public StringProperty getApprover_IDProperty(){ return this.Approver_ID;}
    public StringProperty getFineProperty(){ return this.Fine;}

    public String getSY(){ return this.SY.get();}
    public String getSemester(){ return this.Semester.get();}
    public String getDate_Reserved(){ return this.Date_Reserved.get();}
    public String getDate_Returned(){ return this.Date_Returned.get();}
    public String getTarget_Return_Date(){ return this.Target_Return_Date.get();}
    public String getLibrarian_ID(){ return this.Librarian_ID.get();}
    public String getLibrarian_Remark(){ return this.Librarian_Remark.get();}

    public String getApproval_DateTime(){ return this.Approval_DateTime.get();}
    public String getApprover_ID(){ return this.Approver_ID.get();}
    public String getFine(){ return this.Fine.get(); }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static class PastAppts extends CurrentAppts{
        private final StringProperty SY, Semester, Librarian_ID, Librarian_Remark, Approver_ID;


    public PastAppts(String sy,
                     String semester,
                     String librarianId,
                     String librarianRemark,
                     String Approver_ID,
                     String Appointment_ID,
                     String Slot_ID,
                     String Date,
                     String Day,
                     String Visitor_ID,
                     String firstName,
                     String lastName,
                     String Transaction_Status,
                     String Request_DateTime,
                     String timeIn, String timeOut, String Approval_DateTime) {
        super(Appointment_ID, Slot_ID, Date, Day, Visitor_ID, firstName, lastName, Transaction_Status,
                Request_DateTime, timeIn, timeOut, Approval_DateTime);
        this.SY = new SimpleStringProperty(sy);
        this.Semester = new SimpleStringProperty(semester);
        this.Librarian_ID = new SimpleStringProperty(librarianId);
        this.Librarian_Remark = new SimpleStringProperty(librarianRemark);
        this.Approver_ID = new SimpleStringProperty(Approver_ID);
    }

    public PastAppts(){ this(null, null, null, null, null,
            null, null, null, null, null,null,
            null, null, null, null,null, null);}

    public void setSY(String SY){this.SY.set(SY); }
    public void setSemester(String Semester){this.Semester.set(Semester); }
    public void setLibrarian_ID(String Librarian_ID){this.Librarian_ID.set(Librarian_ID);}
    public void setLibrarian_Remark(String Librarian_Remark){this.Librarian_Remark.set(Librarian_Remark);}
    public void setApprover_ID(String Approver_ID){this.Approver_ID.set(Approver_ID);}
    public StringProperty getSYProperty(){ return this.SY;}
    public StringProperty getSemesterProperty(){ return this.Semester;}
    public StringProperty getLibrarian_IDProperty(){ return this.Librarian_ID;}
    public StringProperty getLibrarian_RemarkProperty(){ return this.Librarian_Remark;}
    public StringProperty getApprover_IDProperty(){ return this.Approver_ID;}
    public String getSY(){ return this.SY.get();}
    public String getSemester(){ return this.Semester.get();}
    public String getLibrarian_ID(){ return this.Librarian_ID.get();}
    public String getLibrarian_Remark(){ return this.Librarian_Remark.get();}
    public String getApprover_ID(){ return this.Approver_ID.get();}




}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class College {
        private final StringProperty College_Code;
        private final StringProperty Description;
        private final StringProperty Date_Opened;
        private final StringProperty Date_Closed;
        private final StringProperty Status;

        public College(String collegeCode, String description, String Date_Opened, String Date_Closed, String status) {
            this.College_Code = new SimpleStringProperty(collegeCode);
            this.Description = new SimpleStringProperty(description);
            this.Date_Opened = new SimpleStringProperty(Date_Opened);
            this.Date_Closed = new SimpleStringProperty(Date_Closed);
            this.Status = new SimpleStringProperty(status);
        }

        public College(){ this(null,null,null,null,null);}

    public void setCollege_Code(String x) { this.College_Code.set(x); }
    public void setDescription(String x) { this.Description.set(x); }
    public void setDate_Opened(String x) { this.Date_Opened.set(x); }
    public void setDate_Closed(String x) { this.Date_Closed.set(x); }
    public void setStatus(String x) { this.Status.set(x); }

    public StringProperty getCollegeCodeProperty() { return College_Code; }
    public StringProperty getDescriptionProperty() { return Description; }
    public StringProperty getDateOpenedProperty() { return Date_Opened; }
    public StringProperty getDateClosedProperty() { return Date_Closed; }
    public StringProperty getStatusProperty() { return Status; }

    public String getCollegeCode() { return College_Code.get(); }
    public String getDescription() { return Description.get(); }
    public String getDateOpened() { return Date_Opened.get(); }
    public String getDateClosed() { return Date_Closed.get(); }
    public String getStatus() { return Status.get(); }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class Courses extends College{
        private final StringProperty Course_Code;
        public Courses(String courseCode, String collegeCode, String description, String dateOpened, String dateClosed, String status) {
            super(collegeCode, description, dateOpened, dateClosed, status);
            this.Course_Code = new SimpleStringProperty(courseCode);
        }
        public Courses(){ this(null,null, null,null, null,null);}
        public void setCourse_Code(String Course_Code){this.Course_Code.set(Course_Code);}
        public StringProperty getCourseCodeProperty() { return Course_Code; }
        public String getCourseCode(){return Course_Code.get();}
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class SlotData {
        private StringProperty slotID;
        private final StringProperty day;
        private final StringProperty timeIn;
        private final StringProperty timeOut;
        private IntegerProperty availableSlots;
        private final IntegerProperty slotLimit;


        public SlotData(String day, String timeIn, String timeOut,  Integer availableSlot, Integer SlotLimit) {
            this.day = new SimpleStringProperty(day);
            this.timeIn = new SimpleStringProperty(timeIn);
            this.timeOut = new SimpleStringProperty(timeOut);
            this.availableSlots = new SimpleIntegerProperty(availableSlot);
            this.slotLimit = new SimpleIntegerProperty(SlotLimit);

        }
        public SlotData(String slotID, String day, String timeIn, String timeOut, Integer SlotLimit){
            this.slotID = new SimpleStringProperty(slotID);
            this.day = new SimpleStringProperty(day);
            this.timeIn = new SimpleStringProperty(timeIn);
            this.timeOut = new SimpleStringProperty(timeOut);
            this.slotLimit = new SimpleIntegerProperty(SlotLimit);

        }
        public String getID(){ return slotID.get();}
        public String getDay() { return day.get(); }
        public String getTimeIn() { return timeIn.get(); }
        public String getTimeOut() { return timeOut.get(); }
        public Integer getAvailableSlots(){ return availableSlots.get(); }
        public Integer getSlotLimit() { return slotLimit.get(); }

        public  StringProperty getSlotIDProperty(){ return slotID; }
        public StringProperty getDayProperty() { return day; }
        public StringProperty getTimeInProperty() { return timeIn; }
        public StringProperty getTimeOutProperty() { return timeOut; }
        public IntegerProperty getAvailableSlotsProperty() { return availableSlots; }
        public IntegerProperty getSlotLimitProperty() { return slotLimit; }

    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class Book {
        private final StringProperty bookId, description, author, publisher, datePublished,
                isbn, status;
        private final DoubleProperty price;

        public Book(String bookId, String description, String author, String publisher,
                    String datePublished, Double price, String isbn, String status) {
            this.bookId = new SimpleStringProperty(bookId);
            this.description = new SimpleStringProperty(description);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.datePublished = new SimpleStringProperty(datePublished);
            this.price = new SimpleDoubleProperty(price);
            this.isbn = new SimpleStringProperty(isbn);
            this.status = new SimpleStringProperty(status);
        }

        public Book() {
            this(null, null, null, null, null, null, null, null);
        }




        public StringProperty getBookIDProperty(){ return bookId; }
        public StringProperty getDescriptionProperty(){ return description; }

        public StringProperty getAuthorProperty(){ return author; }
        public StringProperty getPublisherProperty(){ return publisher; }
        public StringProperty getDatePublishedProperty(){ return datePublished; }
        public DoubleProperty getPriceProperty(){ return price;}
        public StringProperty getIsbnProperty(){ return isbn;}
        public StringProperty getStatusProperty(){ return status; }
        public String getBookId(){ return bookId.get(); }
        public String getDescription(){ return description.get(); }
        public String getAuthor(){ return author.get(); }
        public String getPublisher(){ return publisher.get(); }
        public String getDatePublished(){ return datePublished.get(); }
        public Double getPrice(){ return price.get();}
        public String getIsbn(){ return isbn.get();}
        public String getStatus(){ return status.get(); }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static <T> void textWrap(TableColumn<T, String> column) {
    column.setCellFactory(col -> {
        TableCell<T, String> cell = new TableCell<>() {
            private final Text text = new Text();

            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(text);
                setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(column.widthProperty());
                text.setTextAlignment(TextAlignment.CENTER);
                text.setStyle("-fx-padding: 5;");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    text.setTextAlignment(null);
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        };

        return cell;
    });
}

    public static <T>  void wrapColumnHeader(TableColumn<T, String> column) {
        Text textNode = new Text();
        textNode.textProperty().bind(column.textProperty());
        textNode.wrappingWidthProperty().bind(column.widthProperty());
        textNode.setTextAlignment(TextAlignment.CENTER);

        column.setCellFactory(col -> {
            TableCell<T, String> cell = new TableCell<>() {
                private final Text text = new Text();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(text);
                    setPrefHeight(Control.USE_COMPUTED_SIZE);
                    text.wrappingWidthProperty().bind(column.widthProperty());
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setStyle("-fx-padding: 5;");
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        text.setTextAlignment(null);
                        setGraphic(null);
                    } else {
                        text.setText(item);
                        setGraphic(text);
                    }
                }
            };

            return cell;
        });

        column.setGraphic(textNode);
    }

    public static class NonNullCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? "N/A" : item.toString());
                }
            };
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////








}






