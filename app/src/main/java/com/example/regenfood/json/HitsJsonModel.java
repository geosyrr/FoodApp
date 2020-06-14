package com.example.regenfood.json;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class HitsJsonModel implements Serializable {
    private RecipeJsonModel recipe;
    private boolean bookmarked;
    private boolean bought;

    public RecipeJsonModel getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeJsonModel recipe) {
        this.recipe = recipe;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
