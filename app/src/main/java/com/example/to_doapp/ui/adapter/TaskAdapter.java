package com.example.to_doapp.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import com.example.to_doapp.R;
import com.example.to_doapp.data.entity.TaskEntity;
import com.example.to_doapp.ui.adapter.callbacks.OnEditTextListener;
import com.example.to_doapp.ui.adapter.callbacks.OnImageButtonClickListener;


public class TaskAdapter extends PagedListAdapter<TaskEntity,TaskViewHolder> {


    private OnImageButtonClickListener listener;
    private OnEditTextListener editTextListener;
    public TaskAdapter(OnImageButtonClickListener listener, OnEditTextListener editTextListener) {
        super(DIFF_CALLBACK);

        this.editTextListener = editTextListener;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_task, parent, false);

        // Create and return the UserViewHolder
        return new TaskViewHolder(itemView,listener, editTextListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {



        TaskEntity user = getItem(position);
        if (user != null) {
            holder.bindTo(user);
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear();
        }
    }

    private static final DiffUtil.ItemCallback<TaskEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TaskEntity>() {
                @Override
                public boolean areItemsTheSame(TaskEntity oldUser, TaskEntity newUser) {
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(TaskEntity oldUser, TaskEntity newUser) {
                    return oldUser.getTaskName().equals(newUser.getTaskName());
                }
            };
}
