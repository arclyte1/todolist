package com.example.todolist.task_adapter;

import android.app.PendingIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.TaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList = new ArrayList<>();
    private TaskListener taskListener;
    private TextView emptyTasks;

    public TaskAdapter(TaskListener taskListener, TextView emptyTasks) {
        this.taskListener = taskListener;
        this.emptyTasks = emptyTasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new TaskViewHolder(view, taskListener);
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
        private TextView taskDate;
        private TaskListener taskListener;

        public TaskViewHolder(@NonNull View itemView, TaskListener taskListener) {
            super(itemView);
            taskTextView = itemView.findViewById(R.id.task_text);
            completedCheckBox = itemView.findViewById(R.id.task_completed);
            taskDate = itemView.findViewById(R.id.task_date);
            this.taskListener = taskListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Task task){
            taskTextView.setText(task.getTask());
            completedCheckBox.setChecked(task.isCompleted());
            completedCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.setCompleted(((CheckBox) v).isChecked());
                    taskListener.completeClick(getAdapterPosition());
                }
            });

            long time = Calendar.getInstance().getTimeInMillis();
            if (task.getDate() < time || task.isCompleted())
                taskDate.setText("");
            else {
                if (task.getDate() - time > 24 * 60 * 60 * 1000){
                    int days = Math.round((float)((task.getDate() - time) / (60 * 60 * 1000)) / 24);
                    taskDate.setText(days + " д.");
                }
                else {
                    int hours = Math.round((float)((task.getDate() - time) % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));
                    if (hours == 0)
                        taskDate.setText("<1 ч.");
                    else
                        taskDate.setText(hours + " ч.");
                }
            }
        }

        @Override
        public void onClick(View v) {
            taskListener.taskClick(getAdapterPosition());
        }
    }

    public void setItems(List<Task> tasks) {
        taskList = tasks;
        Collections.sort(taskList);
        if (tasks.isEmpty())
            emptyTasks.setVisibility(View.VISIBLE);
        else
            emptyTasks.setVisibility(View.INVISIBLE);
        notifyDataSetChanged();
    }

}
