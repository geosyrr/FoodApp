package com.example.regenfood;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    AppCompatImageView image;
    MaterialTextView text;
    ConstraintLayout category;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.cat_image);
        text = itemView.findViewById(R.id.cat_text);
        category = itemView.findViewById(R.id.category);
    }
}