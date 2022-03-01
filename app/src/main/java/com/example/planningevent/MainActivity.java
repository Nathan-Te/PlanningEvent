package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.planningevent.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button register = findViewById(R.id.button2);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent LoginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(LoginActivityIntent);
            }
        });

        final Button login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent EventsManagerIntent = new Intent(MainActivity.this, EventsManager.class);
                startActivity(EventsManagerIntent);
            }
        });
    }


}