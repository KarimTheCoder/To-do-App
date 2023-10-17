package com.example.to_doapp.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.data.entity.TaskEntity;
import com.example.to_doapp.databinding.FragmentListBinding;
import com.example.to_doapp.ui.SharedViewModel;
import com.example.to_doapp.ui.adapter.TaskAdapter;
import com.example.to_doapp.ui.adapter.callbacks.OnEditTextListener;
import com.example.to_doapp.ui.adapter.callbacks.OnImageButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements OnImageButtonClickListener, OnEditTextListener, View.OnClickListener {
    private TextView textView;
    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<TaskEntity> tasks;
    private FragmentListBinding binding;
    private Button addButton;
    int listSelectedId;
    private TextInputEditText inputEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addButton = binding.buttonAdd;
        inputEditText = binding.edittextInput;
        addButton.setOnClickListener(this);

        tasks = new ArrayList<>();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        recyclerView = binding.recyclerviewTask;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        adapter = new TaskAdapter(this, this);
        recyclerView.setAdapter(adapter);


        listSelectedId = sharedViewModel.getSelectedListId();

        sharedViewModel.getItemsByListId(listSelectedId).observe(requireActivity(), data->{

            tasks.clear();
            this.tasks.addAll(data);
            adapter.submitList(data);


        });



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onFocusChangedListener(String text, int position) {
        TaskEntity task = tasks.get(position);
        sharedViewModel.updateTaskName(task,text);


    }

    @Override
    public void onRecyclerViewDelete(int position) {
        TaskEntity task = tasks.get(position);
        sharedViewModel.deleteTask(task);

    }

    @Override
    public void onClick(View view) {

        TaskEntity task = new TaskEntity();
        String taskName = inputEditText.getText().toString();
        inputEditText.setText("");
        task.setTaskName(taskName);
        task.setListId(listSelectedId);
        sharedViewModel.addTask(task);


    }
}