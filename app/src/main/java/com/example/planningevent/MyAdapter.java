package com.example.planningevent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context c;
    List<Evenement> mData;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;

    public MyAdapter(Context c, List<Evenement> mData) {
        this.c = c;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.activity_test_swipe, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.aff.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextInputLayout eventName;
        TextView aff;
        ImageView img_delete, img_consult, img_star;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aff = itemView.findViewById(R.id.eventName);
            eventName = itemView.findViewById(R.id.textInputEventName);
            img_delete = itemView.findViewById(R.id.img_delete);
            img_star = itemView.findViewById(R.id.img_star);
            img_consult = itemView.findViewById(R.id.img_consult);
            TextView txtView = itemView.findViewById(R.id.eventName);

            DatabaseReference myRef3 = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events");
            myRef3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        if(txtView.getText().toString().trim().equals(String.valueOf(ds.child("/name").getValue()))){
                            boolean favorite = (boolean) ds.child("/favorite").getValue();
                            if(favorite){
                                img_star.setImageDrawable(img_star.getResources().getDrawable(R.drawable.ic_star_white_24dp));
                            }
                            else{
                                img_star.setImageDrawable(img_star.getResources().getDrawable(R.drawable.ic_baseline_star_border_24));
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView txtView = itemView.findViewById(R.id.eventName);

                    myRef = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                if(txtView.getText().toString().trim().equals(String.valueOf(ds.child("/name").getValue()))){
                                    String event_name = String.valueOf(ds.child("/name").getValue());
                                    long id = (long) ds.child("/id").getValue();
                                    myRef.child(String.valueOf(id)).removeValue();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                }
            });

            img_consult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent EventsManagerIntent = new Intent(c, EventLooker.class);
                    EventsManagerIntent.putExtra("eventname", aff.getText().toString().trim());
                    c.startActivity(EventsManagerIntent);
                }
            });

            img_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView txtView = itemView.findViewById(R.id.eventName);

                    myRef2 = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events");
                    myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                if(txtView.getText().toString().trim().equals(String.valueOf(ds.child("/name").getValue()))){

                                    String event_name = String.valueOf(ds.child("/name").getValue());
                                    long id = (long) ds.child("/id").getValue();
                                    long hour = (long) ds.child("/hour").getValue();
                                    long minute = (long) ds.child("/minute").getValue();
                                    long nb_people = (long) ds.child("/nb_people").getValue();
                                    String date = String.valueOf(ds.child("/date").getValue());
                                    boolean favorite = (boolean) ds.child("/favorite").getValue();

                                    Evenement event = new Evenement(event_name, (int) hour, (int) minute, (int) nb_people, date);
                                    event.setFavorite(favorite);
                                    event.swapFavorite();
                                    event.setName(event_name);
                                    event.setHour((int) hour);
                                    event.setMinute((int) minute);
                                    event.setNb_people((int) nb_people);
                                    event.setDate(date);
                                    event.setId((int) id);

                                    myRef2.child(String.valueOf(event.getId())).setValue(event);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                }
            });
        }
    }
}
