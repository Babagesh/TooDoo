package com.example.toodoo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TaskGroup
{
    @PrimaryKey(autoGenerate=true)
    public long groupId;
    public String groupName;
    public TaskGroup(String groupName)
    {
        this.groupName = groupName;
    }
}
