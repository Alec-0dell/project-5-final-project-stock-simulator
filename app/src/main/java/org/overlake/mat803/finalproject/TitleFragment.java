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

import org.overlake.mat803.finalproject.databinding.FragmentTitleBinding;

public class TitleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentTitleBinding binding = FragmentTitleBinding.inflate(inflater);
        NavController controller = NavHostFragment.findNavController(this);
        binding.startButton.setOnClickListener(view -> {
            controller.navigate(R.id.action_titleFragment_to_summaryFragment);
        });
        binding.helpButton.setOnClickListener(view -> {
            controller.navigate(R.id.action_titleFragment_to_helpFragment);
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
