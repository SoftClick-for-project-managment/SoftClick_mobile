package com.job.softclick_mobile.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.job.softclick_mobile.databinding.FragmentClientDetailsBinding;
import com.job.softclick_mobile.models.Client;

public class ClientDetailsFragment extends Fragment  {
    private FragmentClientDetailsBinding binding;
    private Client client;

    public ClientDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.firstNameValue.setText(client.getPrenom());
        binding.lastNameValue.setText(client.getNom());
        binding.emailValue.setText(client.getEmail());
        binding.phoneValue.setText(client.getTele());
        binding.companyNameValue.setText(client.getNomEntreprise());
        binding.cityValue.setText(client.getVille());
        binding.countryValue.setText(client.getPays());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            client = (Client) getArguments().getSerializable("client");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityClientDetailsBinding.inflate(getLayoutInflater());
//
//        View view = binding.getRoot();
//        setContentView(view);
//
//        Intent intent = getIntent();
//        Client client = (Client) intent.getSerializableExtra("client");
//        binding.firstNameValue.setText(client.getPrenom());
//        binding.lastNameValue.setText(client.getNom());
//        binding.emailValue.setText(client.getEmail());
//        binding.phoneValue.setText(client.getTele());
//        binding.companyNameValue.setText(client.getNomEntreprise());
//        binding.cityValue.setText(client.getVille());
//        binding.countryValue.setText(client.getPays());
//    }
}