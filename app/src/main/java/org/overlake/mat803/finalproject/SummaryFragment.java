package org.overlake.mat803.finalproject;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.util.ArrayMap;
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

import org.overlake.mat803.finalproject.databinding.FragmentSummaryBinding;

import java.util.ArrayList;
import java.util.List;

public class SummaryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSummaryBinding binding = FragmentSummaryBinding.inflate(inflater);
        NavController controller = NavHostFragment.findNavController(this);
        binding.summaryView.setAdapter(new StockAdapter(this, getLayoutInflater(), controller, true));
        binding.summaryView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((SimpleItemAnimator) binding.summaryView.getItemAnimator()).setSupportsChangeAnimations(false);
        binding.watchList.setOnClickListener(view -> {
            controller.navigate(R.id.action_summaryFragment_to_listFragment);
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
