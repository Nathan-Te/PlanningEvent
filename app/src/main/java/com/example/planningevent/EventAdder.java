package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class EventAdder extends AppCompatActivity {

    TextView idEvent;
    TextInputLayout eventName;
    DatePickerDialog.OnDateSetListener setListenerDateFrom;
    NumberPicker nbParticipantsPicker;
    Button timeButton, dateButton;
    int hour, minute;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_adder);

        idEvent = findViewById(R.id.idEvent2);

        /*
            Time
         */
        timeButton = findViewById(R.id.timeButton);

        /*
            Number of participants
         */
        nbParticipantsPicker = findViewById(R.id.numberPicker);
        nbParticipantsPicker.setMinValue(0);
        nbParticipantsPicker.setMaxValue(100);
        // Note pour récupérer valeur : nbParticipantsPicker.getValue();

        /*
            Date
         */
        dateButton = findViewById(R.id.btnDate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EventAdder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        date = day + "/" + month + "/" + year;
                        dateButton.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        setListenerDateFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = day + "/" + month + "/" + year;
            }
        };

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("events");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        idEvent.setText(String.valueOf(Integer.parseInt(ds.child("/id").getValue().toString())+1));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        /*
            BUTTON TO ADD THE NEW EVENT
         */
        FloatingActionButton btnAddEvent2 = (FloatingActionButton) findViewById(R.id.btnAddEvent2);
        btnAddEvent2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pour tester l'ajout dans la database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("events");

                eventName = findViewById(R.id.textInputEventName);

                Evenement eventPerso = new Evenement(eventName.getEditText().getText().toString().trim(), hour, minute, nbParticipantsPicker.getValue(), date);
                /* NE MARCHE PAS SI BDD VIDE */
                eventPerso.setId(Integer.parseInt(idEvent.getText().toString()));
                myRef.child(String.valueOf(eventPerso.getId())).setValue(eventPerso);

                Intent EventsManagerIntent = new Intent(EventAdder.this, EventsManager.class);
                startActivity(EventsManagerIntent);
            }
        });
    }

    /**
     * popTimePicker
     * @param view
     */
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}