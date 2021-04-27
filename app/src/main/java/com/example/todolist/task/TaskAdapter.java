package com.example.todolist.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

import java.util.ArrayList;
import java.util.Collection;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> taskList = new ArrayList<>();

    class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView taskTextView;
        private CheckBox completedCheckBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.task_text);
            completedCheckBox = itemView.findViewById(R.id.task_completed);
        }

        public void bind(Task task){
            taskTextView.setText(task.getTask());
            completedCheckBox.setChecked(task.isCompleted());
            completedCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setCompleted(((CheckBox) v).isChecked());
                }
            });
        }

    }

    public interface OnTaskListener{
        void onTaskClick(int position);
    }

    public void addItem(Task task){
        taskList.add(task);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<Task> tasks) {
        taskList = tasks;
        notifyDataSetChanged();
    }

    public void clearItems() {
        taskList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
