package com.example.planningevent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.example.planningevent.Evenement;

public class EventsManager extends Activity {

    RecyclerView rv;
    List<Evenement> myEvents = new ArrayList<>();
    MyAdapter adapter;


    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_manager);

        rv = findViewById(R.id.rv);
        // Data
        myEvents = db.getAllEvents();

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,myEvents);
        rv.setAdapter(adapter);
       //  rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

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
                Intent TestSwipeIntent = new Intent(EventsManager.this, TestSwipe.class);
                startActivity(TestSwipeIntent);
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