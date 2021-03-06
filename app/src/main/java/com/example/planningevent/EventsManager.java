package com.example.planningevent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventsManager extends Activity {

    RecyclerView rv;
    List<Evenement> myEvents = new ArrayList<>();
    MyAdapter adapter;
    EventsManager objet = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_manager);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("events");

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myEvents.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String event_name = String.valueOf(ds.child("/name").getValue());
                    String date = String.valueOf(ds.child("/date").getValue());
                    long hour = (long) ds.child("/hour").getValue();
                    long minute = (long) ds.child("/minute").getValue();
                    long nb_people = (long) ds.child("/nb_people").getValue();
                    long id = (long) ds.child("/id").getValue();

                    Evenement event = new  Evenement(event_name,(int) hour, (int) minute, (int) nb_people, date);
                    event.setId((int) id);

                    myEvents.add(event);
                }
                adapter = new MyAdapter(objet,myEvents);
                rv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        // Back home
        FloatingActionButton btnHome = (FloatingActionButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent MainActivityIntent = new Intent(EventsManager.this, MainActivity.class);
                startActivity(MainActivityIntent);
            }
        });

        // Event Adder
        FloatingActionButton btnAddEvent = (FloatingActionButton) findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent EventAdderIntent = new Intent(EventsManager.this, EventAdder.class);
                startActivity(EventAdderIntent);
            }
        });

        // Circle View All Events
        ImageView viewAllEvents = findViewById(R.id.viewAllEvents);
        viewAllEvents.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Actualisation du titre
                TextView monTitre = findViewById(R.id.monTitre);
                monTitre.setText("My events");

                // Changement couleur indicateur
                ImageView viewFavorites = findViewById(R.id.viewFavorites);
                ImageView viewAllEvents = findViewById(R.id.viewAllEvents);
                viewFavorites.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.circle_gray));
                viewAllEvents.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.blue_circle));

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("events");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        myEvents.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {

                            String event_name = String.valueOf(ds.child("/name").getValue());
                            String date = String.valueOf(ds.child("/date").getValue());
                            long hour = (long) ds.child("/hour").getValue();
                            long minute = (long) ds.child("/minute").getValue();
                            long nb_people = (long) ds.child("/nb_people").getValue();
                            long id = (long) ds.child("/id").getValue();

                            Evenement event = new  Evenement(event_name,(int) hour, (int) minute, (int) nb_people, date);
                            event.setId((int) id);

                            myEvents.add(event);
                        }
                        adapter = new MyAdapter(objet,myEvents);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }
        });

        ImageView viewFavorites = findViewById(R.id.viewFavorites);
        viewFavorites.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Actualisation du titre
                TextView monTitre = findViewById(R.id.monTitre);
                monTitre.setText("My favorites");

                // Changement couleur indicateur
                ImageView viewFavorites = findViewById(R.id.viewFavorites);
                ImageView viewAllEvents = findViewById(R.id.viewAllEvents);
                viewFavorites.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.blue_circle));
                viewAllEvents.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.circle_gray));

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("events");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        myEvents.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            if((boolean) ds.child("/favorite").getValue() == true){
                                String event_name = String.valueOf(ds.child("/name").getValue());
                                String date = String.valueOf(ds.child("/date").getValue());
                                long hour = (long) ds.child("/hour").getValue();
                                long minute = (long) ds.child("/minute").getValue();
                                long nb_people = (long) ds.child("/nb_people").getValue();
                                long id = (long) ds.child("/id").getValue();

                                Evenement event = new  Evenement(event_name,(int) hour, (int) minute, (int) nb_people, date);
                                event.setId((int) id);

                                myEvents.add(event);
                            }
                        }
                        adapter = new MyAdapter(objet,myEvents);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }
        });

        rv.setOnTouchListener(new OnSwipeTouchListener(EventsManager.this) {
            public void onSwipeLeft() {
                // Actualisation du titre
                TextView monTitre = findViewById(R.id.monTitre);
                monTitre.setText("My favorites");

                // Changement couleur indicateur
                ImageView viewFavorites = findViewById(R.id.viewFavorites);
                ImageView viewAllEvents = findViewById(R.id.viewAllEvents);
                viewFavorites.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.blue_circle));
                viewAllEvents.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.circle_gray));

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("events");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        myEvents.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            if((boolean) ds.child("/favorite").getValue() == true){
                                String event_name = String.valueOf(ds.child("/name").getValue());
                                String date = String.valueOf(ds.child("/date").getValue());
                                long hour = (long) ds.child("/hour").getValue();
                                long minute = (long) ds.child("/minute").getValue();
                                long nb_people = (long) ds.child("/nb_people").getValue();
                                long id = (long) ds.child("/id").getValue();

                                Evenement event = new  Evenement(event_name,(int) hour, (int) minute, (int) nb_people, date);
                                event.setId((int) id);

                                myEvents.add(event);
                            }
                        }
                        adapter = new MyAdapter(objet,myEvents);
                        rv.setAdapter(adapter);
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }

            public void onSwipeRight() {
                // Actualisation du titre
                TextView monTitre = findViewById(R.id.monTitre);
                monTitre.setText("My events");

                // Changement couleur indicateur
                ImageView viewFavorites = findViewById(R.id.viewFavorites);
                ImageView viewAllEvents = findViewById(R.id.viewAllEvents);
                viewFavorites.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.circle_gray));
                viewAllEvents.setImageDrawable(viewFavorites.getResources().getDrawable(R.drawable.blue_circle));

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("events");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        myEvents.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {

                            String event_name = String.valueOf(ds.child("/name").getValue());
                            String date = String.valueOf(ds.child("/date").getValue());
                            long hour = (long) ds.child("/hour").getValue();
                            long minute = (long) ds.child("/minute").getValue();
                            long nb_people = (long) ds.child("/nb_people").getValue();
                            long id = (long) ds.child("/id").getValue();

                            Evenement event = new  Evenement(event_name,(int) hour, (int) minute, (int) nb_people, date);
                            event.setId((int) id);

                            myEvents.add(event);
                        }
                        adapter = new MyAdapter(objet,myEvents);
                        rv.setAdapter(adapter);
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