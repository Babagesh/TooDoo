package com.example.toodoo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskGroup
{
    @PrimaryKey(autoGenerate=false)
    public String groupName;

    public List<Goal> goals = new ArrayList<Goal>();
    public TaskGroup(String groupName)
    {

        this.groupName = groupName;
    }
}
