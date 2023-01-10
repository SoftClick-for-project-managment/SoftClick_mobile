package com.job.softclick_mobile.ui.employees;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.databinding.FragmentEmployeeFormBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Role;
import com.job.softclick_mobile.models.Skill;
import com.job.softclick_mobile.models.User;
import com.job.softclick_mobile.repositories.employees.ISkillRepository;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.ISkillViewModel;
import com.job.softclick_mobile.viewmodels.employees.SkillViewModel;
import com.job.softclick_mobile.viewmodels.user.IUserViewModel;
import com.job.softclick_mobile.viewmodels.user.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import retrofit2.HttpException;

public class EmployeeFormFragment extends Fragment {

    private static final int SELECT_IMAGE_REQUEST_CODE = 1000;
    private FragmentEmployeeFormBinding binding;
    private Employee employee;
    IEmployeeViewModel employeeViewModel;
    IUserViewModel userViewModel;
    CharSequence[] items = {};
    Set<Skill> employeeSkills = new HashSet<>();
    long[] skillIds;
    List<Long> selectedSkillIds;
    boolean[] selectedItems = {};
    ISkillViewModel skillViewModel;
    Hashtable<String, Long> skillHash;
    Bitmap bitmap;
    Uri imageUri;

    private ArrayList<Skill> skillArrayList;

    private Collection<Role> roles = new ArrayList<>();
    private Role role;

    public EmployeeFormFragment() {
    }

    public static EmployeeFormFragment newInstance(String param1, String param2) {
        EmployeeFormFragment fragment = new EmployeeFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            employee = (Employee) getArguments().getSerializable("employee");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeFormBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

        skillViewModel = new ViewModelProvider(this).get(SkillViewModel.class);

        skillViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Skill>>() {

            @Override
            public void onChanged(List<Skill> skills) {
                skillArrayList = new ArrayList<>();

                skills.forEach(skill -> {
                    skillArrayList.add(skill);
                });

                skillHash = new Hashtable<>();
                items = new CharSequence[skillArrayList.size()];
                selectedItems = new boolean[skillArrayList.size()];

                for (int i = 0; i < skillArrayList.size(); i++) {
                    skillHash.put(skillArrayList.get(i).getSkillName(), skillArrayList.get(i).getId());
                    items[i] = skillArrayList.get(i).getSkillName();
                    selectedItems[i] = false;

                    for(Skill skill: employeeSkills){
                        System.out.println(skill.getSkillName() + "=?" + skillArrayList.get(i).getSkillName());
                        if(skillArrayList.get(i).getId() == skill.getId()){
                            System.out.println(skill.getSkillName());
                            selectedItems[i] = true;
                        }
                    }

                }

                binding.employeeSkills.setText(itemsToString());

                items = skillHash.keySet().toArray(new CharSequence[0]);

                skillIds = new long[items.length];
                for (int i = 0; i < items.length; i++) {
                    skillIds[i] = skillHash.get(items[i]);
                }

            }
        });

        binding.employeeSkills.setText(itemsToString());

        binding.selectEmployeeSkillsBtn.setOnClickListener(new View.OnClickListener() {
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
                        binding.employeeSkills.setText(itemsToString());
                        ListView listView = ((AlertDialog)dialogInterface).getListView();
                        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                        // Create a list to store the selected values
                        List<String> selectedValues = new ArrayList<>();
                        selectedSkillIds = new ArrayList<>();

                        // Iterate through the checked item positions and add the selected values to the list
                        for (int j = 0; j < checkedItemPositions.size(); j++) {
                            if (checkedItemPositions.valueAt(j)) {
                                selectedValues.add(items[checkedItemPositions.keyAt(j)].toString());
                                selectedSkillIds.add(skillIds[checkedItemPositions.keyAt(j)]);
                                System.out.println("Selected id: " + skillIds[checkedItemPositions.keyAt(j)] + "Selected value: " + items[checkedItemPositions.keyAt(j)]);
                            }
                        }

                        System.out.println("Selected skills: " + selectedValues);
                        dialogInterface.dismiss();
                    }
                });

                alertDialogBuilder.setNeutralButton("Add Skill", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform some action when the button is clicked
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setTitle("Add New Skill");

                        final EditText skillInput = new EditText(getActivity());
                        skillInput.setInputType(InputType.TYPE_CLASS_TEXT);

                        alertDialogBuilder.setView(skillInput);


                        alertDialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newSkill = skillInput.getText().toString();
                                Toast.makeText(getActivity(), "New Skill "+newSkill, Toast.LENGTH_SHORT).show();
                                createSkill(newSkill);
                            }
                        });
                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.setCanceledOnTouchOutside(true);
                        alertDialog.show();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();

            }
        });

        binding.employeePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickImageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImageIntent, SELECT_IMAGE_REQUEST_CODE);
            }
        });

        if(employee != null) {
            binding.firstName.setText(employee.getEmployeeFirstName());
            binding.lastName.setText(employee.getEmployeeLastName());
            binding.employeeEmail.setText(employee.getEmployeeEmail());
            binding.employeePhone.setText(employee.getEmployeePhone());
            binding.employeeFunction.setText(employee.getEmployeeFunction());
            if(employee.getEmployeeImage() == null) {
                binding.employeePhoto.setImageResource(R.drawable.user_photo);
            }
            else {
                binding.employeePhoto.setImageBitmap(BitmapFactory.decodeFile(employee.getEmployeeImage()));
            }

            for(Skill skill: employee.getSkills()){
                employeeSkills.add(skill);
            }


            binding.pageTitle.setText("Employee Edition ");
            binding.createEmployeeBtn.setText("Edit");

            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmployeeDetailsFragment employeeDetailsFragment = new EmployeeDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("employee", employee);
                    employeeDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeDetailsFragment).commit();
                }
            });

            binding.createEmployeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateEmployee((long) employee.getId());
                }
            });

        }

        else{
            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmployeeListFragment employeeListFragment=new EmployeeListFragment();
                    FooterFragment footerFragment=new FooterFragment(EmployeeListFragment.class);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeListFragment).commit();
                }
            });

            binding.createEmployeeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createEmployee();
                }
            });

        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.employeePhoto.setImageURI(imageUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("image uri" + imageUri);
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

    public void createEmployee(){
        Employee validateEmployee = validate();
        if(validateEmployee != null) {
            System.out.println("Create Employee function <<<<<<<<<<<<<<<<");
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.formBody.setVisibility(View.GONE);

            //__________________________________________
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream = new ByteArrayOutputStream();
            String base64Image = "";
            byte[] bytes = {};
            File imageFile;

            if(bitmap!= null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                bytes = byteArrayOutputStream.toByteArray();
                base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                File directory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                int index = validateEmployee.getEmployeeEmail().indexOf("@");
                String userName = validateEmployee.getEmployeeEmail().substring(0, index);
                String fileName = userName.toString() + ".jpg";
                imageFile = new File(directory, fileName);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(imageFile);
                    fos.write(bytes);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                imageFile = null;
                System.out.println("bitmap null");
            }



            //__________________________________________

            Employee employee = new Employee(
                    null,
                    binding.firstName.getText().toString(),
                    binding.lastName.getText().toString(),
                    binding.employeeFunction.getText().toString(),
                    binding.employeeEmail.getText().toString(),
                    binding.employeePhone.getText().toString()
            );

            Set<Skill> selectedSkills = new HashSet<>();

            for(int i=0; i<selectedSkillIds.size(); i++){
                Skill selectedSkill = new Skill();
                selectedSkill.setId(selectedSkillIds.get(i));
                selectedSkills.add(selectedSkill);
            }

            if(imageFile!=null){
                employee.setEmployeeImage(imageFile.getAbsolutePath());
            }


            employee.setSkills(selectedSkills);

            System.out.println("Employee Email ::: " + employee.getEmployeeEmail());
            System.out.println("Employee Skills::: " + employee.getSkills());

            LiveResponse createLiveResponse =  employeeViewModel.create(employee);


            createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Employee>() {
                @Override
                public void onChanged(Employee e) {
                    if(e != null ){
                        System.out.println("Employee Id <<<<<<<<<" + e.getId());
                        createUser(e);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.back.callOnClick();

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
                        Toast.makeText(getContext(), "Can't Create the Employee!", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof IOException) {

                    }
                    binding.formBody.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    Log.d("ERR", error.getMessage());
                }
            });
        }
        else{
            return;
        }


    }

    public void createUser(Employee e){

        User user = new User();

        int index = e.getEmployeeEmail().indexOf("@");
        String userName = e.getEmployeeEmail().substring(0, index);
        user.setUsername(userName);
        user.setEmployee(e);
        user.setPassword("password");

        System.out.println("UserName ------> " + user.getUsername());
        System.out.println("UserPass ------> " + user.getPassword());
        System.out.println("UserEmployee ------> " + user.getEmployee().getEmployeeEmail());

        role = new Role();
        role.setName(Role.ROLE_EMPLOYEE);
        roles.add(role);
        user.setRoles(roles);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        LiveResponse createLiveResponse =  userViewModel.create(user);

        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    binding.back.callOnClick();
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

    public static <T, E> T getKey(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void updateEmployee(Long key){
        Employee validateEmployee = validate();
        if(validateEmployee != null) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.formBody.setVisibility(View.GONE);

            Employee employee = new Employee(
                    "employee4.png",
                    binding.firstName.getText().toString(),
                    binding.lastName.getText().toString(),
                    binding.employeeFunction.getText().toString(),
                    binding.employeeEmail.getText().toString(),
                    binding.employeePhone.getText().toString()
            );

            Set<Skill> selectedSkills = new HashSet<>();

            for(int i=0; i<selectedSkillIds.size(); i++){
                Skill selectedSkill = new Skill();
                selectedSkill.setId(selectedSkillIds.get(i));


                selectedSkill.setSkillName(getKey(skillHash, selectedSkillIds.get(i)));
                selectedSkills.add(selectedSkill);
            }

            employee.setSkills(selectedSkills);

            LiveResponse createLiveResponse =  employeeViewModel.update(key, employee);

            this.employee = employee;

            System.out.println("Employee ::: " + employee.getEmployeeEmail());

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

        }else{
            return;
        }


    }

    public void createSkill(String skillName){
        //binding.progressBar.setVisibility(View.VISIBLE);
        //binding.formBody.setVisibility(View.GONE);

        Skill skill = new Skill(skillName);

        LiveResponse createLiveResponse =  skillViewModel.create(skill);

        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    //binding.progressBar.setVisibility(View.GONE);
                    //binding.selectEmployeeSkillsBtn.callOnClick();
                    skillViewModel = new ViewModelProvider(getActivity()).get(SkillViewModel.class);

                    skillViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Skill>>() {

                        @Override
                        public void onChanged(List<Skill> skills) {
                            skillArrayList = new ArrayList<>();

                            skills.forEach(skill -> {
                                skillArrayList.add(skill);
                            });


                            skillHash.put(skillArrayList.get(skillArrayList.size()-1).getSkillName(), skillArrayList.get(skillArrayList.size()-1).getId());
                            List<CharSequence> list = new ArrayList<>(Arrays.asList(items));
                            list.add(skillArrayList.get(skillArrayList.size()-1).getSkillName());
                            items = list.toArray(new CharSequence[list.size()]);

                            boolean[] newArray = Arrays.copyOf(selectedItems, selectedItems.length + 1);
                            newArray[selectedItems.length] = false;

                            selectedItems = newArray;

                            //items = skillHash.keySet().toArray(new CharSequence[0]);
                            long[] newArraySkillsId = Arrays.copyOf(skillIds, skillIds.length + 1);
                            newArraySkillsId[skillIds.length] = skillHash.get(items[skillArrayList.size()-1]);
                            skillIds = newArraySkillsId;


                        }
                    });
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

    private boolean isValidEmailId(String email){

        return Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$").matcher(email).matches();
    }


    public Employee validate() {

        Employee addedEmployee;
        String employeeFirstName = binding.firstName.getText().toString().trim();
        String employeeLastName = binding.lastName.getText().toString().trim();
        String employeeEmail = binding.employeeEmail.getText().toString().trim();
        String employeePhone = binding.employeePhone.getText().toString().trim();
        String employeeFunction = binding.employeeFunction.getText().toString().trim();
        String employeeSkills = binding.employeeSkills.getText().toString().trim();

        boolean error = false ;

        if (employeeEmail.equals("")){
            binding.employeeEmail.setHint("Email Address is required ");
            binding.employeeEmail.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            error = true;
        }
        else if(!isValidEmailId(employeeEmail)){
            Toast.makeText(getContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
            error = true ;
        }

        if (employeeFirstName.equals("")) {
            binding.firstName.setHint(" Employee First Name is required ! ");
            binding.firstName.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            error = true ;
        }
        if (employeeLastName.equals("")) {

            binding.lastName.setHint(" Employee Last Name is required ! ");
            binding.lastName.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            error = true ;
        }

        if (employeePhone.equals("")) {

            binding.employeePhone.setHint(" Employee Phone is required ! ");
            binding.employeePhone.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            error = true ;
        }

        if (employeeFunction.equals("")) {

            binding.employeeFunction.setHint(" Employee Function is required ! ");
            binding.employeeFunction.setHintTextColor(getResources().getColor(R.color.design_default_color_error));
            error = true ;
        }


        if (error)
            return null;

        addedEmployee = new Employee(
                employeeFirstName,
                employeeLastName,
                employeeFunction,
                employeeEmail,
                employeePhone
        );

        return addedEmployee;
    }

}