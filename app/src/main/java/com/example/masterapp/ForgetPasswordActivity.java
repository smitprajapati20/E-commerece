package com.example.masterapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email_forget, new_password, confirm_password;
    SQLiteDatabase db ;

    Button changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), email VARCHAR(30), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        email_forget = findViewById(R.id.email_forget);
        new_password = findViewById(R.id.new_password_forget);
        confirm_password = findViewById(R.id.confirm_new_password_forget);
        changePassword = findViewById(R.id.change_password_btn);

        changePassword.setOnClickListener(view -> {
            String email = email_forget.getText().toString().trim();
            String newPassword = new_password.getText().toString().trim();
            String confirmPassword = confirm_password.getText().toString().trim();

            if(email.isEmpty()){
                email_forget.setError("Enter Email");
                email_forget.requestFocus();
                return;
            }
            else if(newPassword.isEmpty()){
                new_password.setError("Enter New Password");
                new_password.requestFocus();
                return;
            }
            else if(newPassword.length() < 6){
                new_password.setError("Password must be of at least 6 characters");
                new_password.requestFocus();
                return;
            }
            else if(confirmPassword.isEmpty()){
                confirm_password.setError("Enter Confirm Password");
                confirm_password.requestFocus();
                return;
            }
            else if(!newPassword.equals(confirmPassword)){
                confirm_password.setError("Password do not match");
                confirm_password.requestFocus();
                return;
            }
            else {
                String checkUser = "SELECT * FROM user WHERE email = '"+email+"' ";
                Cursor cursor = db.rawQuery(checkUser, null);

                if (cursor.getCount() > 0){
                    String updatePassword = "UPDATE user SET password = '"+newPassword+"' WHERE email = '"+email+"'";
                    db.execSQL(updatePassword);
                    Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    email_forget.setError("User does not exist");
                    email_forget.requestFocus();
                }
            }
        });
    }
}