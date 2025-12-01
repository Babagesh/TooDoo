package com.example.toodoo;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        public TextView groupName;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
        {

        }

    }
    public List<TaskGroup> groups;
    public int clickedPosition;
    public GroupsAdapter(List<TaskGroup> groupList)
    {
        groups = groupList;
    }
    @NonNull
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
            this.clickedPosition = holder.getAdapterPosition();
            return false;
        });
    }
    @Override
    public int getItemCount()
    {
        return groups != null ? groups.size() : 0;
    }
}