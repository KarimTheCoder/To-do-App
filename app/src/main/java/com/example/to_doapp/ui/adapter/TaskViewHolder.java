package com.example.to_doapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.R;
import com.example.to_doapp.data.entity.TaskEntity;
import com.example.to_doapp.ui.adapter.callbacks.OnEditTextListener;
import com.example.to_doapp.ui.adapter.callbacks.OnImageButtonClickListener;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private final EditText taskNameTextView;
    private ImageButton deleteButton;
    private CheckBox taskCheckbox;
    private static OnImageButtonClickListener listener;
    private static OnEditTextListener editTextListener;

    public TaskViewHolder(@NonNull View itemView, OnImageButtonClickListener listener, OnEditTextListener editTextListener) {
        super(itemView);
        TaskViewHolder.editTextListener = editTextListener;
        TaskViewHolder.listener = listener;
        taskNameTextView = itemView.findViewById(R.id.edittext_task_description);
        taskCheckbox = itemView.findViewById(R.id.checkbox_completed);
        deleteButton = itemView.findViewById(R.id.button_delete);

        deleteButton.setOnClickListener(view -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Notify the listener (if using a callback interface)
                if (listener != null) {
                    listener.onRecyclerViewDelete(position);
                }
            }
        });

        taskNameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if(!hasFocus){

                    String text = taskNameTextView.getText().toString();

                    editTextListener.onFocusChangedListener(text, getAdapterPosition());




                }

            }
        });


    }

    public static TaskViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_task, parent, false);
        return new TaskViewHolder(view,listener,editTextListener);
    }

    public void bindTo(TaskEntity task) {
        taskNameTextView.setText(task.getTaskName());

    }

    public void clear() {
        taskNameTextView.setText("");
    }
}
