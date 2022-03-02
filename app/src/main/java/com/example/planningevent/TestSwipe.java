package com.example.planningevent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

public class TestSwipe extends AppCompatActivity {

    private SwipeLayout sl;
    List<Evenement> myEvents = new ArrayList<>();

    DatabaseHandler db = new DatabaseHandler(this);
    MyAdapter adapter = new MyAdapter(this,myEvents);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myEvents = db.getAllEvents();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_swipe);

        sl = findViewById(R.id.swipeLayout);
        sl.setShowMode(SwipeLayout.ShowMode.PullOut);
        sl.addDrag(SwipeLayout.DragEdge.Right,sl.findViewById(R.id.linear_sag));

        sl.findViewById(R.id.ara).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_LONG).show();
            }
        });


        sl.findViewById(R.id.delete2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Delete",Toast.LENGTH_LONG).show();
                Intent MainActivityIntent = new Intent(TestSwipe.this, MainActivity.class);
                startActivity(MainActivityIntent);

                /*
                Evenement event_to_delete = adapter.mdata.get();
                db.deleteEvent(event_to_delete);
                myEvents.remove(event_to_delete);
                adapter.notifyDataSetChanged();
                */
            }
        });

        sl.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Star",Toast.LENGTH_LONG).show();

            }
        });
    }
}