package com.example.toodoo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TaskGroup
{
    @PrimaryKey(autoGenerate=false)
    public String groupName;
    public TaskGroup(String groupName)
    {
        this.groupName = groupName;
    }
}
