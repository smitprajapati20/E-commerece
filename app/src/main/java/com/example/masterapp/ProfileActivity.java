package com.example.masterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class ProfileActivity extends AppCompatActivity {

    EditText name, email , contact , password , confirm_password;
    ImageView back;
    Button edit_profile, update_profile;
    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);
        sp = getSharedPreferences(ConstantSP.DataB, MODE_PRIVATE);

        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), email VARCHAR(30), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        name = findViewById(R.id.name_profile);
        email = findViewById(R.id.email_profile);
        contact = findViewById(R.id.contact_profile);
        password = findViewById(R.id.password_profile);
        confirm_password = findViewById(R.id.confirm_password_profile);
        edit_profile = findViewById(R.id.profile_btn);
        update_profile = findViewById(R.id.update_profile_btn);
        back = findViewById(R.id.back);

        name.setText(sp.getString(ConstantSP.name, ""));
        email.setText(sp.getString(ConstantSP.email, ""));
        contact.setText(sp.getString(ConstantSP.contact, ""));
        password.setText(sp.getString(ConstantSP.password, ""));
        confirm_password.setText(sp.getString(ConstantSP.password, ""));

        setData(false);

        back.setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
            finish();
        });

        edit_profile.setOnClickListener(view -> {
            setData(true);
            confirm_password.setVisibility(View.VISIBLE);
            edit_profile.setVisibility(View.GONE);
            update_profile.setVisibility(View.VISIBLE);
        });

        update_profile.setOnClickListener(view ->{

            if (name.getText().toString().isEmpty()){
                name.setError("Enter Name");
                name.requestFocus();
                return;
            }
            if (contact.getText().toString().isEmpty()){
                contact.setError("Enter Contact");
                contact.requestFocus();
                return;
            }
            if (contact.getText().toString().length() != 10){
                contact.setError("Contact number must be 10 digits");
                contact.requestFocus();
                return;
            }
            if (password.getText().toString().isEmpty()){
                password.setError("Enter Password");
                password.requestFocus();
                return;
            }
            if (password.getText().toString().length() < 6){
                password.setError("Password must be 6 characters long");
                password.requestFocus();
                return;
            }
            if (confirm_password.getText().toString().isEmpty()){
                confirm_password.setError("Enter Confirm Password");
                confirm_password.requestFocus();
                return;
            }
            if (!confirm_password.getText().toString().equals(password.getText().toString())){
                confirm_password.setError("Password does not match");
                confirm_password.requestFocus();
                return;
            }
            else {

            db.execSQL("UPDATE user SET name = '"+name.getText().toString()+"', contact = '"+contact.getText().toString()+"', password = '"+password.getText().toString()+"' WHERE email = '"+email.getText().toString()+"' ");
            sp.edit()
                    .putString(ConstantSP.name, name.getText().toString())
                    .putString(ConstantSP.contact, contact.getText().toString())
                    .putString(ConstantSP.password, password.getText().toString())
                    .apply();
            setData(false);
            confirm_password.setVisibility(View.GONE);
            update_profile.setVisibility(View.GONE);
            edit_profile.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, DashboardActivity.class));
            finish();

            }});

    }
    void setData(Boolean b){
        name.setEnabled(b);
        contact.setEnabled(b);
        password.setEnabled(b);

    }
}
