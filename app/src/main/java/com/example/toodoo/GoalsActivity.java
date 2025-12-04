package com.example.toodoo;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity {


    private GroupDatabase db;
    private GoalsAdapter goalsAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_goals);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.goalsView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        goalsAdapter = new GoalsAdapter(new ArrayList<>());
        recyclerView.setAdapter(goalsAdapter);
        registerForContextMenu(recyclerView);
        db = Room.databaseBuilder(getApplicationContext(), GroupDatabase.class, "groupDatabase").build();
        LiveData<List<Goal>> goals = db.goalDAO().getGoals();
        goals.observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals)
            {
                goalsAdapter.updateGoals(goals);
            }
        });
        Button createGoal = findViewById(R.id.createGoal);
    }
}
