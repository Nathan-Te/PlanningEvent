package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent EventsManagerIntent = new Intent(MainActivity.this, EventsManager.class);
                startActivity(EventsManagerIntent);
            }
        });
    }


}