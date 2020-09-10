package com.example.googlemapdemo.helper;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public static Connection openConnection() {
         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(policy);
         Connection connection = null;

         String ip = "192.168.0.112";
         String port = "1433";
         String Classes = "net.sourceforge.jtds.jdbc.Driver";
         String database = "FindApartment_DB";
         String username = "abc";
         String password = "123456";
         String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;

        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  connection;
    }
}
