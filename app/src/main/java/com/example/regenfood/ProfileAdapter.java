package com.example.regenfood;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private Context mContext;
    private ArrayList<String> ArrayData;
    private ArrayList<String> uris;
    private ArrayList<String> labels;

    public ProfileAdapter(ArrayList<String> ArrayData, Context mContext) {
        this.ArrayData = ArrayData;
        this.mContext = mContext;
        this.uris = new ArrayList<>();
        this.labels = new ArrayList<>();
        init();
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_view_holder, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.getTextName().setText(labels.get(position));
        holder.getTextCalories().setText(uris.get(position));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ProfileActivity) {
                    ((ProfileActivity) mContext).showRecipe(("https://api.edamam.com/search?app_id=ccbcf7ca&app_key=e14438ed975fafb38752cbdd559e3d77&r=") + (uris.get(position)).replaceAll("/", "%2F").replaceAll(":", "%3A").replaceAll("#", "%23"));
                    //((ProfileActivity) mContext).showRecipe(("https://api.edamam.com/search?app_id=5476cbdc&app_key=173291640885e0b1116a2f18f1adaa74&r=") + (uris.get(position)).replaceAll("/", "%2F").replaceAll(":", "%3A").replaceAll("#", "%23"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return uris.size();
    }

    public void init() {
        for (int i = 0; i < ArrayData.size(); i++) {
            if (!ArrayData.get(i).equals("")) {
                if (ArrayData.get(i).startsWith("http")) {
                    uris.add(ArrayData.get(i));
                } else {
                    labels.add(ArrayData.get(i));
                }
            }
        }
    }
}