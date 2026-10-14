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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductList> arrayList;
    SharedPreferences sp;

    public ProductAdapter(Context context, ArrayList<ProductList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sp = context.getSharedPreferences(ConstantSP.DataB, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new ProductAdapter.ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView p_img;
        TextView pname, o_rate, d_rate, rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p_img = itemView.findViewById(R.id.p_img);
            pname = itemView.findViewById(R.id.p_name);
            o_rate = itemView.findViewById(R.id.o_rate);
            d_rate = itemView.findViewById(R.id.d_rate);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.p_img.setImageResource(arrayList.get(position).getPimg());
        holder.pname.setText(arrayList.get(position).getPname());
        holder.o_rate.setText(String.valueOf(arrayList.get(position).getO_rate()));
        holder.d_rate.setText(String.valueOf(arrayList.get(position).getD_rate()));
        holder.rating.setText(String.valueOf(arrayList.get(position).getRating()));

        holder.itemView.setOnClickListener(view -> {
            sp.edit()
                    .putInt(ConstantSP.productid,arrayList.get(position).getPid())
                    .putString(ConstantSP.description,arrayList.get(position).getDescription())
                    .putString(ConstantSP.productname,arrayList.get(position).getPname())
                    .putInt(ConstantSP.o_rate,arrayList.get(position).getO_rate())
                    .putInt(ConstantSP.d_rate,arrayList.get(position).getD_rate())
                    .putString(ConstantSP.rating,arrayList.get(position).getRating())
                    .apply();

            context.startActivity(new Intent(context,Product_Detail.class));

        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
