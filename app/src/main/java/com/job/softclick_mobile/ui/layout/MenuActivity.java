package com.job.softclick_mobile.ui.layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.models.Role;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.services.storage.StoredUser;
import com.job.softclick_mobile.ui.auth.LoginActivity;
import com.job.softclick_mobile.ui.auth.ProfileFragment;
import com.job.softclick_mobile.ui.team.TeamListFragment;
import com.job.softclick_mobile.ui.tasks.TaskList;
import com.job.softclick_mobile.ui.employees.EmployeeListFragment;
import com.job.softclick_mobile.ui.invoices.InvoiceListFragment;
import com.job.softclick_mobile.ui.projectFragments.ListProjectsFragment;
import com.job.softclick_mobile.ui.expense.ExpensesListFragment;
import com.job.softclick_mobile.ui.clients.ClientListFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMenuBinding binding;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private IUserViewModel userViewModel;
    private User authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        LiveResponse<User, Throwable> authUserLiveResp = userViewModel.getAuthenticated();
        authUserLiveResp.gettMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser = user;
                Toast.makeText(MenuActivity.this, "Welcome "+authUser.getEmployee().getEmployeeFirstName(), Toast.LENGTH_SHORT).show();
            }
        });
        authUserLiveResp.geteMutableLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(Throwable th) {
                if (th instanceof IOException) {
                    Toast.makeText(MenuActivity.this, "Check internet connection and try again", Toast.LENGTH_SHORT).show();
                }
                Log.d("ERR", th.getMessage());
            }
        });

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


        // Check the user's role and hide the menu item if necessary
        checkUserRole();


    }

    private void checkUserRole() {

        for(Role role: authUser.getRoles()) {

            Menu menu = binding.navView.getMenu();
            MenuItem menuItemTasks = menu.findItem(R.id.tasks_item);
            MenuItem menuItemTeams = menu.findItem(R.id.teams_item);
            MenuItem menuItemProjects = menu.findItem(R.id.projects_item);
            MenuItem menuItemClients = menu.findItem(R.id.clients_item);
            MenuItem menuItemEmployees = menu.findItem(R.id.employees_item);
            MenuItem menuItemExpenses = menu.findItem(R.id.expenses_item);
            MenuItem menuItemInvoices = menu.findItem(R.id.invoices_item);

            switch (role.getName())  {
                case Role.ROLE_DIRECTOR:
                    menuItemTasks.setVisible(true);
                    menuItemTeams.setVisible(true);
                    menuItemProjects.setVisible(true);
                    menuItemClients.setVisible(true);
                    menuItemEmployees.setVisible(true);
                    menuItemExpenses.setVisible(true);
                    menuItemInvoices.setVisible(true);
                    break;
                case Role.ROLE_PROJECT_MANAGER:
                    menuItemTasks.setVisible(true);
                    menuItemTeams.setVisible(false);
                    menuItemProjects.setVisible(true);
                    menuItemClients.setVisible(false);
                    menuItemEmployees.setVisible(false);
                    menuItemExpenses.setVisible(false);
                    menuItemInvoices.setVisible(false);
                    break;
                case Role.ROLE_EMPLOYEE:
                case Role.ROLE_TEAM_MANAGER:
                    menuItemTasks.setVisible(true);
                    menuItemTeams.setVisible(true);
                    menuItemProjects.setVisible(false);
                    menuItemClients.setVisible(false);
                    menuItemEmployees.setVisible(false);
                    menuItemExpenses.setVisible(false);
                    menuItemInvoices.setVisible(false);
                    break;
                case Role.ROLE_ADMIN:
                    break;
            }

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;


        switch (item.getItemId()) {
            case R.id.tasks_item:
            {
                try{
                    fragmentClass = TaskList.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                }
                catch (Exception e ){
                }

                break;
            }
            case R.id.teams_item:
            {
                try{
                    fragmentClass = TeamListFragment.class;
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }

                break;
            }
            case R.id.clients_item:
            {
                try{
                    fragmentClass = ClientListFragment.class;
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }

                break;
            }
            case R.id.employees_item:
            {
                try{
                    fragmentClass = EmployeeListFragment.class;
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }
                //findViewById(R.id.addButton).setOnClickListener(EmployeeListFragment.add());
                break;
            }
            case R.id.expenses_item:
            {
                try{
                    fragmentClass = ExpensesListFragment.class;
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }
                break;
            }
            case R.id.invoices_item:
            {
                try{
                    fragmentClass = InvoiceListFragment.class;
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }
                break;
            }
            case R.id.projects_item:
            {
                try{
                    fragmentClass = ListProjectsFragment.class;
                    fragmentManager.beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }
                break;
            }
            case R.id.profile_item:
            {
                try{
                    fragmentClass = ProfileFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                }
                catch (Exception e ){
                }
                break;
            }
            case R.id.logout_item:
            {
                StoredUser.clear(getApplicationContext());
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                MenuActivity.this.finish();
                return true;
            }
            default:
                //fragmentClass = FirstFragment.class;
                break;
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
