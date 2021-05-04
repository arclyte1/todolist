package com.example.todolist.task_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.OnTaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();
    private OnTaskListener onTaskListener;

    public TaskAdapter(OnTaskListener onTaskListener) {
        this.onTaskListener = onTaskListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new TaskViewHolder(view, onTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView taskTextView;
        private CheckBox completedCheckBox;
        private OnTaskListener onTaskListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.task_text);
            completedCheckBox = itemView.findViewById(R.id.task_completed);
            this.onTaskListener = onTaskListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Task task){
            taskTextView.setText(task.getTask());
            completedCheckBox.setChecked(task.isCompleted());
            completedCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setCompleted(((CheckBox) v).isChecked());
                    onTaskListener.onCompleteClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }

    public void addItem(Task task){
        taskList.add(task);
        notifyDataSetChanged();
    }

    public void addItems(List<Task> tasks){
        taskList.addAll(tasks);
        notifyDataSetChanged();
    }

    public void setItems(List<Task> tasks) {
        taskList = tasks;
        Collections.sort(taskList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        taskList.clear();
        notifyDataSetChanged();
    }
}