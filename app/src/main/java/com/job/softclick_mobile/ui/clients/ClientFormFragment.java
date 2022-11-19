package com.job.softclick_mobile.ui.clients;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentClientFormBinding;
import com.job.softclick_mobile.models.Client;

public class ClientFormFragment extends Fragment {
    private FragmentClientFormBinding binding;

    public ClientFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClientFormBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.createClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createClient(view);
            }
        });

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fContentFooter, new Fragment())
                .commit();

        return view;
    }

    public void createClient(View view){
        Client client = new Client(
                binding.firstName.getText().toString(),
                binding.lastName.getText().toString(),
                binding.email.getText().toString(),
                binding.phone.getText().toString(),
                binding.companyName.getText().toString(),
                binding.city.getText().toString(),
                binding.country.getText().toString()
        );

        if (isValid(client)) {
            Toast.makeText(getActivity().getApplicationContext(), "Client is valid", Toast.LENGTH_SHORT).show();
            // code to create client
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Some field are not valid", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid(Client client){
        if ( client.getNom().equals("")
                || client.getPrenom().equals("")
                || client.getEmail().equals("")
                || client.getTele().equals("")
                || client.getVille().equals("")
                || client.getPays().equals("") ) {
            return false;
        } else {
            return true;
        }
    }
}