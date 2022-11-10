package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMenuBinding binding;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        toolbar = findViewById(R.id.menu_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()) {
            case R.id.tasks_item:
            {
                Toast.makeText(MenuActivity.this, "tasks selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.teams_item:
            {
                Toast.makeText(this, "teams selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.clients_item:
            {
                Toast.makeText(this, "clients selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.employees_item:
            {
                Toast.makeText(this, "employees selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.expenses_item:
            {
                Toast.makeText(this, "expenses selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.invoices_item:
            {
                Toast.makeText(this, "invoices selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.logout_item:
            {
                Toast.makeText(this, "logout selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            default:
                fragmentClass = FirstFragment.class;
                break;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        // Set action bar title
        setTitle(item.getTitle());

        // Close the navigation drawer
        drawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    /*@NonNull
    @Override
    public androidx.lifecycle.viewmodel.CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }*/
}