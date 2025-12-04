package com.example.toodoo;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {TaskGroup.class, Goal.class}, version = 1)
public abstract class GroupDatabase extends RoomDatabase
{
    public abstract TaskGroupDAO groupDAO();
    public abstract GoalDAO goalDAO();
}
