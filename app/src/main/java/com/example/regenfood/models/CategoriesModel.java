package com.example.regenfood.models;

import java.util.ArrayList;

public class CategoriesModel {
    private String SearchUrl;

    public CategoriesModel(String url) {
        this.SearchUrl=url;
    }

    public String getUrl() {
        return SearchUrl;
    }

    public void setUrl(String url) {
        this.SearchUrl = url;
    }


}
