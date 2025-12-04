package com.example.toodoo;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = TaskGroup.class,
        parentColumns = "groupId",
        childColumns = "groupID",
        onDelete = ForeignKey.CASCADE
))
public class Goal
{
    @PrimaryKey(autoGenerate = true)
    public long goalID;
    public long groupID;
    public String goalName;
    public String taskList;
    public int priority;

    public Goal(long groupID, String goalName, String taskList, int priority)
    {
        this.groupID = groupID;
        this.goalName = goalName;
        this.taskList = taskList;
        this.priority = priority;
    }
}
