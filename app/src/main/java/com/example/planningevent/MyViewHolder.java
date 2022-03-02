package com.example.planningevent;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView eventName;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        eventName = itemView.findViewById(R.id.eventName);
    }
}
