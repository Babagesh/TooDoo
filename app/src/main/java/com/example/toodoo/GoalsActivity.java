package com.example.toodoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import java.util.concurrent.Executors;

public class GoalsActivity extends AppCompatActivity implements OnGoalClickListener {


    private GroupDatabase db;
    private GoalsAdapter goalsAdapter;
    private RecyclerView recyclerView;
    private long groupID;

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
        goalsAdapter = new GoalsAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(goalsAdapter);
        registerForContextMenu(recyclerView);
        db = Room.databaseBuilder(getApplicationContext(), GroupDatabase.class, "groupDatabase").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        Intent intent = getIntent();
        groupID = intent.getLongExtra("groupID", -1L);
        LiveData<List<Goal>> goals = db.goalDAO().getGoalsByGroup(groupID);
        goals.observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals)
            {
                goalsAdapter.updateGoals(goals);
            }
        });
        Button createGoal = findViewById(R.id.createGoal);
        createGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGoal();
            }
        });
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoalsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void createGoal()
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_goal, null);

        EditText nameInput = view.findViewById(R.id.goalNameInput);
        EditText descriptionInput = view.findViewById(R.id.goalDescriptionInput);
        EditText priorityInput = view.findViewById(R.id.goalPriorityInput);
        EditText taskListInput = view.findViewById(R.id.goalTaskListInput);
        Button createGoal = view.findViewById(R.id.createGoalButton);
        // Needs to be implemented Button aiGenerate = view.findViewById(R.id.aiGenerateButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        createGoal.setOnClickListener(v ->
        {
                String name = nameInput.getText().toString().trim();
                String description = descriptionInput.getText().toString().trim();
                String priority = priorityInput.getText().toString().trim();
                String taskList = taskListInput.getText().toString().trim();
                int priorityInt = Integer.parseInt(priority);
                Goal newGoal = new Goal(groupID, name, description, taskList, priorityInt);
                Executors.newSingleThreadExecutor().execute(() -> {
                db.goalDAO().insertGoal(newGoal);
            });
            dialog.dismiss();
        });
        dialog.show();
    }
    public void onGoalClick(Goal goal)
    {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.goal_display, null);

        TextView displayName = view.findViewById(R.id.displayName);
        TextView displayDescription = view.findViewById(R.id.displayDescription);
        TextView displayPriority = view.findViewById(R.id.displayPriority);
        TextView displayTaskList = view.findViewById(R.id.displayTaskList);

        displayName.setText(goal.goalName);
        displayDescription.setText(goal.description);
        // Convert the integer priority to a String for setText()
        displayPriority.setText(String.valueOf(goal.priority));
        displayTaskList.setText(goal.taskList);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view)
                // Add a simple "Close" button that does nothing but dismiss the dialog
                .setPositiveButton("Close", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
