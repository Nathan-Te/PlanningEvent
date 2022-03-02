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
    // String items[] = new String[]{"Wedding Florian and Mayara","Party","Birthday Nathan"};
    //String myDataset[] = new String[]{"Wedding Florian and Mayara","Party","Birthday Nathan"};

    RecyclerView rv;
    List<Evenement> name = new ArrayList<>();
    MyAdapter adapter;

    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_manager);

        /*
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        TextView monTitre = (TextView) findViewById(R.id.monTitre);
        */

        rv = findViewById(R.id.rv);
        // Data
        db.addEvent(new Evenement("Birthday 1"));
        db.addEvent(new Evenement("Birthday 2"));
        db.addEvent(new Evenement("Birthday 3"));
        db.addEvent(new Evenement("Birthday 4"));
        name = db.getAllEvents();
        /*
        name.add(new Evenement("Birthday"));
        name.add(new Evenement("Wedding"));
        name.add(new Evenement("Party"));
        name.add(new Evenement("Help"));
        name.add(new Evenement("Test"));
        */
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,name);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);

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

    }

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
            name.remove(event_to_delete);
            adapter.notifyDataSetChanged();
        }

    };
}