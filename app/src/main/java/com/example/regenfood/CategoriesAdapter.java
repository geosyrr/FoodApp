package com.example.regenfood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private ArrayList<String> mImageNames;
    private ArrayList<String> mImages;
    private Context mContext;

    public CategoriesAdapter(ArrayList<String> mImageNames, ArrayList<String> mImages, Context mContext) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Glide.with(mContext).asBitmap().load(mImages.get(position)).into(holder.image);
        holder.text.setText(mImageNames.get(position));
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof CategoriesActivity) {
                    ((CategoriesActivity) mContext).dietOrHealth(mImageNames.get(position), v, mImageNames.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }
}
