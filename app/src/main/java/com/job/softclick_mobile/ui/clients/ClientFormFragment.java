package com.job.softclick_mobile.ui.clients;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentClientFormBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.ui.tasks.DetailsTask;
import com.job.softclick_mobile.ui.tasks.TaskForm;
import com.job.softclick_mobile.ui.tasks.TaskList;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;
import com.job.softclick_mobile.viewmodels.clients.IClientViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.io.IOException;

import retrofit2.HttpException;

public class ClientFormFragment extends Fragment {
    private FragmentClientFormBinding binding;
    private Client client;

    IClientViewModel clientViewModel;

    public ClientFormFragment() {
        // Required empty public constructor
    }

    public static ClientFormFragment newInstance(String param1, String param2) {
        ClientFormFragment fragment = new ClientFormFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            client = (Client) getArguments().getSerializable("client");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        // Inflate the layout for this fragment
        binding = FragmentClientFormBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);


        if(client != null) {
            binding.subheaderTitle.setText("Client Edition");
            binding.createClientBtn.setText("Edit");
            binding.firstName.setText(client.getNom());
            binding.lastName.setText(client.getPrenom());
            binding.email.setText(client.getEmail());
            binding.phone.setText(client.getTele());
            binding.companyName.setText(client.getNomEntreprise());
            binding.city.setText(client.getVille());
            binding.country.setText(client.getPays());

            binding.backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClientDetailsFragment employeeDetailsFragment = new ClientDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("client", client);
                    employeeDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,employeeDetailsFragment).commit();
                }
            });

            binding.createClientBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateClient((long) client.getId());
                }
            });

        }
        else{
            binding.backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClientListFragment ClientList =new ClientListFragment();
                    FooterFragment footerFragment=new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,ClientList).commit();
                }
            });

            binding.createClientBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createClient();
                }
            });

        }





        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;

        return view;

    }


    public void createClient(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);
        Client client = new Client(
                binding.firstName.getText().toString(),
                binding.lastName.getText().toString(),
                binding.email.getText().toString(),
                binding.phone.getText().toString(),
                binding.companyName.getText().toString(),
                binding.city.getText().toString(),
                binding.country.getText().toString()
        );

        LiveResponse createLiveResponse =  clientViewModel.create(client);
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    Toast.makeText(getContext(), "Client created successfully", Toast.LENGTH_SHORT).show();
                    binding.progressBar.setVisibility(View.GONE);
                    binding.backArrow.callOnClick();
                }
            }
        });


        createLiveResponse.geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                Throwable error = (Throwable) o;
                if (error instanceof HttpException) {
                    Log.d("DEBUG", error.getMessage());
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {
                    Toast.makeText(getContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                error.printStackTrace();
                Log.d("ERR", error.getMessage());
            }
        });

    }

    public void updateClient(Long key){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);

        Client client = new Client(
                binding.firstName.getText().toString(),
                binding.lastName.getText().toString(),
                binding.email.getText().toString(),
                binding.phone.getText().toString(),
                binding.companyName.getText().toString(),
                binding.city.getText().toString(),
                binding.country.getText().toString()
        );



        LiveResponse createLiveResponse =  clientViewModel.update(key, client);

        this.client = client;

       /// System.out.println("Employee ::: " + employee.getEmployeeEmail());

        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                System.out.println("changed");
                if((Boolean) o == true ){
                    Toast.makeText(getContext(), "Client updated successfully", Toast.LENGTH_SHORT).show();

                    binding.progressBar.setVisibility(View.GONE);
                    binding.backArrow.callOnClick();
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