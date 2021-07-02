package com.chasetech.mysqlservertestapp.database;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnection {

    public static Connection connection;

    public void setConnection() {
        try {

            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);

//            Class.forName("net.sourceforge.jtds.jdbc.Driver");
//            String ConnectionURL = "jdbc:jtds:sqlserver://" + "192.168.1.6" + "/" + "mysqlservertestapp_db";
//            connection = DriverManager.getConnection(ConnectionURL, "sa", "1");

            String ip = "192.168.1.6";
            String connectionurl = "jdbc:jtds:sqlserver://" + ip + "; instance = SQLEXPRESS; database = mysqlservertestapp_db;";
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionurl, "user", "1234");

            Log.e("Sevrep", "Connection called.");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Sevrep", "ClassNotFoundException: " + e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Sevrep", "SQLException: " + throwables.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
