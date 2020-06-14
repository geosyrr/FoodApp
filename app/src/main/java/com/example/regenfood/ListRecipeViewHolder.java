package com.example.regenfood;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListRecipeViewHolder extends RecyclerView.ViewHolder {
    private MaterialTextView recipeName;
    private CircleImageView image;
    private ConstraintLayout recipe;

    public MaterialTextView getRecipeName() {
        return recipeName;
    }

    public ImageView getImage() {
        return image;
    }

    public ConstraintLayout getRecipe() {
        return recipe;
    }

    public ListRecipeViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipe);
        image = itemView.findViewById(R.id.image);
        recipe = itemView.findViewById(R.id.recipelist);
    }
}
