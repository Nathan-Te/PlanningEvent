package com.example.planningevent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
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
    Evenement data_ev;
    EventsManager objet = this;

    public Evenement getData(){
        return this.data_ev;
    }

    public void setData(Evenement s){
        this.data_ev = s;
    }

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
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                myEvents.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String event_name = String.valueOf(ds.child("/name").getValue());
                    Log.w("TEST INFO : ", event_name);
                    setData(new Evenement(event_name));
                    myEvents.add(getData());
                    Log.w("LISTE TEMP :", myEvents.toString());
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
        Log.w("LIST : ", myEvents.toString());
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

        // Event Adder
        FloatingActionButton btnDelete = (FloatingActionButton) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /**
                 * This button has no interest.
                 */
            }
        });

    }

    /*
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Evenement event_to_delete = adapter.mdata.get(viewHolder.getAdapterPosition());
            Log.e("To Delete : ", event_to_delete.toString());
            db.deleteEvent(event_to_delete);
            myEvents.remove(event_to_delete);
            adapter.notifyDataSetChanged();
        }

    };*/
}