package com.job.softclick_mobile.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentAddProjectBinding;
import com.job.softclick_mobile.databinding.FragmentEmployeeFormBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private FragmentAddProjectBinding binding;
    EditText date_picker_debut;
    EditText date_picker_fin;
    private AutoCompleteTextView Combo_domain;
    private AutoCompleteTextView Combo_client;
    private AutoCompleteTextView Combo_chef;
    ImageView flesh_back;
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
    private String mParam1;
    private String mParam2;

    public AddProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProjectFragment newInstance(String param1, String param2) {
        AddProjectFragment fragment = new AddProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddProjectBinding.inflate(inflater, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        date_picker_debut =  binding.dateDebut;
        date_picker_fin = binding.datePickerFin;
        Combo_domain = binding.domainCombo;
        Combo_client = binding.clientCombo;
        Combo_chef = binding.chefCombo;
        flesh_back = binding.fleshBack;

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
                    getParentFragmentManager().popBackStackImmediate();
                }
            }
        });
        return binding.getRoot();
    }

}