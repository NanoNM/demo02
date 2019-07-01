package com.example.demo.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Controller
public class MySQLProvider {
    @Value("${MySQLUrl}")
    private String MySQLUrl;
    @Value("${MySQLname}")
    private String MySQLname;
    @Value("${MySQLpw}")
    private String MySQLpw;
    @Value("${MySQLDataBase}")
    private String MySQLDataBase;
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn= DriverManager.getConnection((MySQLUrl+MySQLDataBase),MySQLname,MySQLpw);
        return conn;
    }
}
