package com.job.softclick_mobile.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentAddProjectBinding;
import com.job.softclick_mobile.models.Project;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";



    private FragmentAddProjectBinding binding;
    EditText name_project , description , revenue;
    EditText date_picker_debut;
    EditText date_picker_fin;
    private AutoCompleteTextView Combo_domain;
    private AutoCompleteTextView Combo_client;
    private AutoCompleteTextView Combo_chef;
    ImageView flesh_back;
    TextView add_btn,update_btn , title_page ;
    String[] domains = new String[]{
            "Info",
            "indus",
            "Electrique",
            "Architect",
            "Civil"
    };
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
    private Project project= null;


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

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        title_page = binding.pageTitle;
        name_project = binding.nameProjectInput;
        description = binding.desciptionInput;
        revenue = binding.revenueInput;
        date_picker_debut =  binding.dateDebut;
        date_picker_fin = binding.datePickerFin;
        Combo_domain = binding.domainCombo;
        Combo_client = binding.clientCombo;
        Combo_chef = binding.chefCombo;
        flesh_back = binding.fleshBack;
        add_btn = binding.addbtn;
        update_btn = binding.updatebtn;

        if(project == null){
            title_page.setText(R.string.add_project);
            update_btn.setVisibility(View.GONE);
            add_btn.setVisibility(View.VISIBLE);
            title_page.setText(R.string.addbtn);
        }else{
            title_page.setText(R.string.updatebtn);
            add_btn.setVisibility(View.GONE);
            update_btn.setVisibility(View.VISIBLE);
            name_project.setText(project.getNameProject());
            description.setText(project.getDescriptionProject());
            revenue.setText(project.getRevenue().toString());
            //and complete all fields ... TODO

        }

        ArrayAdapter<String> adapter_domain = new ArrayAdapter<>(getActivity(),
                R.layout.dropdown_item,domains);
        Combo_domain.setAdapter(adapter_domain);

        ArrayAdapter<String> adapter_client = new ArrayAdapter<>(getActivity(),
                R.layout.dropdown_item,clients);
        Combo_client.setAdapter(adapter_client);

        ArrayAdapter<String> adapter_chef= new ArrayAdapter<>(getActivity(),
                R.layout.dropdown_item,Chefs);
        Combo_chef.setAdapter(adapter_chef);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        final MaterialDatePicker materialDatePicker_debut = builder.build();
        final MaterialDatePicker materialDatePicker_fin = builder.build();

        date_picker_debut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker_debut.show(getParentFragmentManager() ,"DATE_PICKER");
            }
        });

        date_picker_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker_fin.show(getParentFragmentManager() ,"DATE_PICKER");
            }
        });

        materialDatePicker_debut.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                date_picker_debut.setText(materialDatePicker_debut.getHeaderText());
            }
        });

        materialDatePicker_fin.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                date_picker_fin.setText(materialDatePicker_fin.getHeaderText());
            }
        });

        flesh_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getParentFragmentManager().getBackStackEntryCount() > 0){
                    try {
                        getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                        getParentFragmentManager().popBackStackImmediate();
                    }catch (Exception e) {
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

        return binding.getRoot();
    }

    public Boolean validate(){
        Boolean isValidated = true;
        String name = name_project.getText().toString().trim();
        String client = Combo_client.getText().toString().trim();
        String domain = Combo_domain.getText().toString().trim();
        if(name.equals("")){
            name_project.setHint(" Project name is required ! ");
            name_project.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            isValidated = false;

        }
        if(domain.equals("") ){
            Combo_domain.setHint(" Domain is required ! ");
            Combo_domain.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            isValidated = false;
        }
        if(client.equals("") ){
            Combo_client.setHint(" Client is required ! ");
            Combo_client.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            isValidated = false;
        }

        if(date_picker_debut.getText().toString().trim().equals("")){
            date_picker_debut.setHint(" Date debut is required ! ");
            date_picker_debut.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            isValidated = false;
        }
        return  isValidated;
    }
    public void update_project(){
        Boolean isValidated = validate();
        if(isValidated){
            try {
                getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                Fragment fragment = new ListProjectsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void add_project(){
       Boolean isValidated = validate();
        if(isValidated){
            try {
                getParentFragmentManager().beginTransaction().replace(R.id.fContentFooter, (Fragment) FooterFragment.class.newInstance()).commit();
                Fragment fragment = new ListProjectsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}