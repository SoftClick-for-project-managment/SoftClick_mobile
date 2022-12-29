package com.job.softclick_mobile.ui.clients;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentClientDetailsBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.ui.tasks.DetailsTask;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;
import com.job.softclick_mobile.viewmodels.clients.IClientViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.io.IOException;

import retrofit2.HttpException;

public class ClientDetailsFragment extends Fragment  {
    private FragmentClientDetailsBinding binding;
    private Client client;

    private IClientViewModel clientViewModel ;
////////client///////////////////
    public ClientDetailsFragment() {

    }

    public static ClientDetailsFragment newInstance(String param1, String param2) {
        ClientDetailsFragment fragment = new ClientDetailsFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter,new Fragment()).commit() ;




        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        binding.firstNameValue.setText(client.getNom());
        binding.lastNameValue.setText(client.getPrenom());
        binding.emailValue.setText(client.getEmail());
        binding.phoneValue.setText(client.getTele());
        binding.companyNameValue.setText(client.getNomEntreprise());
        binding.cityValue.setText(client.getVille());
        binding.countryValue.setText(client.getPays());



        clientViewModel.getSingle(client.getId()).geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Object>() {
                    @Override
                    public void onChanged(Object o) {
                        Throwable error = (Throwable) o;
                        if (error instanceof HttpException) {
                            binding.backArrow.callOnClick();
                            Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof IOException) {

                        }
                        Log.d("ERR", error.getMessage());
                    }
                });

        clientViewModel.getSingle(client.getId()).gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Client>() {
            @Override
            public void onChanged(Client c) {
                client = c;
                refreshUi();
            }
        });

        if (binding.moreOptions != null) {
            binding.moreOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), binding.moreOptions);
                    popupMenu.getMenuInflater().inflate(R.menu.details_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            // Toast message on menu item clicked
                            Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                            switch (menuItem.getItemId()) {
                                case R.id.edit:

                                    Fragment fragment = new ClientFormFragment();

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("client", client);
                                    fragment.setArguments(bundle);

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.flContent, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    break;

                                case R.id.delete:
                                    AlertDialog diaBox = AskOption();
                                    diaBox.show();
                                    break;

                                default:
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

        if (binding.backArrow != null) {
            binding.backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment clientListFragment = new ClientListFragment();
                    FooterFragment footerFragment = new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, clientListFragment).commit();
                }
            });
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void refreshUi() {

        binding.detailsBody.setVisibility(View.VISIBLE);
        binding.firstNameValue.setText(client.getPrenom());
        binding.lastNameValue.setText(client.getNom());
        binding.emailValue.setText(client.getEmail());
        binding.phoneValue.setText(client.getTele());
        binding.companyNameValue.setText(client.getNomEntreprise());
        binding.cityValue.setText(client.getVille());
        binding.countryValue.setText(client.getPays());
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete this Client?")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteClient();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public void deleteClient(){
       //binding.progressBar.setVisibility(View.VISIBLE);
        //binding.formBody.setVisibility(View.GONE);
        LiveResponse createLiveResponse =  clientViewModel.delete((long) this.client.getId());
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    //binding.progressBar.setVisibility(View.GONE);
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
                    error.printStackTrace();
                    Toast.makeText(getContext(), "This screen is under maintenance", Toast.LENGTH_SHORT).show();
                } else if (error instanceof IOException) {

                }
                //binding.formBody.setVisibility(View.VISIBLE);
                //binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }

}