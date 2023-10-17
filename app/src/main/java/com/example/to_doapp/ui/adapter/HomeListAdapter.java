package com.example.to_doapp.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_doapp.R;
import com.example.to_doapp.data.entity.ListEntity;
import com.example.to_doapp.data.entity.TaskEntity;
import com.example.to_doapp.ui.SharedViewModel;
import com.example.to_doapp.ui.adapter.callbacks.OnHomeRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends PagedListAdapter<ListEntity, ListViewHolder> {

    public interface OnItemLongClickListener{ void onItemLongClick(int position);}

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private FragmentActivity lifeCycleOwner;
    private List<TaskEntity> tasks;

    private OnHomeRecyclerViewClickListener listener;
    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    public HomeListAdapter(OnHomeRecyclerViewClickListener listener, SharedViewModel sharedViewModel,FragmentActivity lifeCycleOwner,RecyclerView recyclerView) {
        super(DIFF_CALLBACK);

        this.listener = listener;
        this.sharedViewModel = sharedViewModel;
        this.lifeCycleOwner = lifeCycleOwner;
        tasks = new ArrayList<>();

        this.recyclerView = recyclerView;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_list, parent, false);


        itemView.setOnLongClickListener(v -> {
            int position = recyclerView.getChildAdapterPosition(v); // Use the stored reference
            if (position != RecyclerView.NO_POSITION) {
                // Handle long-press event
                Log.i("Adapter","Long press");
            }
            return true;
        });

        // Create and return the UserViewHolder
        return new ListViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        InnerAdapter innerAdapter = new InnerAdapter();
        holder.recyclerView.setAdapter(innerAdapter);

        ListEntity list = getItem(position);
        int listId = list.getListId();

        observeInnerData(listId,innerAdapter);
        //innerData.removeObservers(lifeCycleOwner);


        if (list != null) {
            holder.bindTo(list);
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear();
        }
    }

    private void observeInnerData(int listId, InnerAdapter adapter) {
        LiveData<PagedList<TaskEntity>> innerData = sharedViewModel.getItemsByListId(listId);

        innerData.observe(lifeCycleOwner,innerTasks->{

            this.tasks.addAll(innerTasks);
            adapter.submitList(innerTasks);

        });
    }



    private static final DiffUtil.ItemCallback<ListEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ListEntity>() {
                @Override
                public boolean areItemsTheSame(ListEntity oldUser, ListEntity newUser) {
                    return oldUser.getListId() == newUser.getListId();
                }

                @Override
                public boolean areContentsTheSame(ListEntity oldUser, ListEntity newUser) {
                    return oldUser.getListName().equals(newUser.getListName());
                }
            };



}
 class ListViewHolder extends RecyclerView.ViewHolder{


    private final TextView listNameTextview;
    private ImageButton buttonDelete;
    private ConstraintLayout constraintLayout;
    public RecyclerView recyclerView;
    public ListViewHolder(@NonNull View itemView, OnHomeRecyclerViewClickListener listener) {
        super(itemView);

        listNameTextview = itemView.findViewById(R.id.list_name_textview);
        buttonDelete = itemView.findViewById(R.id.imageButton_delete_list);
        constraintLayout = itemView.findViewById(R.id.constraint_layout_home_recyclerview);
        recyclerView = itemView.findViewById(R.id.recyclerview_home_inner);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Notify the listener (if using a callback interface)
                    if (listener != null) {
                        listener.onHomeRecyclerViewItemClick(position);
                    }
                }
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Notify the listener (if using a callback interface)
                    if (listener != null) {
                        listener.onHomeRecyclerViewItemClick(position);
                    }
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Notify the listener (if using a callback interface)
                    if (listener != null) {
                        listener.onHomeRecyclerViewDelete(position);
                    }
                }
            }
        });


    }

    public void bindTo(ListEntity list){

        listNameTextview.setText(list.getListName());
    }
    public void clear(){
        listNameTextview.setText("");
    }
}



class InnerAdapter extends PagedListAdapter<TaskEntity, InnerTaskViewHolder>{

    public InnerAdapter() {
        super(DIFF_CALLBACK);


    }

    @NonNull
    @Override
    public InnerTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_inner_task, parent, false);

        // Create and return the UserViewHolder
        return new InnerTaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerTaskViewHolder holder, int position) {



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

class InnerTaskViewHolder extends RecyclerView.ViewHolder{


    private final TextView innerTextView;

    public InnerTaskViewHolder(@NonNull View itemView) {
        super(itemView);

        innerTextView = itemView.findViewById(R.id.inner_textview);

    }

    public void bindTo(TaskEntity task){

        innerTextView.setText(task.getTaskName());
    }
    public void clear(){
        innerTextView.setText("");
    }
}