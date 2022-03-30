package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

import com.example.planningevent.ui.login.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button register = findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent LoginActivityIntent = new Intent(MainActivity.this, Register.class);
                startActivity(LoginActivityIntent);
            }
        });

        final Button login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent EventsManagerIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(EventsManagerIntent);
            }
        });
    }
}