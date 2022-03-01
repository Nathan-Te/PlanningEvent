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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EventsManager extends Activity {
    // String items[] = new String[]{"Wedding Florian and Mayara","Party","Birthday Nathan"};
    //String myDataset[] = new String[]{"Wedding Florian and Mayara","Party","Birthday Nathan"};

    RecyclerView rv;
    List<String> name = new ArrayList<>();
    MyAdapter adapter = new MyAdapter(this,name);

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
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");
        name.add("Birthday");
        name.add("Party");
        name.add("Wedding");

        rv.setLayoutManager(new LinearLayoutManager(this));
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
    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            name.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };
}