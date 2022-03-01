package com.example.planningevent;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.planningevent.Evenement;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context c;
    List<Evenement> mdata;

    public MyAdapter(Context c, List<Evenement> mdata) {
        this.c = c;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(mdata.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
}
