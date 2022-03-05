package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class EventAdder extends AppCompatActivity {

    TextInputEditText eventName;
    DatePickerDialog.OnDateSetListener setListenerDateFrom;
    NumberPicker nbParticipantsPicker;
    Button timeButton, dateButton;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_adder);

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
                        String date = day + "/" + month + "/" + year;
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
                String date = day + "/" + month + "/" + year;
            }
        };

        /*
            BUTTON TO ADD THE NEW EVENT
         */
        FloatingActionButton btnAddEvent2 = (FloatingActionButton) findViewById(R.id.btnAddEvent2);
        btnAddEvent2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pour tester l'ajout dans la database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("users");
                Evenement eventPerso = new Evenement("Nathan");
                myRef.child(String.valueOf(eventPerso.getId())).setValue(eventPerso);

                eventName = findViewById(R.id.inputEventName);

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