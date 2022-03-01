package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class EventAdder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Going back to My Events
        FloatingActionButton btnAddEvent2 = (FloatingActionButton) findViewById(R.id.btnAddEvent2);
        btnAddEvent2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextInputEditText saisie = (TextInputEditText) findViewById(R.id.inputevent);
                // CODE POUR INSERER DANS BDD NOUVEL EVENT
                Intent EventsManagerIntent = new Intent(EventAdder.this, EventsManager.class);
                startActivity(EventsManagerIntent);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_adder);
    }
}