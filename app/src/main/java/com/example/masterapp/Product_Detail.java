package com.example.masterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Product_Detail extends AppCompatActivity {
    ImageView image,back,wlimg;
    TextView o_rate,d_rate,rating,description;
    SharedPreferences sp;
    SQLiteDatabase db;
    Boolean isToggle = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);
        sp = getSharedPreferences(ConstantSP.DataB,MODE_PRIVATE);

        image = findViewById(R.id.pdimage);
        back = findViewById(R.id.pdback);
        o_rate = findViewById(R.id.original_price);
        d_rate = findViewById(R.id.discounted_price);
        rating = findViewById(R.id.review);
        description = findViewById(R.id.description);

        back.setOnClickListener(view -> {
            startActivity(new Intent(Product_Detail.this,Product.class));
        });

        o_rate.setText(String.valueOf(sp.getInt(ConstantSP.o_rate, 0)));
        d_rate.setText(String.valueOf(sp.getInt(ConstantSP.d_rate,0)));
        rating.setText(sp.getString(ConstantSP.rating,""));
        description.setText(sp.getString(ConstantSP.description,""));

        String fetchimg = "SELECT image FROM product WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' ";
        Cursor cursor = db.rawQuery(fetchimg, null);

        if (cursor.moveToFirst()) {
            String imgName = cursor.getString(cursor.getColumnIndexOrThrow("image"));

            int imgResId = getResources().getIdentifier(
                    imgName,
                    "drawable",
                    getPackageName()
            );

            if (imgResId != 0) {
                image.setImageResource(imgResId);
            }
        }
        cursor.close();

        wlimg = findViewById(R.id.whishlistImg);

        wlimg.setOnClickListener(view -> {
            if(isToggle){
                isToggle = false;
                wlimg.setImageResource(R.drawable.wishlistoff);
                Toast.makeText(Product_Detail.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
            }else{
                isToggle = true;
                wlimg.setImageResource(R.drawable.whishliston);
                Toast.makeText(Product_Detail.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
            }

        });
    }
}