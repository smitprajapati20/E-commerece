package com.example.masterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Product_Detail extends AppCompatActivity {
    ImageView image,back,wlimg,cart;
    LinearLayout cartlayout;
    TextView o_rate,d_rate,rating,description,minus,plus,cartvalue;
    SharedPreferences sp;
    SQLiteDatabase db;
    Boolean isToggle;
    int cartTotal = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = openOrCreateDatabase(ConstantSP.DataB,MODE_PRIVATE,null);
        sp = getSharedPreferences(ConstantSP.DataB,MODE_PRIVATE);

        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), email VARCHAR(30), contact VARCHAR(10), password VARCHAR(20))";
        db.execSQL(userTable);

        String CategoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50), image VARCHAR)";
        db.execSQL(CategoryTable);

        String SubCategoryTable = "CREATE TABLE IF NOT EXISTS sub_category(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid INTEGER,name VARCHAR(50), image VARCHAR)";
        db.execSQL(SubCategoryTable);

        String ProductTable = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT,subcategoryid INTEGER,name VARCHAR(50), image VARCHAR, o_rate INTEGER, d_rate INTEGER, rating VARCHAR, description TEXT)";
        db.execSQL(ProductTable);

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist(wishlistid INTEGER PRIMARY KEY AUTOINCREMENT, productid INTEGER, userid INTEGER)";
        db.execSQL(wishlistTable);

        String cartTable = "CREATE TABLE IF NOT EXISTS cart(cartid INTEGER PRIMARY KEY AUTOINCREMENT, productid INTEGER, userid INTEGER, quantity INTEGER)";
        db.execSQL(cartTable);

        image = findViewById(R.id.pdimage);
        back = findViewById(R.id.pdback);
        o_rate = findViewById(R.id.original_price);
        d_rate = findViewById(R.id.discounted_price);
        rating = findViewById(R.id.review);
        description = findViewById(R.id.description);
        wlimg = findViewById(R.id.whishlistImg);
        cartlayout = findViewById(R.id.cartlayout);
        cart = findViewById(R.id.cart);
        cartvalue = findViewById(R.id.cartvalue);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);

        back.setOnClickListener(view -> {
            startActivity(new Intent(Product_Detail.this,Product.class));
        });

        o_rate.setText(String.valueOf(sp.getInt(ConstantSP.o_rate, 0)));
        d_rate.setText(String.valueOf(sp.getInt(ConstantSP.d_rate,0)));
        rating.setText(sp.getString(ConstantSP.rating,""));
        image.setImageResource(sp.getInt(ConstantSP.productimg,0));
        description.setText(sp.getString(ConstantSP.description,""));

        String checkWishlist = "SELECT * FROM wishlist WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
        Cursor cursor = db.rawQuery(checkWishlist, null);
        if(cursor.getCount()>0){
            isToggle = true;
            wlimg.setImageResource(R.drawable.whishliston);
        }
        else{
            isToggle = false;
            wlimg.setImageResource(R.drawable.wishlistoff);
        }

        wlimg.setOnClickListener(view -> {
            if(isToggle){
                isToggle = false;
                String deleteFromWishlist = "DELETE FROM wishlist WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
                db.execSQL(deleteFromWishlist);
                wlimg.setImageResource(R.drawable.wishlistoff);
                Toast.makeText(Product_Detail.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
            }
            else{
                isToggle = true;
                String insertInWishlist = "INSERT INTO wishlist VALUES(NULL, '"+sp.getInt(ConstantSP.productid, 0)+"', '"+sp.getInt(ConstantSP.userid, 0)+"')";
                db.execSQL(insertInWishlist);
                wlimg.setImageResource(R.drawable.whishliston);
                Toast.makeText(Product_Detail.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
            }

        });

//        String checkcart = "SELECT * FROM cart WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
//        Cursor cursorCart = db.rawQuery(checkcart, null);
//        if(cursorCart.getCount()>0){
//            cartlayout.setVisibility(View.VISIBLE);
//            cart.setVisibility(View.GONE);
//            Log.d("CART_QTY", String.valueOf(cursorCart.getCount()));
//            cartTotal = cursorCart.getInt(2);
//            Log.d("CART_QTY", String.valueOf(cartTotal));
////            cartvalue.setText(String.valueOf(cartTotal));
////            String fetchquentity = "SELECT * FROM cart WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
////            Cursor cursorQuantity = db.rawQuery(fetchquentity, null);
////            if(cursorQuantity.getCount()>0){
////                Log.d("Cart_QTY", String.valueOf(cursorQuantity.getCount()));
////                cartTotal = cursorQuantity.getInt(3);
////                Log.d("Cart_QTY", String.valueOf(cartTotal));
//////                cartvalue.setText(String.valueOf(cursorQuantity.getInt(3)));
////            }
////            else{
////                cartTotal = 0;
////            }
//        }
//        else{
//            cart.setVisibility(View.VISIBLE);
//            cartlayout.setVisibility(View.GONE);
//        }


        String checkCart = "SELECT * FROM cart WHERE productid = '" + sp.getInt(ConstantSP.productid, 0) + "' AND userid = '" + sp.getInt(ConstantSP.userid, 0) + "'";
        Cursor cursorCart = db.rawQuery(checkCart, null);

        if (cursorCart.moveToFirst()) {
            cartlayout.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
            cartTotal = cursorCart.getInt(3);
            cartvalue.setText(String.valueOf(cartTotal));
        } else {
            cartTotal = 0;
            cart.setVisibility(View.VISIBLE);
            cartlayout.setVisibility(View.GONE);
        }

        cart.setOnClickListener(v ->{
            Toast.makeText(Product_Detail.this, "Added To Cart!", Toast.LENGTH_SHORT).show();
            cartTotal = 1;
            String insertInCart = "INSERT INTO cart VALUES(NULL, '"+sp.getInt(ConstantSP.productid, 0)+"', '"+sp.getInt(ConstantSP.userid, 0)+"','"+cartTotal+"')";
            db.execSQL(insertInCart);
            cartvalue.setText(String.valueOf(cartTotal));
            cartlayout.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
        });

        minus.setOnClickListener(v ->{
            // jyare cart ni value 1 hase jo tyare minus par click thase to obviously 1 - 1 = 0 thase
            // to tyare aapde aa layout hataine fari carticon lavisu etle aa condition rakhi chhe
            // and sathe sathe cartTable mathi ene delete karai desu
            if(cartTotal==1){
                cartTotal = 0;
//                String deleteFromCart = "DELETE FROM cart WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
//                db.execSQL(deleteFromCart);
                cartlayout.setVisibility(View.GONE);
                cart.setVisibility(View.VISIBLE);
            }
            // jo cart ni value 1 nai hoy and click thase to normaly just -1 thase.
            // and navi value ne aapde update karai daisu
            else{
                cartTotal--;
//                String updateMinusCart = "UPDATE cart SET quantity = quantity - 1 WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
//                db.execSQL(updateMinusCart);
                cartvalue.setText(String.valueOf(cartTotal));
            }
        });

        // jo cart ni value kai b hoy and plus click thase to +1 thase.
        // and navi value ne aapde update karai daisu.
        plus.setOnClickListener(view -> {
            cartTotal++;
//            String updatePlusCart = "UPDATE cart SET quantity = quantity + 1 WHERE productid = '"+sp.getInt(ConstantSP.productid, 0)+"' AND userid = '"+sp.getInt(ConstantSP.userid, 0)+"'";
//            db.execSQL(updatePlusCart);
            cartvalue.setText(String.valueOf(cartTotal));
        });

    }

    // aa tyare work karse(run thase) jyare app ma biju kaai kaam nai thatu hoy
    // ka pacchi jyare aapde aa page mathi bare nikdisu tyare
    @Override
    protected void onPause() {
        super.onPause();

        int productid = sp.getInt(ConstantSP.productid, 0);
        int userid = sp.getInt(ConstantSP.userid, 0);

        if (cartTotal > 0) {
            String updateCart = "UPDATE cart SET quantity = " + cartTotal + " WHERE productid = " + productid + " AND userid = " + userid;
            db.execSQL(updateCart);
        } else {
            String deleteCart = "DELETE FROM cart WHERE productid = " + productid + " AND userid = " + userid;
            db.execSQL(deleteCart);
        }
    }
}