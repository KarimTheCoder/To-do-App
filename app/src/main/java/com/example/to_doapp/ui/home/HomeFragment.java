package com.example.to_doapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.R;
import com.example.to_doapp.data.entity.ListEntity;
import com.example.to_doapp.databinding.FragmentHomeBinding;
import com.example.to_doapp.ui.SharedViewModel;
import com.example.to_doapp.ui.adapter.HomeListAdapter;
import com.example.to_doapp.ui.adapter.callbacks.OnHomeRecyclerViewClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements OnHomeRecyclerViewClickListener, View.OnClickListener {

    public static final String LIST_CATEGORY = "HOME";
    public static final String TRASH_CATEGORY = "TRASH";
    private HomeListAdapter adapter;
    private List<ListEntity> lists;
    private RecyclerView recyclerView;
    private SharedViewModel sharedViewModel;
    private FragmentHomeBinding binding;
    private TextInputEditText inputEditText;
    private Button addButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        initialSetup();
        observingData();


        inputEditText = binding.edittextInputList;
        addButton = binding.buttonAddList;
        addButton.setOnClickListener(this);
        return root;
    }



    private void initialSetup(){

        lists = new ArrayList<>();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        recyclerView = binding.homeRecyclerview;
        adapter = new HomeListAdapter(this,sharedViewModel,requireActivity(),recyclerView);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter.setOnItemLongClickListener(new HomeListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Log.i("Adapter","Long press");
            }
        });


    }

    private void observingData(){
        sharedViewModel.getListByCategory(LIST_CATEGORY).observe(requireActivity(),lists->{
            adapter.submitList(lists);
            this.lists.clear();
            this.lists.addAll(lists);

        });
        //sharedViewModel.getItemsByListId("List").observe(requireActivity(),adapter::submitInnerData);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onHomeRecyclerViewDelete(int position) {
        if (position >= 0 && position < lists.size()) {
            ListEntity list = lists.get(position);

            if (list != null) {
                list.setListCategory(TRASH_CATEGORY);
                sharedViewModel.updateList(list);
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
                .navigate(R.id.action_nav_home_to_nav_list);

        Toast.makeText(requireActivity(),"item clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        if(view == binding.buttonAddList){
            String listName = Objects.requireNonNull(inputEditText.getText()).toString().trim();
            if (!listName.isEmpty()) {
                ListEntity list = new ListEntity();
                list.setListName(listName);
                list.setListCategory(LIST_CATEGORY);
                sharedViewModel.addList(list);
                inputEditText.setText("");
            } else {
                Toast.makeText(requireActivity(),"Enter valid list name",Toast.LENGTH_SHORT).show();
            }
        }
    }


}