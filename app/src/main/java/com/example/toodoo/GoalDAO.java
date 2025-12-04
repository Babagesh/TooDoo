package com.example.toodoo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GoalDAO
{
    @Insert
    void insertGoal(Goal goal);

    @Update
    void updateGoal(Goal goal);

    @Delete
    void deleteGoal(Goal goal);

    @Query("Select * from Goal where goalName = :goalName")
    Goal getGoal(String goalName);

    @Query("Select * from Goal")
    LiveData<List<Goal>> getGoals();


}