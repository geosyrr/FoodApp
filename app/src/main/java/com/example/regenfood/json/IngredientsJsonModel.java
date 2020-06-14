package com.example.regenfood.json;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class IngredientsJsonModel implements Serializable {
    private String text;
    private double weight;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
