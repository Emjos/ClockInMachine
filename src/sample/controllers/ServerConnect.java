package sample.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerConnect {

    private final static String DBURL = "jdbc:mysql://46.242.246.219:3306";
    private final static String DBUSER = "30712964_clock_in";
    private final static String DBPASS = "ClockIn123";
    private final static String DBDRIVER = "com.mysql.jdbc.Driver";

    Connection connection;

    public void getConnection() throws SQLException, ClassNotFoundException {

        Class.forName(DBDRIVER);
        connection = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        System.out.println("polaczona");

    }





}
