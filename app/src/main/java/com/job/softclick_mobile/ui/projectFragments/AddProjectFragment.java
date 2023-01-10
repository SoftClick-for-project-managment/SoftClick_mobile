package com.job.softclick_mobile.ui.projectFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.MainRecyclerAdapter;
import com.job.softclick_mobile.databinding.FragmentAddProjectBinding;
import com.job.softclick_mobile.databinding.FragmentListProjectsBinding;
import com.job.softclick_mobile.models.Domain;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Priority;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.models.Status;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.viewmodels.domains.DomainViewModel;
import com.job.softclick_mobile.viewmodels.domains.IDomainViewModel;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.priority.IPriorityViewModel;
import com.job.softclick_mobile.viewmodels.priority.PriorityViewModel;
import com.job.softclick_mobile.viewmodels.project.IProjectViewModel;
import com.job.softclick_mobile.viewmodels.project.ProjectViewModel;
import com.job.softclick_mobile.viewmodels.status.IStatusViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;
import com.job.softclick_mobile.viewmodels.teams.ITeamViewModel;
import com.job.softclick_mobile.viewmodels.teams.TeamViewModel;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProjectFragment<items> extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";


    private FragmentAddProjectBinding binding;
    EditText name_project, description, revenue;
    EditText date_picker_debut;
    EditText date_picker_fin;
    private AutoCompleteTextView Combo_domain;
    private AutoCompleteTextView Combo_priority;
    private AutoCompleteTextView Combo_chef;
    ImageView flesh_back;
    TextView add_btn, update_btn, title_page , selectTeambtn , selectedTeamsText;
    Date date_debut=new Date();
    Date date_fin = new Date();
    private IDomainViewModel domainViewModel;
    HashMap<String,Long> domains_pairs = new HashMap<>();
    HashMap<String,Long> employe_pairs = new HashMap<>();
    HashMap<String,Integer> priority_pairs = new HashMap<>();

    List<Team> teamArrayList = new ArrayList<>();
    CharSequence[] items;
    boolean[] selectedItems;
    Hashtable<String,Long> teamHash=new Hashtable<>();
    long[] teamIds;
    List<Long> selectedTeamIds = new ArrayList<>();


    String[] clients = new String[]{
            "Faisal",
            "Lc Waikiki",
            "Marwa",
            "Abtal cham",
            "Marjan"
    };
    String[] Chefs = new String[]{
            "Othman",
            "Hajar",
            "Hind",
            "Wafae",
            "Soukaina",
            "Manal"
    };

    // TODO: Rename and change types of parameters
    private Project project = null;
    private IProjectViewModel projectViewModel;
    private IEmployeeViewModel employeeViewModel;
    private IPriorityViewModel priorityViewModel;
    private ITeamViewModel teamViewModel;


    public AddProjectFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddProjectFragment newInstance(Project project_param) {
        AddProjectFragment fragment = new AddProjectFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, project_param);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddProjectBinding.inflate(inflater, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();


        projectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);
        domainViewModel = new ViewModelProvider(this).get(DomainViewModel.class);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        priorityViewModel = new ViewModelProvider(this).get(PriorityViewModel.class);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);

        title_page = binding.pageTitle;
        name_project = binding.nameProjectInput;
        description = binding.desciptionInput;
        revenue = binding.revenueInput;
        date_picker_debut = binding.dateDebut;
        date_picker_fin = binding.datePickerFin;
        Combo_domain = binding.domainCombo;
        Combo_priority = binding.priorityCombo;
        Combo_chef = binding.chefCombo;
        flesh_back = binding.fleshBack;
        add_btn = binding.addbtn;
        update_btn = binding.updatebtn;
        selectTeambtn = binding.selectTeamsbtn;
        selectedTeamsText = binding.selectedTeamsText;



        domainViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Domain>>() {

            @Override
            public void onChanged(List<Domain> domains) {

                try {
                    domains.forEach(domain -> {
                        domains_pairs.put(domain.getNameDomain(), domain.getIdDomain());
                    });
                    domains_pairs.put("",null);
                    List<String> domain_names = new ArrayList<>(domains_pairs.keySet());
                    ArrayAdapter<String> adapter_domain = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, domain_names);
                    Combo_domain.setAdapter(adapter_domain);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }

        });

        employeeViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {

            @Override
            public void onChanged(List<Employee> employees) {

                try {
                    employees.forEach(employee -> {
                        employe_pairs.put(employee.getEmployeeLastName()+" "+employee.getEmployeeFirstName(), (long) employee.getId());
                    });
                    employe_pairs.put("",null);
                    List<String> employe_names = new ArrayList<>(employe_pairs.keySet());
                    ArrayAdapter<String> adapter_employe = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, employe_names);
                    Combo_chef.setAdapter(adapter_employe);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        priorityViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Priority>>() {

            @Override
            public void onChanged(List<Priority> priorities) {

                try {
                    priorities.forEach(priority -> {
                        priority_pairs.put(priority.getNamePriority(),  priority.getIdPriority());
                    });
                    priority_pairs.put("",null);
                    List<String> priority_names = new ArrayList<>(priority_pairs.keySet());
                    ArrayAdapter<String> adapter_prioritiy = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, priority_names);
                    Combo_priority.setAdapter(adapter_prioritiy);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
            }
        });

        if (project == null) {
            title_page.setText(R.string.add_project);
            update_btn.setVisibility(View.GONE);
            add_btn.setVisibility(View.VISIBLE);
            title_page.setText(R.string.addbtn);
        } else {
            title_page.setText(R.string.updatebtn);
            add_btn.setVisibility(View.GONE);
            update_btn.setVisibility(View.VISIBLE);
            name_project.setText(project.getNameProject());
            description.setText(project.getDescriptionProject());
            revenue.setText(project.getRevenueProject().toString());
            Combo_domain.setText(project.getDomainProjet().getNameDomain());
            Combo_priority.setText(project.getProjectPriority().getNamePriority());
            Combo_chef.setText(project.getChefProject().getEmployeeLastName()+" "+project.getChefProject().getEmployeeFirstName());

            date_picker_debut.setText(project.getDateDebut().toString());
            date_debut.setTime(project.getDateDebut().getTime());

            date_picker_fin.setText(project.getDateFin().toString());
            date_fin.setTime(project.getDateFin().getTime());



            //and complete all fields ... TODO

        }




        ArrayAdapter<String> adapter_priority= new ArrayAdapter<>(getActivity(),
                R.layout.dropdown_item, clients);
        Combo_priority.setAdapter(adapter_priority);

        ArrayAdapter<String> adapter_chef = new ArrayAdapter<>(getActivity(),
                R.layout.dropdown_item, Chefs);
        Combo_chef.setAdapter(adapter_chef);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        final MaterialDatePicker materialDatePicker_debut = builder.build();
        materialDatePicker_debut.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override public void onPositiveButtonClick(Long selection) {
                // Do something...
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                date_debut = calendar.getTime();
                date_picker_debut.setText(materialDatePicker_debut.getHeaderText());

            }});

        final MaterialDatePicker materialDatePicker_fin = builder.build();
        materialDatePicker_fin.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override public void onPositiveButtonClick(Long selection) {
                // Do something...
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                date_fin= calendar.getTime();
                date_picker_fin.setText(materialDatePicker_fin.getHeaderText());

            }});

        date_picker_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                materialDatePicker_debut.show(getParentFragmentManager(), "DATE_PICKER");

            }
        });

        date_picker_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker_fin.show(getParentFragmentManager(), "DATE_PICKER");
            }
        });



        flesh_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    try {
                        getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                        getParentFragmentManager().popBackStackImmediate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_project();
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_project();
            }
        });

        //-------------------------------------------------------------
        teamViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                teamArrayList = new ArrayList<>();

                teams.forEach(team -> {
                    teamArrayList.add(team);
                });
                teamHash = new Hashtable<>();
                items = new CharSequence[teamArrayList.size()];
                selectedItems = new boolean[teamArrayList.size()];
                List<Team> teamsProject = (project != null)? new ArrayList<>(project.getTeams()):new ArrayList<>();
                for (int i = 0; i < teamArrayList.size(); i++) {
                    teamHash.put(teamArrayList.get(i).getTeam_Name(), teamArrayList.get(i).getIdTeam());
                    items[i] = teamArrayList.get(i).getTeam_Name();
                    selectedItems[i] = false;

                    for(Team team: teamsProject){
                        if(teamArrayList.get(i).getIdTeam() == team.getIdTeam()){
                            selectedItems[i] = true;
                        }
                    }
                }
                binding.selectedTeamsText.setText(itemsToString());

                items = teamHash.keySet().toArray(new CharSequence[0]);

                teamIds = new long[items.length];
                for (int i = 0; i < items.length; i++) {
                    teamIds[i] = teamHash.get(items[i]);
                }
            }
        });
        //-------------------------------------------------------------
        binding.selectTeamsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Select teams to work on this proejct");
                alertDialogBuilder.setMultiChoiceItems(items, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        selectedItems[which] = isChecked;
                    }
                });
                alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.selectedTeamsText.setText(itemsToString());
                        ListView listView = ((AlertDialog)dialogInterface).getListView();
                        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                        // Create a list to store the selected values
                        List<String> selectedValues = new ArrayList<>();
                        selectedTeamIds = new ArrayList<>();

                        // Iterate through the checked item positions and add the selected values to the list
                        for (int j = 0; j < checkedItemPositions.size(); j++) {
                            if (checkedItemPositions.valueAt(j)) {
                                selectedValues.add(items[checkedItemPositions.keyAt(j)].toString());
                                selectedTeamIds.add(teamIds[checkedItemPositions.keyAt(j)]);

                            }
                        }

                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();

            }
        });
        //-------------------------------------------------------------

        return binding.getRoot();
    }



    public Project validate() {
        Project added_project = null;
        boolean is_validated = true;
        String name = name_project.getText().toString().trim();
        String description_text = description.getText().toString().trim();
        Double revenue_text=0d ;
        try{
             revenue_text = Double.parseDouble(revenue.getText().toString().trim());
        }catch (Exception e){
            revenue.setHint(" Revenu is requered as number ! ");
            revenue.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            is_validated = false;
        }



        Long domain_id = domains_pairs.get(Combo_domain.getText().toString());
        Domain domain = new Domain();
        domain.setIdDomain(domain_id);

        Long chef_id = employe_pairs.get(Combo_chef.getText().toString());

        Employee chef = new Employee();
        chef.setId(chef_id);

        Integer priority_id = priority_pairs.get(Combo_priority.getText().toString());
        Priority priority = new Priority(priority_id,1f,"");
        String priority_name = Combo_priority.getText().toString().trim();

        Status status = new Status(1l,"");


        if (name.equals("")) {
            name_project.setHint(" Project name is required ! ");
            name_project.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            is_validated = false;


        }
        if (Combo_domain.getText().toString().equals("")) {
            Combo_domain.setHint(" Domain is required ! ");
            Combo_domain.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            is_validated = false;

        }
        if (priority_name.equals("")) {
            Combo_priority.setHint(" priority is required ! ");
            Combo_priority.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            is_validated = false;

        }
        if (Combo_chef.getText().toString().equals("")) {
            Combo_chef.setHint(" chef is required ! ");
            Combo_chef.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            is_validated = false;

        }

        if (date_picker_debut.getText().toString().trim().equals("")) {
            date_picker_debut.setHint(" Date debut is required ! ");
            date_picker_debut.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            is_validated = false;

        }

        if(!is_validated){
            return null;
        }

        Set<Team> selectedTeams = new HashSet<>();

        for(int i=0; i<selectedTeamIds.size(); i++){
            Team selectedTeam = new Team();
            selectedTeam.setIdTeam(selectedTeamIds.get(i));
            selectedTeams.add(selectedTeam);
        }



        added_project = new Project(name,description_text,revenue_text,domain,new Timestamp(date_debut.getTime()),new Timestamp(date_fin.getTime()),chef,status,priority);
        added_project.setTeams(selectedTeams);

        return added_project;
    }

    public void update_project() {
        binding.progressBar.setVisibility(View.VISIBLE);

        Project validated_project = validate();
        if (validated_project != null) {
            try {
                projectViewModel.update(project.getIdProject(),validated_project);
                binding.progressBar.setVisibility(View.VISIBLE);
                getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, new FooterFragment(ListProjectsFragment.class)).commit();
                ListProjectsFragment fragment = fragment = ListProjectsFragment.class.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void add_project() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Project validated_project = validate();
        if (validated_project != null) {
            try {

               Log.d("CONSOLE LOG", "project is  : "+validated_project.toString());
                projectViewModel.create(validated_project);
                binding.progressBar.setVisibility(View.VISIBLE);
                getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, new FooterFragment(ListProjectsFragment.class)).commit();
                ListProjectsFragment fragment = fragment = ListProjectsFragment.class.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

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


}