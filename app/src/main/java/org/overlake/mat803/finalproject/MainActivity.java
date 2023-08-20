package org.overlake.mat803.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;

import org.overlake.mat803.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm = getSupportFragmentManager();
        NavHostFragment mNavigation = (NavHostFragment) fm.findFragmentById(R.id.myNavHostFragment);
        NavController mNavController = mNavigation.getNavController();
        NavigationUI.setupActionBarWithNavController(this, mNavController);
        NavigationUI.setupWithNavController(binding.navView, mNavController);
        mDrawerLayout = binding.drawerLayout;
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();
        //NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayout);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController mNavController = Navigation.findNavController(this, R.id.myNavHostFragment);
        return NavigationUI.navigateUp(mNavController, mDrawerLayout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if (mToggle.onOptionsItemSelected(item)) {
        //    return true;
        //}
        return super.onOptionsItemSelected(item);
    }
}

