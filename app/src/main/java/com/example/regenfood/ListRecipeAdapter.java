package com.example.regenfood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.regenfood.json.JsonModel;

import java.util.List;

public class ListRecipeAdapter extends RecyclerView.Adapter<ListRecipeViewHolder> {
    private static final String TAG = "ListRecipeAdapter";

    private Context context;
    private List<String> Images;
    private List<String> Labels;
    private JsonModel Recipies;
    private String url;

    public ListRecipeAdapter(Context context, List<String> images, List<String> labels, JsonModel Recipies, String url) {
        this.context = context;
        Images = images;
        Labels = labels;
        this.Recipies = Recipies;
        this.url = url;
    }

    @NonNull
    @Override
    public ListRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_recipes, parent, false);

        return new ListRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecipeViewHolder holder, int position) {

        Log.i(TAG, "onBindViewHolder: line44");
        Glide.with(this.context).asBitmap().load(Images.get(position)).into(holder.getImage());
        holder.getRecipeName().setText(Labels.get(position));
        holder.getRecipe().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked item-recipe-list");
                if (context instanceof ListRecipesActivity) {
                    ((ListRecipesActivity) context).showRecipe(url + (Recipies.getHits().get(position).getRecipe().getUri()).replaceAll("/", "%2F").replaceAll(":", "%3A").replaceAll("#", "%23"), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Images.size();
    }
}
