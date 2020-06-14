package com.example.regenfood.json;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONStringer;

import java.io.Serializable;
import java.util.List;

public class RecipeJsonModel implements Serializable {
    private String uri;
    private String label;
    private String image;
    private String source;
    private String url;
    private String shareAs;
    private double yield;
    private List<String> dietLabels;
    private List<String> healthLabels;
    private List<String> cautions;
    private List<String> ingredientLines;
    private List<IngredientsJsonModel> ingredients;
    private double calories;
    private double totalWeight;
    private double totalTime;
    private JsonElement totalNutrients;
    private JsonElement totalDaily;
    private JsonElement digest;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public List<String> getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<String> getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public void setCautions(List<String> cautions) {
        this.cautions = cautions;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public List<IngredientsJsonModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientsJsonModel> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public JsonElement getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(JsonElement totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public JsonElement getTotalDaily() {
        return totalDaily;
    }

    public void setTotalDaily(JsonElement totalDaily) {
        this.totalDaily = totalDaily;
    }

    public JsonElement getDigest() {
        return digest;
    }

    public void setDigest(JsonElement digest) {
        this.digest = digest;
    }

    @Override
    public String toString() {
        return "{"
                + "\"uri\" : uri,"
                + "\"label\" : label,"
                + "\"image\" : image,"
                + "\"source\" : source,"
                + "\"url\" : url,"
                + "\"shareAs\" : shareAs,"
                + "\"yield\" : yield,"
                + "\"dietLabels\" : dietLabels,"
                + "\"healthLabels\" : healthLabels,"
                + "\"cautions\" : cautions,"
                + "\"ingredientLines\" : ingredientLines,"
                + "\"ingredients\" : ingredients,"
                + "\"calories\" : calories,"
                + "\"totalWeight\" : totalWeight,"
                + "\"totalTime\" : totalTime,"
                + "\"totalNutrients\" : totalNutrients,"
                + "\"totalDaily\" : totalDaily,"
                + "\"digest\" : digest"
                + "}";
    }
}
