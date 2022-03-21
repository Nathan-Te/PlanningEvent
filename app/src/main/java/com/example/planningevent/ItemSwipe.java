package com.example.planningevent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.daimajia.swipe.SwipeLayout;

public class ItemSwipe extends AppCompatActivity {

    private SwipeLayout sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        sl = findViewById(R.id.swipeLayout);
        sl.setShowMode(SwipeLayout.ShowMode.PullOut);
        sl.addDrag(SwipeLayout.DragEdge.Right,sl.findViewById(R.id.linear_sag));
    }
}