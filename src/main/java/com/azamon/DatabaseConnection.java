package com.azamon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String jdbcURL = "jdbc:postgresql:azamondb";
    private static String username = "azamon";
    private static String password = "password";

    public static Connection getConnection() throws SQLException {

        //Connecting to PostgreSQL database by JDBC
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Database connection is successful");
        return connection;
    }
}
