package com.example.regenfood;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView textName;
    private ConstraintLayout profileRecycler;
    private View view;
    private TextView textCalories;

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setTextName(TextView textName) {
        this.textName = textName;
    }

    public void setProfileRecycler(ConstraintLayout profileRecycler) {
        this.profileRecycler = profileRecycler;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setTextCalories(TextView textCalories) {
        this.textCalories = textCalories;
    }

    public void setTextTime(TextView textTime) {
        this.textTime = textTime;
    }

    public void setFavoriteButt(ToggleButton favoriteButt) {
        this.favoriteButt = favoriteButt;
    }

    private TextView textTime;
    private ToggleButton favoriteButt;

    public ImageView getImage() {
        return image;
    }

    public TextView getTextName() {
        return textName;
    }

    public ConstraintLayout getProfileRecycler() {
        return profileRecycler;
    }

    public View getView() {
        return view;
    }

    public TextView getTextCalories() {
        return textCalories;
    }

    public TextView getTextTime() {
        return textTime;
    }

    public ToggleButton getFavoriteButt() {
        return favoriteButt;
    }

    public ProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.favImage);
        profileRecycler = itemView.findViewById(R.id.favRecPro);
        view = itemView.findViewById(R.id.favViewDesc);
        textCalories = itemView.findViewById(R.id.calories);
        textTime = itemView.findViewById(R.id.prepTime);
        favoriteButt = itemView.findViewById(R.id.favoriteTog);
        textName = itemView.findViewById(R.id.favDescription);
    }
}
