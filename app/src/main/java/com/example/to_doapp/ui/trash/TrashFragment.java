package com.example.to_doapp.ui.trash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.to_doapp.R;
import com.example.to_doapp.data.entity.ListEntity;
import com.example.to_doapp.databinding.FragmentTrashBinding;
import com.example.to_doapp.ui.SharedViewModel;
import com.example.to_doapp.ui.adapter.HomeListAdapter;
import com.example.to_doapp.ui.adapter.callbacks.OnHomeRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

public class TrashFragment extends Fragment implements OnHomeRecyclerViewClickListener {

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<ListEntity> lists;
    public static final String LIST_CATEGORY = "TRASH";

    FragmentTrashBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTrashBinding.inflate(inflater,container,false);

        View root = binding.getRoot();


        lists = new ArrayList<>();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        recyclerView = binding.recyclerviewTrash;
        adapter = new HomeListAdapter(this,sharedViewModel,requireActivity(),recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        sharedViewModel.getListByCategory(LIST_CATEGORY).observe(requireActivity(),lists->{
            adapter.submitList(lists);
            this.lists.clear();
            this.lists.addAll(lists);

        });






        return root;
    }

    @Override
    public void onHomeRecyclerViewDelete(int position) {

        if (position >= 0 && position < lists.size()) {
            ListEntity list = lists.get(position);

            if (list != null) {

                sharedViewModel.deleteList(list.getListId());
                Log.i("onHomeRecyclerViewItemClick", "pos: " + position + " list name: " + list.getListName());
            } else {
                Log.e("onHomeRecyclerViewItemClick", "list at position " + position + " is null");
            }
        } else {
            Log.e("onHomeRecyclerViewItemClick", "Invalid position: " + position);
        }

    }

    @Override
    public void onHomeRecyclerViewItemClick(int position) {

        int listName = lists.get(position).getListId();
        sharedViewModel.setSelectedListId(listName);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_trash_to_nav_list);

        Toast.makeText(requireActivity(),"item clicked",Toast.LENGTH_SHORT).show();
    }
}