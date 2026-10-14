package com.example.masterapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView Category_recycler;
    SQLiteDatabase db;

    int[] idArray = {1,2,3,4,5,6};
    String[] nameArray = {"Electronics","Clothes","Books","Shoes","Bags","Watches"};
    int[] imgArray = {R.drawable.electronics,R.drawable.clothes,R.drawable.books,R.drawable.shoes,R.drawable.bags,R.drawable.watches};
    ArrayList<CategoryList> arrayList;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);

        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), email VARCHAR(30), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        String CategoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), image VARCHAR)";
        db.execSQL(CategoryTable);

        for(int i=0; i< idArray.length;i++){
            String checkCategory = "SELECT * FROM category WHERE name = '"+nameArray[i]+"' ";
            Cursor cursor = db.rawQuery(checkCategory,null);

            if(cursor.getCount()==0){
                String insertCategory = "INSERT INTO category VALUES(NULL, '"+nameArray[i]+"', '"+imgArray[i]+"')";
                db.execSQL(insertCategory);
            }
        }

        arrayList = new ArrayList<>();

        String fetchCategory = "SELECT * FROM category";
        Cursor cursor = db.rawQuery(fetchCategory, null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                CategoryList list = new CategoryList();
                list.setId(cursor.getInt(0));
                list.setName(cursor.getString(1));
                list.setImage(cursor.getInt(2));
                arrayList.add(list);
            }
        }

        Category_recycler = findViewById(R.id.category_recycler);

        Category_recycler.setLayoutManager(new LinearLayoutManager(this));

//        Category_recycler.setAdapter(new CategoryAdapter(CategoriesActivity.this,idArray,nameArray,imgArray));
        Category_recycler.setAdapter(new CategoryAdapter(CategoriesActivity.this,arrayList));

        back = findViewById(R.id.cback);
        back.setOnClickListener(view -> {
            startActivity(new Intent(CategoriesActivity.this,DashboardActivity.class));
            finish();
        });
    }
}

