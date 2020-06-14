package com.example.regenfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ButtonsRecyclerAdapter extends RecyclerView.Adapter<ButtonsRecyclerAdapter.MyViewHolder> {
    ArrayList<String> buttons;

    public ButtonsRecyclerAdapter(ArrayList<String> buttons) {
        this.buttons = buttons;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_buttons_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.Buttons.setText(buttons.get(position));
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Buttons;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Buttons = itemView.findViewById(R.id.buttonList);
        }
    }
}