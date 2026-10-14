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

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    Context context;
    ArrayList<SubCategoryList> arrayList;
    SharedPreferences sp;
    public SubCategoryAdapter(Context context, ArrayList<SubCategoryList> subCategoryList) {
        this.context = context ;
        this.arrayList = subCategoryList;
        sp = context.getSharedPreferences(ConstantSP.DataB, Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new SubCategoryAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView subcat_img;
        TextView subcat_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subcat_img = itemView.findViewById(R.id.cat_img);
            subcat_name = itemView.findViewById(R.id.cat_name);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder holder, int position) {
        holder.subcat_name.setText(arrayList.get(position).getSubcat_name());
        holder.subcat_img.setImageResource(arrayList.get(position).getSubcat_img());

        holder.itemView.setOnClickListener(view -> {
            sp.edit().putInt(ConstantSP.subcategoryid,arrayList.get(position).getSubcat_id()).apply();
            context.startActivity(new Intent(context, Product.class));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
