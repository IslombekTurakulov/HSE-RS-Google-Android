package com.iuturakulov.task5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterOfClass extends RecyclerView.Adapter<AdapterOfClass.MyViewHolder> {

    private final List<String> language;
    private final List<String> description;
    private final List<Integer> images;
    private final Context context;

    public AdapterOfClass(Context context, List<String> language, List<String> description, List<Integer> images) {
        this.language = language;
        this.description = description;
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.lang.setText(language.get(position));
        holder.desc.setText(description.get(position));
        holder.image.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return language.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView desc, lang;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageOfLanguage);
            desc = itemView.findViewById(R.id.descriptionOfLanguage);
            lang = itemView.findViewById(R.id.nameOfLanguage);
        }
    }
}
