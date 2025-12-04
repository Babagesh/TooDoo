package com.example.toodoo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView groupName;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            groupName = itemView.findViewById(R.id.goalName);
        }

    }
    public List<TaskGroup> groups;
    public int clickedPosition;
    public OnGroupClickListener onGroupClickListener;
    public GroupsAdapter(List<TaskGroup> groupList, OnGroupClickListener onGroupClickListener)
    {
        groups = groupList;
        this.onGroupClickListener = onGroupClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View groupItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_layout, parent, false);
        return new ViewHolder(groupItem);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        TaskGroup cur = groups.get(position);
        holder.groupName.setText(cur.groupName);
        holder.itemView.setOnLongClickListener(v -> {
            this.clickedPosition = holder.getBindingAdapterPosition();
            return false;
        });
        holder.itemView.setOnClickListener(v -> {
            onGroupClickListener.onGroupClick(cur);
        });
    }

    @Override
    public int getItemCount()
    {
        return groups != null ? groups.size() : 0;
    }

    public TaskGroup getLongClickedGroup()
    {
       return groups.get(clickedPosition);
    }

    public void updateGroups(List<TaskGroup> newGroups)
    {
        this.groups = newGroups;
        notifyDataSetChanged();
    }
}

