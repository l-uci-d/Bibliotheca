package com.example.oopapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.lang.reflect.Method;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.example.oopapplication.HelperMethods.*;

public class DataBaseMethods {

    static ConnectDB db = new ConnectDB();
    static Connection conn = db.Connect();
       ResultSet rs = null;
    PreparedStatement ps = null;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getUserID(String tbl, String val1, String val2, String colName) {
        String a = "";
        String q = "SELECT * FROM " + tbl + " WHERE FIRSTNAME = ? AND LASTNAME = ? ;";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, val1);
            ps.setString(2, val2);

            //System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a = rs.getString(colName);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return a;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void populateCmbBox(String tableName, String columnName, ComboBox<String> comboBox) {
        String q = "SELECT DISTINCT " + columnName + " FROM " + tableName;

        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                String value = resultSet.getString(columnName);
                comboBox.getItems().add(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getUserIDColumnName(String tblCol) {
        if (tblCol.equals("Student"))
            return "Student_No";
        else if (tblCol.equals("Employee"))
            return "Employee_ID";
        else if (tblCol.equals("Guest"))
            return "Guest_ID";
        else if (tblCol.equals("Book"))
            return "Book_ID";
        else if (tblCol.equals("Book_Reservation"))
            return "Reservation_ID";
        else if (tblCol.equals("User"))
            return "User_ID";

        return null;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int getCount(String tbl, String input, String colName, String extra) {
        int count = 0;
        String q = "SELECT COUNT(*) AS recordcount FROM " + tbl + " WHERE " + colName + " = ? " + extra + ";";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, input);
            ResultSet rs = ps.executeQuery();
            //System.out.println(ps);
            if (rs.next())
                count = rs.getInt("recordcount");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return count;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int getCountLike(String tbl, String input, String colName, String extra) {
        int count = 0;
        String q = "SELECT COUNT(*) AS recordcount FROM " + tbl + " WHERE " + colName + " LIKE '" + input + "%'" + extra + "; ";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                count = rs.getInt("recordcount");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return count;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void getBookTransacts(String tblName, String status, String ID, ObservableList<TableModels.PendingBooks> dataList, TextArea txtArea){
        String q = "SELECT * FROM " + tblName + " WHERE Student_NO = ? AND Transaction_Status = ?;";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, ID);
            ps.setString(2, status);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                txtArea.setText("Your " + status + " Book Reservations: \n");
                String Book_ID = rs.getString("Book_Id");
                String Book_Title = rs.getString("Book_Title");
                dataList.add(new TableModels.PendingBooks(null, Book_ID, Book_Title, null,
                        null, null, null, null));
                txtArea.appendText(Book_ID + " - " + Book_Title + "\n");
            }
            while(rs.next()){
                String Book_ID = rs.getString("Book_Id");
                String Book_Title = rs.getString("Book_Title");
                dataList.add(new TableModels.PendingBooks(null, Book_ID, Book_Title, null,
                        null, null, null, null));
                txtArea.appendText(Book_ID + " - " + Book_Title + "\n");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String getNextID(String tblName, String colName, int rowNumber, String bookID) {
        String lastID = null;
        String q = "SELECT " + colName + " FROM " + tblName;
        if (!bookID.isEmpty()) {
            q += " WHERE " + colName + " LIKE '" + bookID + "%'";
        }
        q += " ORDER BY " + colName + " DESC; ";

        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ResultSet rs = ps.executeQuery();

            while (rowNumber > 0) {
                rs.next();
                rowNumber--;
            }

            lastID = rs.getString(colName);
            //System.out.println("Unincremented ID: " + lastID);

        } catch (SQLException e) {
            System.out.println(e);
        }
        if (bookID.isEmpty()) {
            String nonNumericPart = lastID.substring(0, 1);
            String numericPartStr = lastID.substring(1);

            int numericPart = Integer.parseInt(numericPartStr);
            numericPart++;

            String formattedNumericPart = String.format("%04d", numericPart);
            return nonNumericPart + formattedNumericPart;
        } else {
            String nonNumericPart = lastID.substring(0, 3);
            String numericPartStr = lastID.substring(3);

            int numericPart = Integer.parseInt(numericPartStr);
            numericPart++;

            String formattedNumericPart = String.format("%03d", numericPart);  // Assuming you want 3 digits with leading zeros
            return nonNumericPart + formattedNumericPart;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void dropDB(String tblName, String ID, String colName) {
        String q = "DELETE FROM " + tblName + " WHERE " + colName + " = ?";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, ID);
            //System.out.println(ps);
            //ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void insertIntoDB(String tableName, Map<String, String> data) {
        data.entrySet().removeIf(entry -> entry.getValue() == null);
        if (data.isEmpty()) {
            return;
        }
        String columns = String.join(", ", data.keySet());
        String values = String.join(", ", data.keySet().stream().map(key -> "?").toArray(String[]::new));
        String q = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";


        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {

            int parameterIndex = 1;
            for (Object value : data.values()) {
                if (value != null) {
                    ps.setObject(parameterIndex++, value);
                }
            }
            //System.out.println(ps);
            //ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void insertIntoDB(String tblName, List<String> colNames, List<String> newVals) {
        StringBuilder q = new StringBuilder("INSERT INTO " + tblName);

        if (!colNames.isEmpty()) {
            q.append("(");
            for (String colName : colNames) {
                q.append(colName).append(", ");
            }
            q.setLength(q.length() - 2);
            q.append(") ");

        }
        q.append("VALUES ");
        if (!newVals.isEmpty()) {
            q.append("(");
            for (String newVal : newVals) {
                q.append("?, ");
            }
            q.setLength(q.length() - 2);
            q.append(");");

        }
        //System.out.println(q);
        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement(q.toString())) {
                for (int i = 0; i < colNames.size(); i++) {
                    setParameter(preparedStatement, i + 1, newVals.get(i));
                }
                //System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public static void updateDB(String tblName, List<String> colNames, List<String> newVals, String colPk, String pk) {
    StringBuilder q = new StringBuilder("UPDATE " + tblName +
            " SET ");

    if (!colNames.isEmpty()) {
        for (String colName : colNames) {
            q.append(colName).append("= ?, ");
        }
        q.setLength(q.length() - 2);
        q.append(" WHERE " + colPk + " = ?");

    }

    //System.out.println(q);
    try {
        try (PreparedStatement preparedStatement = conn.prepareStatement(q.toString())) {
            for (int i = 0; i < colNames.size(); i++) {
                setParameter(preparedStatement, i + 1, newVals.get(i));
            }
            preparedStatement.setString(colNames.size() + 1, pk);
            //System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private static void setParameter(PreparedStatement preparedStatement, int index, Object value) throws SQLException {
        if (value instanceof String) {
            preparedStatement.setString(index, (String) value);
        } else if (value instanceof Integer) {
            preparedStatement.setInt(index, (Integer) value);
        } else if (value instanceof Double) {
            preparedStatement.setDouble(index, (Double) value);
        } else if (value instanceof Boolean) {
            preparedStatement.setBoolean(index, (Boolean) value);
        } else if (value instanceof Date) {
            preparedStatement.setDate(index, (Date) value);
        } else if (value instanceof Time) {
            preparedStatement.setTime(index, (Time) value);
        } else if (value instanceof Timestamp) {
            preparedStatement.setTimestamp(index, (Timestamp) value);
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static <T> void populate(String tableName, List<String> colNames, ObservableList<T> dataList, TableView<T> table, Class<T> modelClass, String conditions) {
        dataList.clear();
        StringJoiner columns = new StringJoiner(", ");
        if(colNames != null)
            colNames.forEach(columns::add);
        else
            columns.add("*");
        String q = null;

        if (conditions.isEmpty())
            q = "SELECT " + columns + " FROM " + tableName;
        else
            q = "SELECT " + columns + " FROM " + tableName + conditions;


        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                T instance = modelClass.getDeclaredConstructor().newInstance();
                //System.out.println(instance);


                for (String colName : colNames) {
                    String methodName = "set" + colName;
                    Method method = modelClass.getMethod(methodName, String.class);
                    method.invoke(instance, rs.getString(colName));
                    //System.out.print(" " + colName + ": " + rs.getString(colName) + " ");
                }


                dataList.add(instance);
                // System.out.println("DataList size: " + dataList.size());
                //System.out.println(dataList);
            }
            table.getItems().clear();
            table.setItems(FXCollections.observableArrayList(dataList));
            table.refresh();
             //System.out.println("Setted the datalist into the table!!!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDateTime() {

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String currentSYSEM(String choice) {
        String currentSY = null, currentSem = null;
        String q = "SELECT * FROM current_sy_sem;";

        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentSem = rs.getString("active_sem");
                currentSY = rs.getString("active_sy");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        if (choice.equals("Sem"))
            return currentSem;
        else return currentSY;

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void toCmbBox(String tblname, String colName, String userID, ComboBox<String> cmbBox) {
        String q = "SELECT " + colName + " FROM " + tblname + " WHERE User_ID = ? OR username = ?;";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {

            ps.setString(1, userID);
            ps.setString(2, userID);
            rs = ps.executeQuery();
            // System.out.println("Cmb Box = " + ps);
            if (rs.next()) {
                String result = rs.getString(colName);
                cmbBox.setValue(result);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB(String tableName, String username, String userID, List<String> colNames, List<Object> newValues, String pk, String pass) {
        if (username != null) {
            //System.out.println("Received un : " + username);
            String q1 = "UPDATE user SET username = '" + username + "' where user_ID = '"
                    + userID + "' OR username = '" + userID + "';";
            // System.out.println(q1);
            try (PreparedStatement ps = conn.prepareStatement(q1)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        if (!pass.isEmpty()) {
            String q1 = "UPDATE user SET password = ? where user_ID = ? OR username = ?";
            try (PreparedStatement ps = conn.prepareStatement(q1)) {
                ps.setString(1, pass);
                ps.setString(2, username);
                ps.setString(3, username);
                //System.out.println(ps);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        StringBuilder q = new StringBuilder("UPDATE " + tableName + " SET ");


        for (int i = 0; i < colNames.size(); i++) {
            q.append(colNames.get(i)).append(" = ?, ");
        }
        q.setLength(q.length() - 2);
        q.append(" WHERE " + pk + "= '" + userID + "';");
        //System.out.println(q);

        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement(q.toString())) {
                for (int i = 0; i < colNames.size(); i++) {
                    setParameter(preparedStatement, i + 1, newValues.get(i));
                }
                //System.out.println(preparedStatement);
                int rows_affected = preparedStatement.executeUpdate();
                if(rows_affected != 0)
                    showInfo(info, "Updated Information", "Successfully Updated Account Information.");
            }
        } catch (SQLException e) {
            showInfo(error, "Failed Update", "Unsuccessfully Updated Account Information,\n please try again");
            e.printStackTrace();
            return;
        }


    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDB(String tableName, String userID, List<String> colNames, List<Object> newValues, String pk) {

        StringBuilder q = new StringBuilder("UPDATE " + tableName + " SET ");


        for (int i = 0; i < colNames.size(); i++) {
            q.append(colNames.get(i)).append(" = ?, ");
        }
        q.setLength(q.length() - 2);
        q.append(" WHERE " + pk + "= '" + userID + "';");
        //System.out.println(q);

        try {
            try (PreparedStatement preparedStatement = conn.prepareStatement(q.toString())) {
                for (int i = 0; i < colNames.size(); i++) {
                    setParameter(preparedStatement, i + 1, newValues.get(i));
                }
                //System.out.println(preparedStatement);
                int rows_affected = preparedStatement.executeUpdate();
                if(rows_affected != 0)
                    showInfo(info, "Updated Information", "Successfully Updated Account Information.");
            }
        } catch (SQLException e) {
            showInfo(error, "Failed Update", "Unsuccessfully Updated Account Information,\n please try again");
            e.printStackTrace();
            return;
        }


    }

    private void toSetText(String colName, TextControl label, String userID, String course) {
        if (course != null) {
            String q3 = "SELECT Course_Code FROM student where student_no = ?;";
            try (Connection conn = new ConnectDB().Connect();
                 PreparedStatement ps = conn.prepareStatement(q3)) {
                ps.setString(1, colName);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String result = rs.getString("Course_Code");
                    label.setText(result);
                } else
                    label.setText("Null values");
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            String q = "SELECT " + colName + " FROM vwUserInfo  WHERE User_ID = ? OR username = ?;";
            try (Connection conn = new ConnectDB().Connect();
                 PreparedStatement ps = conn.prepareStatement(q)) {

                ps.setString(1, userID);
                ps.setString(2, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String result = rs.getString(colName);
                    label.setText(result);
                } else
                    label.setText("Null values");

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void toText(String colName, Label label, String userID, String course) {
        toSetText(colName, label::setText, userID, course);
    }

    public void toText(String colName, TextField textField, String userID, String course) {
        toSetText(colName, textField::setText, userID, course);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void populateSlot(TableView<TableModels.SlotData> table, int x) {

        String q = "SELECT * FROM vwSlots order by FIELD(Day, 'Mon', 'Tue', " +
                "'Wed', 'Thu', 'Fri', 'Sat'), Time_In;";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {

            rs = ps.executeQuery();
            ObservableList<TableModels.SlotData> slotDataList = FXCollections.observableArrayList();

            while (rs.next()) {
                String slotID = rs.getString("Slot_ID");
                String day = rs.getString("Day");
                String timeIn = rs.getString("Time_In");
                String timeOut = rs.getString("Time_Out");
                Integer availableSlots = rs.getInt("Available_Slots");
                Integer slotLimit = rs.getInt("Slot_Limit");
                if (x == 0)
                    slotDataList.add(new TableModels.SlotData(slotID, day, timeIn, timeOut, availableSlots, slotLimit));
                else
                    slotDataList.add(new TableModels.SlotData(slotID, day, timeIn, timeOut, slotLimit));
            }

            // System.out.println("SlotData List: " + slotDataList);
            table.setItems(slotDataList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void populateBook(TableView<TableModels.Book> table, Map<String, String> filters) {

        StringBuilder query = new StringBuilder("SELECT * FROM Book");

        if (filters != null && !filters.isEmpty()) {
            query.append(" WHERE ");

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                query.append(entry.getValue()).append(" LIKE ? AND ");
            }

            query.setLength(query.length() - 5);  // Remove the trailing " AND "
        }

        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            if (filters != null && !filters.isEmpty()) {
                int parameterIndex = 1;
                for (Map.Entry<String, String> entry : filters.entrySet()) {
                    ps.setString(parameterIndex++, "%" + entry.getKey() + "%");
                }
            }
            //System.out.println(ps);
            rs = ps.executeQuery();
            ObservableList<TableModels.Book> bookDataList = FXCollections.observableArrayList();
            while (rs.next()) {
                String bookId = rs.getString("Book_Id");
                String description = rs.getString("Description");
                String author = rs.getString("Author");
                String publisher = rs.getString("Publisher");
                String datePublished = rs.getString("Date_Published");
                double price = rs.getFloat("Price");
                String isbn = rs.getString("ISBN");
                String status = rs.getString("Status");

                bookDataList.add(new TableModels.Book(bookId, description, author, publisher, datePublished, price, isbn, status));

            }
            table.getItems().clear();
            table.setItems(bookDataList);
            table.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void populateSYSEM(TableView<TableModels.SYSEM> table, String str) {
        String q = "SELECT * FROM " + str;

        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {

            rs = ps.executeQuery();
            ObservableList<TableModels.SYSEM> syList = FXCollections.observableArrayList();

            while (rs.next()) {
                String col1 = rs.getString(str);
                syList.add(new TableModels.SYSEM(col1));
            }

            table.setItems(syList);

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void filter() {

    }

    public void populateEmployees(TableView<TableModels.Employee> table) {
        String q = "SELECT * FROM Employee";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            rs = ps.executeQuery();
            ObservableList<TableModels.Employee> employeeDataList = FXCollections.observableArrayList();

            while (rs.next()) {
                String employeeID = rs.getString("Employee_ID");
                String lastName = rs.getString("LastName");
                String firstName = rs.getString("FirstName");
                String email = rs.getString("Email");
                String gender = rs.getString("Gender");
                String cpNumber = rs.getString("CP_Num");
                String address = rs.getString("Address");
                String bDay = rs.getString("BDay");
                String status = rs.getString("Status");
                employeeDataList.add(new TableModels.Employee(employeeID, lastName, firstName, email, gender, cpNumber, address, bDay, status));
            }
            table.setItems(employeeDataList);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void populateGuests(TableView<TableModels.Guest> table) {
        String q = "SELECT * FROM Guest";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            rs = ps.executeQuery();
            ObservableList<TableModels.Guest> guestDataList = FXCollections.observableArrayList();

            while (rs.next()) {
                String guestID = rs.getString("Guest_ID");
                String lastName = rs.getString("LastName");
                String firstName = rs.getString("FirstName");
                String email = rs.getString("Email");
                String gender = rs.getString("Gender");
                String cpNumber = rs.getString("CP_Num");
                String address = rs.getString("Address");
                String bDay = rs.getString("BDay");
                String status = rs.getString("Status");
                guestDataList.add(new TableModels.Guest(guestID, lastName, firstName, email, gender, cpNumber, address, bDay, status));
            }
            table.setItems(guestDataList);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void populateUsers(TableView<TableModels.Users> table) {
        String q = "SELECT * FROM vwUserInfo";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            rs = ps.executeQuery();
            ObservableList<TableModels.Users> guestDataList = FXCollections.observableArrayList();

            while (rs.next()) {
                String userID = rs.getString("User_ID");
                String userType = rs.getString("User_Type");
                String lastName = rs.getString("LastName");
                String firstName = rs.getString("FirstName");
                String username = rs.getString("Username");
                String password = rs.getString("Password");

                guestDataList.add(new TableModels.Users(userID, username, password, lastName, firstName, null, null, null, null, null, null, userType));
            }
            table.setItems(guestDataList);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String getUserType(String userID) {
        String q = "SELECT User_Type FROM User WHERE User_ID = ? OR username = ?;";
        String usertype = null;

        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, userID);
            ps.setString(2, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // System.out.println("Records exists");
                usertype = rs.getString("User_Type");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usertype;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean checkPassword(String userID, String inputPass) {
        String q = "SELECT Password FROM User where User_ID = ? OR username = ?;";
        String actualPass = null;
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, userID);
            ps.setString(2, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //System.out.println("Password retrieved");
                actualPass = rs.getString("Password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inputPass.equals(actualPass);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setBDay(String userID, DatePicker datePicker) {
        LocalDate date = null;
        String q = "SELECT BDay FROM vwUserInfo where user_ID = ? or username = ?;";
        try (Connection conn = new ConnectDB().Connect();
             PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, userID);
            ps.setString(2, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                Date bDayDate = rs.getDate("BDay");
                if (bDayDate != null) {
                    date = bDayDate.toLocalDate();
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (date != null) {
            datePicker.setValue(date);
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private interface TextControl {
        void setText(String text);
    }

}


