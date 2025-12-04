package com.example.toodoo;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder>
{
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView goalName;
        public TextView priority;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.goalName = itemView.findViewById(R.id.goalName);
            this.priority = itemView.findViewById(R.id.priority);
        }
    }
    public List<Goal> goals;
    public OnGoalClickListener onGoalClickListener;
    public GoalsAdapter(List<Goal> goalsList, OnGoalClickListener onGoalClickListener)
    {
        goals = goalsList;
        this.onGoalClickListener = onGoalClickListener;
    }
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View goalItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_layout, parent, false);
        return new ViewHolder(goalItem);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Goal cur = goals.get(position);
        holder.goalName.setText(cur.goalName);
        holder.priority.setText(String.valueOf(cur.priority));
        holder.itemView.setOnClickListener(v -> {
            onGoalClickListener.onGoalClick(cur);
        });
    }
    @Override
    public int getItemCount()
    {
        return goals != null ? goals.size() : 0;
    }


    public void updateGoals(List<Goal> newGoals)
    {
        this.goals = newGoals;
        notifyDataSetChanged();
    }

}

