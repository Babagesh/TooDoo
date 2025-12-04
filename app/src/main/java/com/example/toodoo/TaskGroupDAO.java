package com.example.toodoo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskGroupDAO {
    @Insert
    void insertGroup(TaskGroup group);

    @Update
    void updateGroup(TaskGroup g);

    @Delete
    void deleteGroup(TaskGroup g);

    @Query("Select * from TaskGroup where groupName = :groupName")
    TaskGroup getGroup(String groupName);

    @Query("Select * from TaskGroup")
    LiveData<List<TaskGroup>> getGroups();
}
