package com.example.toodoo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView groupName;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }

    public GroupsAdapter()
    {

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

    }
    @Override
    public int getItemCount()
    {
        return 0;
    }
}