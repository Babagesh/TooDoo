package com.example.toodoo;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

public class MainActivity extends AppCompatActivity
{
    private GroupDatabase db;
    private  GroupsAdapter groupsAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        groupsAdapter = new GroupsAdapter(new ArrayList<>());
        recyclerView.setAdapter(groupsAdapter);
        registerForContextMenu(recyclerView);

        db = Room.databaseBuilder(getApplicationContext(), GroupDatabase.class, "groupDatabase").build();
        LiveData<List<TaskGroup>> groups = db.groupDAO().getGroups();
        groups.observe(this, new Observer<List<TaskGroup>>() {
            @Override
            public void onChanged(List<TaskGroup> taskGroups) {
                groupsAdapter.updateGroups(taskGroups);
            }
        });
        Button createGroup = findViewById(R.id.createGroup);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGroupName();
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
        TaskGroup selectedGroup = groupsAdapter.getLongClickedGroup();
        int itemId = item.getItemId();
        if(itemId == R.id.groupEdit)
        {
            editGroupName(selectedGroup);
            return true;
        }
        else if(itemId == R.id.groupDelete)
        {
            deleteGroupName(selectedGroup);
            return true;
        }
        return super.onContextItemSelected(item);
    }
    public void setGroupName()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Group");
        EditText input = new EditText(this);
        input.setHint("Enter group name");
        builder.setView(input);

        builder.setPositiveButton("Create", (dialog, which)-> {
            String groupName = input.getText().toString().trim();
            if(!groupName.isEmpty())
            {
                Executors.newSingleThreadExecutor().execute(()->{
                    if(db.groupDAO().getGroup(groupName) == null)
                    {
                        db.groupDAO().insertGroup(new TaskGroup(groupName));
                    }
                    else
                    {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Group already exists", Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which)-> dialog.cancel());
        builder.show();

    }

    public void editGroupName(TaskGroup group)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Group Name");
        EditText input = new EditText(this);
        input.setHint("Enter new group name");
        builder.setView(input);

        builder.setPositiveButton("Edit", (dialog, which)-> {
            String groupName = input.getText().toString().trim();
            if(!groupName.isEmpty())
            {
                group.groupName = groupName;
                Executors.newSingleThreadExecutor().execute(()->{
                    db.groupDAO().updateGroup(group);
                });
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which)-> dialog.cancel());
        builder.show();
    }
    public void deleteGroupName(TaskGroup group)
    {
        Executors.newSingleThreadExecutor().execute(()->{
            db.groupDAO().deleteGroup(group);
        });
    }

}

