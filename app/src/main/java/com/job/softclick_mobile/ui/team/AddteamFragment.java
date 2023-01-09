package com.job.softclick_mobile.ui.team;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.Member_list_Adapter;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.databinding.FragmentAddteamBinding;
import com.job.softclick_mobile.databinding.FragmentEmployeeFormBinding;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.ui.team.DetailsFragment;
import com.job.softclick_mobile.ui.team.TeamListFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.teams.ITeamViewModel;
import com.job.softclick_mobile.viewmodels.teams.TeamViewModel;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import retrofit2.HttpException;

public class AddteamFragment extends Fragment {

    private FragmentAddteamBinding binding;
    private Team team;
    ITeamViewModel teamViewModel;
    private IEmployeeViewModel EmployeeViewModel;
    private ArrayList<Employee> employeeArrayList;
    private Hashtable<String,Integer> EmployeeHash;
    private List<String> employeeName;
    private AutoCompleteTextView Combo_employee;
    private ListPopupWindow employeepopup;
    CharSequence[] items = {};
    boolean[] selectedItems = {};
    Set<Employee> teamMembers = new HashSet<>();
    long[] employeeIds;
    List<Long> selectedEmployeeIds = new ArrayList<>();
    private Member_list_Adapter member_list_adapter;
    private RecyclerView recyclerView;


    public AddteamFragment() {
        // Required empty public constructor
    }

    public static AddteamFragment newInstance(String param1, String param2) {
        AddteamFragment fragment = new AddteamFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            team = (Team) getArguments().getSerializable("team");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddteamBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);

        if (team != null) {
            binding.teamname.setText(team.getTeam_Name());
            binding.description.setText(team.getDescription());
            binding.teampagetitle.setText("Team Edition ");
            binding.createbutton.setText("Edit");

            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsFragment teamDetailsFragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("team", team);
                    teamDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, teamDetailsFragment).commit();
                }
            });

            binding.createbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTeam((long) team.getIdTeam());
                }
            });

        } else {
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeamListFragment teamListFragment = new TeamListFragment();
                    FooterFragment footerFragment = new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, teamListFragment).commit();
                }
            });

            binding.createbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createTeam();
                }
            });

        }
        EmployeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        EmployeeViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {

            @Override
            public void onChanged(List<Employee> employees) {
                employeeArrayList = new ArrayList<>();

                employees.forEach(employee -> {
                    employeeArrayList.add(employee);
                    //System.out.println("irst name:" +employee.getEmployeeFirstName());
                });

                EmployeeHash = new Hashtable<>();
                items = new CharSequence[employeeArrayList.size()];
                selectedItems = new boolean[employeeArrayList.size()];

                for (int i = 0; i < employeeArrayList.size(); i++) {
                    EmployeeHash.put(employeeArrayList.get(i).getEmployeeLastName() + employeeArrayList.get(i).getEmployeeFirstName(), employeeArrayList.get(i).getId().intValue());
                    items[i] = employeeArrayList.get(i).getEmployeeLastName() + employeeArrayList.get(i).getEmployeeFirstName();
                    selectedItems[i] = false;
                    if(team !=null && team.getMembers() != null){
                        teamMembers = team.getMembers();
                    }

                    for (Employee employee : teamMembers) {
                        if (employeeArrayList.get(i).getId() == employee.getId()) {
                            System.out.println(employee.getEmployeeFirstName());
                            selectedItems[i] = true;
                        }
                    }

                }

                binding.teammembers.setText(itemsToString());

                items = EmployeeHash.keySet().toArray(new CharSequence[0]);

                employeeIds = new long[items.length];
                for (int i = 0; i < items.length; i++) {
                    employeeIds[i] = EmployeeHash.get(items[i]);
                }

            }
        });
        binding.addmeberstoteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Select Employee Skills");
                alertDialogBuilder.setMultiChoiceItems(items, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        selectedItems[which] = isChecked;
                    }
                });
                alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.teammembers.setText(itemsToString());
                        ListView listView = ((AlertDialog)dialogInterface).getListView();
                        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                        // Create a list to store the selected values
                        List<String> selectedValues = new ArrayList<>();
                        selectedEmployeeIds = new ArrayList<>();

                        // Iterate through the checked item positions and add the selected values to the list
                        for (int j = 0; j < checkedItemPositions.size(); j++) {
                            if (checkedItemPositions.valueAt(j)) {
                                selectedValues.add(items[checkedItemPositions.keyAt(j)].toString());
                                selectedEmployeeIds.add(employeeIds[checkedItemPositions.keyAt(j)]);
                                System.out.println("Selected id: " + employeeIds[checkedItemPositions.keyAt(j)] + "Selected value: " + items[checkedItemPositions.keyAt(j)]);
                            }
                        }
                        // Do something with the selected values
                        System.out.println("Selected employees: " + selectedValues);
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();

            }
        });

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;
        return view;
    }

    private String itemsToString() {
        String text = "";
        for(int i=0; i<selectedItems.length; i++) {
            if(selectedItems[i]) {
                text += items[i] + " | ";
            }
        }
        return text.trim();
    }


    public void createTeam(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);
        Team team = new Team(
                binding.teamname.getText().toString(),
                binding.description.getText().toString()
        );
        Set<Employee> selectedEmployees = new HashSet<>();

        for(int i=0; i<selectedEmployeeIds.size(); i++){
            Employee selectedEmployee = new Employee();
            selectedEmployee.setId(selectedEmployeeIds.get(i));
            selectedEmployees.add(selectedEmployee);
        }
        team.setMembers(selectedEmployees);
        LiveResponse createLiveResponse =  teamViewModel.create(team);
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.back.callOnClick();
                }
            }
        });
    }

    public void updateTeam(Long key){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        Team teamNew = new Team(
                binding.teamname.getText().toString(),
                binding.description.getText().toString()
        );
        Set<Employee> selectedEmployees = new HashSet<>();

        for(int i=0; i<selectedEmployeeIds.size(); i++){
            Employee selectedEmployee = new Employee();
            selectedEmployee.setId(selectedEmployeeIds.get(i));
            selectedEmployees.add(selectedEmployee);
        }

        teamNew.setMembers(selectedEmployees);
        this.team = teamNew;
        LiveResponse createLiveResponse =  teamViewModel.update(key, team);


        System.out.println("Team : " + team.getTeam_Name());

        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                System.out.println("changed");
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.back.callOnClick();
                    System.out.println("back called");
                }
            }
        });

        createLiveResponse.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                if (error instanceof HttpException) {
                    Log.d("DEBUG", error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {

                }
                binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }
}