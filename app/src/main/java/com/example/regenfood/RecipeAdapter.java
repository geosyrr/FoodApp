package com.example.regenfood;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {
    private ArrayList<String> ingredients;

    public RecipeAdapter(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_recipe_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ingredients.setText(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        if (ingredients != null) return ingredients.size();
        else return 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ingredients;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredients = itemView.findViewById(R.id.ingredientList);
        }
    }
}
