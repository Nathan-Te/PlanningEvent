package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.example.planningevent.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Handler;

public class LoadingScreenActivity extends AppCompatActivity {

    private TextView isLog;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler progresshandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        isLog = findViewById(R.id.boolLog);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Getting extra infos from the Adapter
        Intent intent = getIntent();
        if (intent != null){

            if(intent.hasExtra("login") && intent.hasExtra("password")){
                Log.w("ERROR", "ERROR");
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("logs");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            if( intent.getStringExtra("login").equals(String.valueOf(ds.child("/login").getValue())) && intent.getStringExtra("password").equals(String.valueOf(ds.child("/password").getValue())) ){
                                isLog.setText("1");
                            }
                        }

                        if (isLog.getText().toString().trim().equals("1")){
                            Intent EventsManagerIntent = new Intent(LoadingScreenActivity.this, EventsManager.class);
                            startActivity(EventsManagerIntent);
                        }
                        else{
                            Intent LoginActivityIntent = new Intent(LoadingScreenActivity.this, LoginActivity.class);
                            LoginActivityIntent.putExtra("error", "Connection failed, invalid username or password");
                            startActivity(LoginActivityIntent);
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }

            if(intent.hasExtra("Registerlogin") && intent.hasExtra("Registerpassword")) {

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://planning-event-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("logs");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            isLog.setText(String.valueOf(Integer.parseInt(ds.child("/id").getValue().toString())+1));
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });

                new Thread(new Runnable() {
                    public void run() {
                        while (progressStatus < 100) {
                            progressStatus += 1;
                            // Update the progress bar and display the
                            //current value in the text view
                            progresshandler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                Thread.sleep(18);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        DatabaseReference myRef = database.getReference("logs");
                        Account account = new Account(Integer.parseInt(isLog.getText().toString().trim()), intent.getStringExtra("Registerlogin"), intent.getStringExtra("Registerpassword"));
                        myRef.child(isLog.getText().toString().trim()).setValue(account);
                        Intent EventsManagerIntent = new Intent(LoadingScreenActivity.this, EventsManager.class);
                        startActivity(EventsManagerIntent);
                    }
                }, 3000);   //1000 ms

            }

        }

    }

}