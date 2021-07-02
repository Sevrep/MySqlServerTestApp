package com.chasetech.mysqlservertestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chasetech.mysqlservertestapp.database.SqlServerConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCheckConnection = findViewById(R.id.btnCheckConnection);
        btnCheckConnection.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnCheckConnection) {
            checkConnection();
        }
    }

    public void checkConnection() {

        try {
            if (SqlServerConnection.connection == null) {
                new SqlServerConnection().setConnection();
            }

            if (SqlServerConnection.connection != null) {
                Statement statement = SqlServerConnection.connection.createStatement();
                String sqlQuery = "SELECT * FROM login";
                ResultSet resultSet = statement.executeQuery(sqlQuery);
                Log.e("Sevrep", "-------------------------------------------------------");
                while (resultSet.next()) {
                    Log.e("Sevrep", resultSet.getString("name"));
                }
                Log.e("Sevrep", "-------------------------------------------------------");
                Toast.makeText(getApplicationContext(), R.string.query_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.connection_failed, Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException throwables) {
            Toast.makeText(getApplicationContext(), throwables.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Sevrep", throwables.getMessage());
            throwables.printStackTrace();
        }

    }
}