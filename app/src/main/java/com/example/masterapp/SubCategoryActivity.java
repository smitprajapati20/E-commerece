package com.example.masterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    RecyclerView subcategory_recycler;
    ArrayList<SubCategoryList> arrayList;
    SQLiteDatabase db;
    SharedPreferences sp;
    ImageView back;

    int[] subid = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
    int[] catid = {1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6};
    String[] subname = {"Fridges","Headphones","Mobile","Bottoms","Shirts",
            "T-Shirts","HorrorBook","RomanticBook","ThrillerBook",
            "FormalShoes","SneakerShoes","SportsShoes","HandBag","Laptopbag"
            ,"Toatbag","AnalogWatches","DigitalWatches","SmartWatches"};
    int[] subimg = {R.drawable.fridges,R.drawable.headphones,R.drawable.mobile,
            R.drawable.bottoms,R.drawable.shirts,R.drawable.tshirts,
            R.drawable.horror,R.drawable.romantic,R.drawable.thriller,
            R.drawable.formals,R.drawable.sneakers,R.drawable.sportsshoes,
            R.drawable.handbags,R.drawable.laptopbag, R.drawable.toatbags,
            R.drawable.analog,R.drawable.digital,R.drawable.smart};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);
        sp = getSharedPreferences(ConstantSP.DataB,MODE_PRIVATE);

        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), email VARCHAR(30), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        String CategoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), image VARCHAR)";
        db.execSQL(CategoryTable);

        String SubCategoryTable = "CREATE TABLE IF NOT EXISTS sub_category(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid INTEGER,name VARCHAR(50), image VARCHAR)";
        db.execSQL(SubCategoryTable);

        for(int i=0; i< subid.length;i++){
            String checkSubCategory = "SELECT * FROM sub_category WHERE name = '"+subname[i]+"' ";
            Cursor cursor = db.rawQuery(checkSubCategory,null);

            if(cursor.getCount()==0){
                String insertSubCategory = "INSERT INTO sub_category VALUES(NULL, '"+catid[i]+"','"+subname[i]+"', '"+subimg[i]+"')";
                db.execSQL(insertSubCategory);
            }
        }

        arrayList = new ArrayList<>();

        String fetchSubCategory = "SELECT * FROM sub_category WHERE categoryid = '"+sp.getInt(ConstantSP.categoryid, 0)+"'";
        Cursor cursor = db.rawQuery(fetchSubCategory,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                SubCategoryList sublist = new SubCategoryList();
                sublist.setSubcat_id(cursor.getInt(0));
                sublist.setSubcat_name(cursor.getString(2));
                sublist.setSubcat_img(cursor.getInt(3));
                arrayList.add(sublist);
            }
        }

        subcategory_recycler = findViewById(R.id.subcategory_recycler);
        subcategory_recycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));
        subcategory_recycler.setAdapter(new SubCategoryAdapter(SubCategoryActivity.this,arrayList));

        back = findViewById(R.id.scback);
        back.setOnClickListener(view -> {
            startActivity(new Intent(SubCategoryActivity.this,CategoriesActivity.class));
            finish();
        });
    }
}