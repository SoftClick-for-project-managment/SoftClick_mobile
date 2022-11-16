package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityClientDetailsBinding;
import com.job.softclick_mobile.models.Client;

public class ClientDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityClientDetailsBinding binding;
    private Intent intent;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClientDetailsBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.menuToolbar);
        binding.menuToolbar.showOverflowMenu();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.menuToolbar,
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

        intent = getIntent();
        Client client = (Client) intent.getSerializableExtra("client");
        Toast.makeText(this, client.toString(), Toast.LENGTH_SHORT).show();
        binding.firstNameValue.setText(client.getPrenom());
        binding.lastNameValue.setText(client.getNom());
        binding.emailValue.setText(client.getEmail());
        binding.phoneValue.setText(client.getTele());
        binding.companyNameValue.setText(client.getNomEntreprise());
        binding.cityValue.setText(client.getVille());
        binding.countryValue.setText(client.getPays());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        Bundle bundle = new Bundle();

        switch (item.getItemId()) {
            case R.id.tasks_item:
            {

                Toast.makeText(ClientDetailsActivity.this, "tasks selected", Toast.LENGTH_SHORT).show();

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
                fragmentClass = ClientListFragment.class;
                break;
            }
            case R.id.employees_item:
            {
                Toast.makeText(this, "employees selected", Toast.LENGTH_SHORT).show();
                fragmentClass = EmployeeFormFragment.class;
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
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        item.setChecked(true);
        binding.menuToolbar.setTitle(item.getTitle());
        binding.drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}