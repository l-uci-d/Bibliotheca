package com.example.oopapplication;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
    public Connection Connect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Group2", "root", "12qwaszx");
            //System.out.println("Connected Successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }


}
