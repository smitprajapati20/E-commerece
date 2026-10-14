package com.example.masterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.snackbar.Snackbar;

public class DashboardActivity extends AppCompatActivity {

    Button delete_profile , logout , profile , categories ;
    TextView welcome ;
    SharedPreferences sp ;
    SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);
        sp = getSharedPreferences(ConstantSP.DataB, MODE_PRIVATE);

        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), email VARCHAR(30), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        delete_profile = findViewById(R.id.dashboard_delete);
        logout = findViewById(R.id.dashboard_logout);
        profile = findViewById(R.id.dashboard_profile);
        welcome = findViewById(R.id.dashboard_welcome);
        categories = findViewById(R.id.dashboard_Categories);

        welcome.setText("Welcome "+sp.getString(ConstantSP.name, ""));



        profile.setOnClickListener(view -> {
            startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
        });

        categories.setOnClickListener(view -> {
            startActivity(new Intent(DashboardActivity.this, CategoriesActivity.class));
        });

        logout.setOnClickListener(view -> {
            sp.edit().clear().apply();
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            finish();
        });

        delete_profile.setOnClickListener(view -> {
            Snackbar.make(view, "Sure to delete account", Snackbar.LENGTH_SHORT)
                    .setAction("Yes" , v -> {
                        db.execSQL("DELETE FROM user WHERE userid = "+sp.getInt(ConstantSP.userid, 0));
                        sp.edit().clear().apply();
                        startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                        finish();
                    }).show();
        });
    }
    }