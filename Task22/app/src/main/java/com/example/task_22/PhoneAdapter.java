package com.example.task_22;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private final List<LinkData> data;
    private final Context context;
    private int lastPosition;

    public PhoneAdapter(Context context, List<LinkData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.phone_row, parent, false);

        return new PhoneViewHolder(view);
    }

    @SuppressLint({"ShowToast", "IntentReset"})
    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {

        LinkData data = this.data.get(position);
        holder.name.setText(data.getName());
        holder.phone.setText(data.getPhone());

        holder.call.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + data.getPhone()));

            context.startActivity(intent);
        });

        holder.message.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.setData(Uri.parse("smsto:" + data.getPhone()));
            context.startActivity(smsIntent);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class PhoneViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name, phone;
        ImageButton call, message;


        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.phone_card);
            name = itemView.findViewById(R.id.phoneName);
            phone = itemView.findViewById(R.id.phoneNumber);
            call = itemView.findViewById(R.id.btnCall);
            message = itemView.findViewById(R.id.btnMessage);
        }
    }
}
