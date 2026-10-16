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

import java.util.ArrayList;

public class Product extends AppCompatActivity {
    RecyclerView productRV;
    SQLiteDatabase db;
    SharedPreferences sp;
    ArrayList<ProductList> arrayList;
    ImageView back;

    int[] Pid = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
    int[] subcat_id = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9,10,10,11,11,12,12,13,13,14,14,15,15,16,16,17,17,18,18};
    String[] Pname = {"Samsung","Whirpool","Sony","JBL","Samsung","GooglePixel",
            "Trouser","Cargo","Linen","BoxyFit","Oversized","Printed",
            "IT","The Shining","Twilight","The Notebook","The Silent Patient","Gone Girl",
            "Clarks","Red Tape","Converse","Vans","ASICS","New Balance",
            "Michael Kors","Lavie","Targus","American Tourister","Baggu","Chumbak",
            "Rolex","Titan","Casio","Timex","Apple","Samsung"};

    int[] Pimg = {R.drawable.samsungfridge,R.drawable.whirpoolfridge,R.drawable.sonyheadphones,R.drawable.jblheadphones,
            R.drawable.samsungmobile,R.drawable.pixel,R.drawable.trouser,R.drawable.cargo,
            R.drawable.linenshirt,R.drawable.boxyfitshirt,R.drawable.oversizedtshirt,R.drawable.printedtshirt,
            R.drawable.itbook,R.drawable.theshiningbook,R.drawable.twilightbook,R.drawable.thenotebbok,
            R.drawable.thesilentpateintbook,R.drawable.gonegirlbook,R.drawable.clarksformal,R.drawable.redtapeformal,
            R.drawable.conversesneakers,R.drawable.vanssneakers,R.drawable.asicssportsshoes,R.drawable.newbalancesports,
            R.drawable.michaelkorshandbag,R.drawable.laviehandbag,R.drawable.targuslaptopbag,R.drawable.americanlaptopbag,
            R.drawable.baggutote,R.drawable.chumbaktote,R.drawable.rolex,R.drawable.titan,
            R.drawable.casio,R.drawable.timex,R.drawable.apple,R.drawable.samsungwatch};
    int[] O_rate = {25000,12000,18000,2500,20000,35000,
            1200,900,1500,800,700,600,
            500,450,350,400,300,250,
            3500,2800,4500,5000,6000,5500,
            12000,2500,1800,2200,1500,2000,
            45000,8000,2500,3000,40000,25000};

    int[] D_rate = {20000,9600,14400,2000,16000,28000,
            960,720,1200,640,560,480,
            400,360,280,320,240,200,
            2800,2240,3600,4000,4800,4400,
            9600,2000,1440,1760,1200,1600,
            36000,6400,2000,2400,32000,20000};

    String[] rating = {"4.5⭐","4.2⭐","4.6⭐","4.3⭐","4.7⭐","4.4⭐",
            "4.3⭐","4.2⭐","4.4⭐","4.1⭐","4.5⭐","4.3⭐","4.2⭐","4.4⭐",
            "4.6⭐","4.5⭐","4.3⭐","4.7⭐","4.5⭐","4.4⭐","4.6⭐","4.3⭐",
            "4.7⭐","4.5⭐","4.6⭐","4.4⭐","4.3⭐","4.5⭐","4.2⭐","4.4⭐",
            "4.8⭐","4.5⭐","4.6⭐","4.3⭐","4.7⭐","4.6⭐"};

    String[] Pdescription = {
            "Samsung Refrigerator featuring a modern design, spacious food storage, adjustable shelves, and convenient compartments to keep everyday groceries organized. Designed for convenient food storage and everyday household use.",
            "Whirlpool Refrigerator designed for everyday cooling and organized food storage. Its practical interior layout provides space for fresh produce, beverages, dairy products, and other household essentials.",
            "Sony Headphones designed for an immersive listening experience with clear audio and comfortable ear cushions. Suitable for enjoying music, watching movies, attending online meetings, and everyday entertainment.",
            "JBL Headphones featuring a stylish design and an engaging audio experience. Ideal for music lovers, entertainment, online classes, and everyday listening at home or while travelling.",
            "Samsung Galaxy Smartphone featuring a sleek design, an intuitive Android experience, and versatile features for communication, photography, entertainment, and everyday productivity. Exact performance and camera specifications depend on the model.",
            "Google Pixel Smartphone featuring Googles Android experience and intelligent photography capabilities. Designed for capturing everyday moments, using Google services, browsing, communication, and smooth everyday smartphone use. Features vary by model.",
            "Mens Trousers featuring a versatile design suitable for office wear, formal occasions, and everyday styling. Pair them with shirts, polos, or casual tops to create a smart and comfortable outfit.",
            "Mens Cargo Pants featuring a utility-inspired design with multiple pockets and a relaxed casual appearance. Suitable for everyday outings, travel, and streetwear-inspired outfits.",
            "Linen Clothing featuring a lightweight, breathable-feel fabric and a relaxed appearance. A versatile choice for warm-weather dressing, holidays, casual outings, and effortless everyday styling.",
            "Boxy Fit Clothing featuring a relaxed silhouette with a contemporary shape. Designed for a modern casual look and easy pairing with jeans, trousers, and sneakers.",
            "Oversized Clothing featuring a loose-fitting silhouette for a relaxed streetwear-inspired appearance. Suitable for casual outfits and pairing with jeans, cargo pants, or shorts.",
            "Printed Clothing featuring attractive patterns that add character to everyday outfits. Easy to style with jeans, trousers, or shorts for casual occasions and outings.",
            "IT Book covering information technology topics for readers interested in computers, software, networking, or digital technologies. Refer to the specific title and edition for its exact syllabus and subject coverage.",
            "The Shining by Stephen King is a psychological horror novel following Jack Torrance and his family as they stay at the isolated Overlook Hotel. An unsettling story of isolation, supernatural forces, and psychological suspense.",
            "Twilight by Stephenie Meyer is a romantic fantasy novel about Bella Swan, whose life changes after meeting Edward Cullen, a mysterious vampire. Blending romance, fantasy, and suspense, it begins the popular Twilight series.",
            "The Notebook by Nicholas Sparks is a romantic novel about Noah Calhoun and Allie Nelson, whose enduring relationship is shaped by memories, separation, and the passage of time. A moving story of love and commitment.",
            "The Silent Patient by Alex Michaelides is a psychological thriller about Alicia Berenson, a painter who stops speaking after her husband is murdered. A psychotherapist becomes determined to uncover the truth behind her silence.",
            "Gone Girl by Gillian Flynn is a psychological mystery thriller centered on the disappearance of Amy Dunne and the growing suspicion surrounding her husband, Nick. A suspenseful story of deception, unreliable perspectives, and unexpected revelations.",
            "Clarks Shoes combine timeless styling with designs suited to everyday comfort and versatile dressing. Suitable for office wear, formal occasions, and smart-casual outfits, depending on the selected style.",
            "Red Tape Shoes feature contemporary designs suited to casual wear, everyday outings, and sporty styling. Pair them with jeans, joggers, or casual trousers for a versatile everyday look.",
            "Converse Shoes are known for their iconic canvas sneaker designs and distinctive casual style. Easy to pair with jeans, shorts, and everyday outfits, they offer a timeless addition to a casual wardrobe.",
            "Vans Shoes feature a recognizable skate-inspired aesthetic with versatile designs for casual and streetwear outfits. Pair them with denim, relaxed trousers, or shorts for an everyday look.",
            "ASICS Sports Shoes are designed for active lifestyles, with models developed for running, training, and other sporting activities. Choose the appropriate model based on your activity, fit, and cushioning preferences.",
            "New Balance Shoes combine sporty styling with designs for walking, running, training, and everyday wear. Different models offer different cushioning, support, and fit options to suit individual needs.",
            "Michael Kors Handbag featuring a fashion-forward design suitable for everyday outings, work, and special occasions. A stylish accessory for carrying personal essentials while complementing your outfit.",
            "Lavie Handbag combines contemporary styling with practical space for everyday essentials. Suitable for shopping, college, work, and outings, depending on the selected size and design.",
            "Targus Laptop Bag designed to carry a compatible laptop along with chargers, documents, and essential accessories. A practical option for commuting, office use, college, and business travel.",
            "American Tourister Travel Bag designed to help organize clothing and travel essentials for trips and holidays. Choose from available sizes and designs according to your packing and travel requirements.",
            "Baggu Bag features a practical, versatile design for carrying everyday essentials, shopping items, and personal belongings. A convenient option for errands, outings, and everyday use.",
            "Chumbak Bag features distinctive prints, colorful designs, and artistic styling. Suitable for carrying daily essentials while adding a playful design element to casual outfits.",
            "Rolex Watch represents luxury watchmaking, with attention to craftsmanship, precision, and distinctive design. Specific materials, movement, functions, and water resistance depend on the original model.",
            "Titan Watch combines stylish design with everyday timekeeping. Available in a variety of styles, dial designs, and strap materials to complement formal, business, and casual outfits.",
            "Casio Watch offers practical timekeeping with a wide range of designs, from classic everyday watches to digital and sport-oriented models. Features vary by collection and model.",
            "Timex Watch combines versatile styling with practical timekeeping for everyday use. Choose from classic analog designs, casual watches, and other collections according to your preferences.",
            "Apple Watch is a smartwatch designed to work with compatible iPhones, offering notifications, activity tracking, workout features, and useful everyday apps. Available features depend on the Apple Watch model and software version.",
            "Samsung Galaxy Watch is a smartwatch offering notifications, activity tracking, workout tools, and smartphone connectivity with compatible devices. Health and smart features vary by model and connected phone compatibility."

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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




        for(int i=0; i< Pid.length;i++){
            String checkproduct = "SELECT * FROM product WHERE name = '"+Pname[i]+"' ";
            Cursor cursor = db.rawQuery(checkproduct,null);

            if(cursor.getCount()==0){
                String insertProduct = "INSERT INTO product VALUES(NULL, '"+subcat_id[i]+"','"+Pname[i]+"','"+Pimg[i]+"','"+O_rate[i]+"','"+D_rate[i]+"','"+rating[i]+"','"+Pdescription[i]+"')";
                db.execSQL(insertProduct);
            }
        }

        arrayList = new ArrayList<>();

        String fetchproduct = "SELECT * FROM product WHERE subcategoryid = '"+sp.getInt(ConstantSP.subcategoryid, 0)+"'";
        Cursor cursor = db.rawQuery(fetchproduct,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                ProductList prolist = new ProductList();
                prolist.setPid(cursor.getInt(0));
                prolist.setPname(cursor.getString(2));
                prolist.setPimg(cursor.getInt(3));
                prolist.setO_rate(cursor.getInt(4));
                prolist.setD_rate(cursor.getInt(5));
                prolist.setRating(cursor.getString(6));
                prolist.setDescription(cursor.getString(7));
                arrayList.add(prolist);
            }
        }
        productRV = findViewById(R.id.product_recycler);
        productRV.setLayoutManager(new LinearLayoutManager(this));
        productRV.setAdapter(new ProductAdapter(Product.this,arrayList));

        back = findViewById(R.id.prback);
        back.setOnClickListener(view -> {
            startActivity(new Intent(Product.this,SubCategoryActivity.class));
            finish();
        });




    }
}