package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMenuBinding binding;
    private  FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.menuHeader.menuToolbar);
        binding.menuHeader.menuToolbar.showOverflowMenu();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.menuHeader.menuToolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        //------------To change Navigation drawer icon ---------------
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        // Insert the fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu1) {
        super.onCreateOptionsMenu(menu1);
        getMenuInflater().inflate(R.menu.header_menu, menu1);
        return true;
    }*/


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
            case R.id.projects_item:
            {
                Toast.makeText(this, "projects selected", Toast.LENGTH_SHORT).show();
                fragmentClass = FirstFragment.class;
                break;
            }
            case R.id.clients_item:
            {
                Toast.makeText(this, "clients selected", Toast.LENGTH_SHORT).show();
                fragmentClass = ClientListFragment.class;
                break;
            }
            case R.id.employees_item:
            {
                Toast.makeText(this, "employees selected", Toast.LENGTH_SHORT).show();
                fragmentClass = EmployeeListFragment.class;
                //findViewById(R.id.addButton).setOnClickListener(EmployeeListFragment.add());
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
                fragmentClass = InvoiceFormFragment.class;
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
            fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
            fragment = (Fragment) fragmentClass.newInstance();
//            Bundle bundle = new Bundle();
//            bundle.putInt("newInt", 5);
//            bundle.putString("newText", "Tasks Selected");
//            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Insert the fragment by replacing any existing fragment
        //FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();



        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);

        // Set action bar title
        binding.menuHeader.menuToolbar.setTitle(item.getTitle());

        // Close the navigation drawer
        binding.drawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}
