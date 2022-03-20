package com.example.planningevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventsFavorite extends Activity {

    RecyclerView rv;
    List<Evenement> myEvents = new ArrayList<>();
    MyAdapter adapter;
    Evenement data_ev;
    EventsFavorite objet = this;

    public Evenement getData(){
        return this.data_ev;
    }

    public void setData(Evenement s){
        this.data_ev = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("events");

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

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

        // Data
        /*
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,myEvents);
        rv.setAdapter(adapter);
        */

       // rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
       // new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);

        // Back home
        FloatingActionButton btnHome = (FloatingActionButton) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent MainActivityIntent = new Intent(EventsFavorite.this, MainActivity.class);
                startActivity(MainActivityIntent);
            }
        });

        // Event Adder
        FloatingActionButton btnAddEvent = (FloatingActionButton) findViewById(R.id.btnAddEvent);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent EventAdderIntent = new Intent(EventsFavorite.this, EventAdder.class);
                startActivity(EventAdderIntent);
            }
        });

        // Event Adder
        FloatingActionButton btnDelete = (FloatingActionButton) findViewById(R.id.btnFavorite);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * This button has no interest.
                 */
            }
        });

    }
}