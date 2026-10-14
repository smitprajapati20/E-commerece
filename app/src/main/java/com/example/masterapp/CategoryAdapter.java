package com.example.masterapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
//    int[] idArray;
//    String[] nameArray;
//    int[] imgArray;
    ArrayList<CategoryList> arrayList;
    SharedPreferences sp;

//    public CategoryAdapter(Context context, int[] idArray, String[] nameArray, int[] imgArray) {
//        this.context = context;
//        this.idArray = idArray;
//        this.nameArray = nameArray;
//        this.imgArray = imgArray;
//    }
    public CategoryAdapter(Context context, ArrayList<CategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSP.DataB, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_img;
        TextView cat_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_img = itemView.findViewById(R.id.cat_img);
            cat_name = itemView.findViewById(R.id.cat_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
//        holder.cat_img.setImageResource(imgArray[position]);
//        holder.cat_name.setText(nameArray[position]);

        holder.cat_name.setText(arrayList.get(position).getName());
        holder.cat_img.setImageResource(arrayList.get(position).getImage());

        holder.itemView.setOnClickListener(view -> {
            sp.edit().putInt(ConstantSP.categoryid,arrayList.get(position).getId()).apply();
            context.startActivity(new Intent(context, SubCategoryActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}

