package com.example.oopapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {

    // ----- 1.) validates name. may have upper, lowercase values, spaces, and special characters or accents. no numerals. has character limit
    public static boolean isValidName(String name) {
        if (name.isEmpty() || name.length() > 30) {
            return false; // empty or too long is not a valid name
        }

        // Remove diacritics (accents) from the name
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Regular expression to allow letters and spaces
        String regex = "^[\\p{L} ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }

    public static boolean isValidPass(String pass) {
        if (pass.length() > 20 || pass.length() <= 7) {
            return false; // empty or too long is not a valid name
        }

        // Remove diacritics (accents) from the name
        String normalized = Normalizer.normalize(pass, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Regular expression to allow letters, spaces, and some common special characters
        String regex = "^(?=.*[\\p{L}])(?=.*[0-9])[\\p{L}0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }

    public static boolean isValidUserName(String username) {
        if ((username.length() >= 30) || (username.length() <= 5))
            return false;

        // Remove diacritics (accents) from the name
        String normalized = Normalizer.normalize(username, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }


    // ----- 2.) validates cp_num. starts with "09" and is followed by exactly 9 more digits
    public static boolean isValidNumber(String number) {
        if (number.isEmpty()) {
            return false; // empty is not a valid number
        }
        // Regular expression to validate Filipino mobile number
        String regex = "^09\\d{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }


    // ----- 3.) validates a type-written date picker date
    public static boolean isValidDate(String dateString) {
        if (dateString.isEmpty()) {
            System.out.println("Empty date");
            return false; // empty date
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return false;
        }
    }


    // ----- 4.) validates address, not null and has char limit
    public static boolean isValidAddress(String address) {
        return !address.isEmpty() && address.length() <= 255;
    }


    // ----- 5.) validates a description. can have upper, lower case and dashes, apostrophes, parentheses, dots, numerals
    public static boolean isValidDescription(String description) {
        if (description.isEmpty()) {
            return false; // empty is not a valid description
        }

        // Remove diacritics (accents) from the description
        String normalized = Normalizer.normalize(description, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Regular expression to allow letters, spaces, dashes, apostrophes, parentheses, dots, and numerals
        String regex = "^[\\p{L} .'-()0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }

    // ----- 6.) validates a primary key and checks validity. can have upper, lower case and dash. must not have same code
    public static boolean isValidCode(String strCode, String strTableName, String strColumn) {
        if (!strCode.matches("^[A-Za-z\\-]*$") || strCode.isEmpty() || strCode.length() > 10) {
            return false; // Invalid code format
        }

        return isCodeUnique(strCode, strTableName, strColumn);
    }


    // ----- 7.) validates a subject primary key and checks validity. can have upper, lower case, dash, dots, spaces, numerals. must not have same code
    public static boolean isValidSubjectCode(String strCode, String strTableName, String strColumn) {
        if (!strCode.matches("^[A-Za-z0-9\\- .()]*$") || strCode.isEmpty() || strCode.length() > 10) {
            return false; // Invalid code format
        }
        return isCodeUnique(strCode, strTableName, strColumn);
    }


    // ----- 8.) validates primary keys uniqueness
    public static boolean isCodeUnique(String strCode, String strTableName, String strColumn) {
        // Check if the code already exists in the specified column of the table
        String query = "SELECT COUNT(*) FROM " + strTableName + " WHERE " + strColumn + " = ?";
        try (Connection connection = new ConnectDB().Connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, strCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count == 0; // Return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean isValidEmail(String email){
        if ((email.length() >= 50) || (email.length() <= 5))
            return false;

        String normalized = Normalizer.normalize(email, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");


        String regex = ".*@.*\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(normalized);

        return matcher.matches();
    }

}
