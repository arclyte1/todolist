package com.example.todolist.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.todolist.OnTaskListener;
import com.example.todolist.R;
import com.example.todolist.task.Task;

public class OnAddTaskDialog extends DialogFragment{

    private Button addButton;
    private TextView taskName;
    private OnTaskListener onTaskListener;

    public OnAddTaskDialog(OnTaskListener onTaskListener) {
        this.onTaskListener = onTaskListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_task_dialog, container, false);
        TextView taskName = v.findViewById(R.id.task_name);
        addButton = (Button)v.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = taskName.getText().toString();
                if (!text.isEmpty()) {
                    Task task = new Task(text);
                    onTaskListener.onAddTask(task);
                    onDestroyView();
                } else
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
