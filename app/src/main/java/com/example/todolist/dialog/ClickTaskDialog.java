package com.example.todolist.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.TaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

public class ClickTaskDialog extends DialogFragment{

    private Button editButton;
    private Button deleteButton;
    private TaskListener taskListener;
    private Task task;

    public ClickTaskDialog(TaskListener taskListener, Task task) {
        this.taskListener = taskListener;
        this.task = task;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.on_click_task_dialog, container, false);
        editButton = (Button)v.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTaskDialog editTaskDialog = new EditTaskDialog(taskListener, task);
                editTaskDialog.show(getFragmentManager(), "Edit task dialog");
                onDestroyView();
            }
        });
        deleteButton = (Button)v.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListener.deleteTask(task);
                onDestroyView();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
