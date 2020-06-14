package com.example.regenfood.json;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class JsonModel implements Serializable {
    private String q;              //query
    private int from;              //index to start from
    private int to;                //till that index
    private boolean more;          // ....
    private int count;             // counter of recipes
    private List<HitsJsonModel> hits; // results

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<HitsJsonModel> getHits() {
        return hits;
    }

    public void setHits(List<HitsJsonModel> hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "{" +
                "q='" + q + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", more=" + more +
                ", count=" + count +
                ", hits=" + hits +
                '}';
    }
}
