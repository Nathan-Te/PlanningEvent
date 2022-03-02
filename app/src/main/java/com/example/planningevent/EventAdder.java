package com.example.planningevent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EventAdder extends AppCompatActivity {

    // Initialize variables
    EditText dateFrom;
    EditText dateTo;
    EditText editText;
    TextView textView1, textView2;

    DatePickerDialog.OnDateSetListener setListenerDateFrom, setListenerDateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_adder);

        /*
            CALENDARS
         */
        dateFrom = findViewById(R.id.inputDateFrom);
        dateTo = findViewById(R.id.inputDateTo);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EventAdder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        dateFrom.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        setListenerDateFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day + "/" + month + "/" + year;
            }
        };

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EventAdder.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        dateTo.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        setListenerDateTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day + "/" + month + "/" + year;
            }
        };

        /*
            BUTTON TO ADD THE NEW EVENT
         */
        FloatingActionButton btnAddEvent2 = (FloatingActionButton) findViewById(R.id.btnAddEvent2);
        btnAddEvent2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextInputEditText inputEvent = (TextInputEditText) findViewById(R.id.inputEventName);
                // CODE POUR INSERER DANS BDD NOUVEL EVENT
                Intent EventsManagerIntent = new Intent(EventAdder.this, EventsManager.class);
                startActivity(EventsManagerIntent);
            }
        });

        /*
            ENTER THE ADDRESS
         */

        // Assign variable
        editText = findViewById(R.id.edit_text);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);

        // Initialize places
        Places.initialize(getApplicationContext(),"AIzaSyBO8KyC65t07N5oqXsSWOYmMzRj5iHEBOA");

        // Set EditText non focusable
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                // Create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(EventAdder.this);
                //Start activity result
                startActivityForResult(intent,100); // deprecated ?
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK) {
            // When success
            // Initialize places
            Place place = Autocomplete.getPlaceFromIntent(data);
            // Set address on EditText
            editText.setText(place.getAddress());
            // Set locality name
            textView1.setText(String.format("Locality Name : %s", place.getName()));
            // Set latitude & longitude
            textView2.setText(String.valueOf(place.getLatLng()));
        } else if(resultCode == AutocompleteActivity.RESULT_ERROR) {
            // When error
            // Initialize status
            Status status = Autocomplete.getStatusFromIntent(data);
            // Display toast
            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}