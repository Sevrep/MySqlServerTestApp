package com.chasetech.mysqlservertestapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chasetech.mysqlservertestapp.database.SqlServerConnection;

import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtId;
    private EditText edtName;
    private EditText edtAddress;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCheckConnection = findViewById(R.id.btnCheckConnection);
        btnCheckConnection.setOnClickListener(this);

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        Button btnGet = findViewById(R.id.btnGet);
        btnGet.setOnClickListener(this);

        txtResult = findViewById(R.id.txtResult);

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnCheckConnection) {
            if (checkConnection()) {
                customToastLog(R.string.connection_success);
            } else {
                customToastLog(R.string.connection_failed);
            }
        } else if (viewId == R.id.btnAdd) {
            insertRecord();
        } else if (viewId == R.id.btnUpdate) {
            updateRecord();
        } else if (viewId == R.id.btnDelete) {
            deleteRecord();
        } else if (viewId == R.id.btnGet) {
            getRecord();
        }
    }

    private void deleteRecord() {
        if (checkConnection()) {
            String id = edtId.getText().toString().trim();
            String sqlDelete = "DELETE FROM [user] WHERE id = '" + id + "';";
            try {
                Statement statement = SqlServerConnection.connection.createStatement();
                statement.executeUpdate(sqlDelete);
                customToastLog(R.string.query_delete_success);
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
                customToastLog(R.string.query_delete_failed);
            }
        } else {
            customToastLog(R.string.connection_failed);
        }
    }

    private void updateRecord() {
        if (checkConnection()) {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String sqlUpdate = "UPDATE [user] SET name = '" + name + "', address = '" + address + "' WHERE id = '" + id + "';";
            try {
                Statement statement = SqlServerConnection.connection.createStatement();
                statement.executeUpdate(sqlUpdate);
                customToastLog(R.string.query_update_success);
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
                customToastLog(R.string.query_update_failed);
            }
        } else {
            customToastLog(R.string.connection_failed);
        }
    }

    private void insertRecord() {
        if (checkConnection()) {
            String id = edtId.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String sqlInsert = "INSERT INTO [user] (id, name, address) VALUES('" + id + "', '" + name + "', '" + address + "');";
            try {
                Statement statement = SqlServerConnection.connection.createStatement();
                statement.executeUpdate(sqlInsert);
                customToastLog(R.string.query_insert_success);
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
                customToastLog(R.string.query_insert_failed);
            }
        } else {
            customToastLog(R.string.connection_failed);
        }
    }

    private void getRecord() {
        if (checkConnection()) {
            String id = edtId.getText().toString().trim();
            String sqlSelect = "SELECT name, address FROM [user] WHERE id = '" + id + "';";
            try {
                Statement statement = SqlServerConnection.connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlSelect);

                while (rs.next()) {
                    edtName.setText(rs.getString(1));
                    edtAddress.setText(rs.getString(2));
                }

                customToastLog(R.string.query_select_success);
            } catch (Exception exception) {
                Log.e("Error", exception.getMessage());
                customToastLog(R.string.query_select_failed);
            }
        } else {
            customToastLog(R.string.connection_failed);
        }
    }

    public boolean checkConnection() {
        boolean connectionStatus = false;
        try {
            if (SqlServerConnection.connection == null) {
                new SqlServerConnection().setConnection();
            }
            connectionStatus = SqlServerConnection.connection != null;
        } catch (Exception exception) {
            Log.e("Sevrep", exception.getMessage());
            exception.printStackTrace();
        }
        return connectionStatus;
    }

    private void customToastLog(int p) {
        Log.e("Sevrep", String.valueOf(p));
        Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
    }

}