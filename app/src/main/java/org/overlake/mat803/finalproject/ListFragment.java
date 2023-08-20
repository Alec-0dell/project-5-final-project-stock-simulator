package org.overlake.mat803.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import org.overlake.mat803.finalproject.databinding.FragmentListBinding;

import java.util.List;

public class ListFragment extends Fragment {
    StockAdapter adapter;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        NavController controller = NavHostFragment.findNavController(this);
        this.adapter = new StockAdapter(this, getLayoutInflater(),controller, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentListBinding binding = FragmentListBinding.inflate(inflater);
        NavController controller = NavHostFragment.findNavController(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((SimpleItemAnimator) binding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.buttonSummary.setOnClickListener(view -> {
            controller.navigate(R.id.action_listFragment_to_summaryFragment);
        });
        binding.defualt.setOnClickListener(view -> {
            adapter.insertDefaultStocks();
        });
        binding.search.setOnClickListener(view -> {
            adapter.searchStock();
        });
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.navdrawer_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController mNavController = NavHostFragment.findNavController(this);
        return NavigationUI.onNavDestinationSelected(item, mNavController ) ||super.onOptionsItemSelected(item);
    }
}
