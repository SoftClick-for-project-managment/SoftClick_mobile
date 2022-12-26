package com.job.softclick_mobile.ui.clients;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ClientListAdapter;
import com.job.softclick_mobile.adapters.ItemAdapter;
import com.job.softclick_mobile.models.Task;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.ui.tasks.TaskForm;
import com.job.softclick_mobile.ui.tasks.TaskList;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;
import com.job.softclick_mobile.viewmodels.clients.IClientViewModel;
import com.job.softclick_mobile.viewmodels.task.ITaskViewModel;
import com.job.softclick_mobile.viewmodels.task.TaskViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClientListFragment extends Fragment implements RecyclerViewHandler<Client> {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private ClientListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private IClientViewModel clientViewModel;
    private List<Client> clients =new ArrayList<>();
    private ArrayList<Client> clientArrayList;

    private ProgressBar progressBar;


    public ClientListFragment() {
        // Required empty public constructor
    }

    public static ClientListFragment newInstance(String param1, String param2) {
        ClientListFragment fragment = new ClientListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CLIENT_LIST_FRAGMENT", "onCreate got called");
        if (getArguments() != null) {
//            mParam1 = getArguments().getString("ARG_PARAM1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CLIENT_LIST_FRAGMENT", "onCreateView got called");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);
        recyclerView = view.findViewById(R.id.clientListRecyclerView);
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        clientViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });


        clientViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients)
            {
                clientArrayList = new ArrayList<>();
                AtomicReference<ArrayList<Client>> sClientList = new AtomicReference<>(new ArrayList<>());
                clients.forEach(client -> {
                    clientArrayList.add(client);
                });
                progressBar.setVisibility(View.INVISIBLE);
                refreshUi();
            }
        });


        addButton = this.getActivity().findViewById(R.id.addButton);
        if(addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent,(Fragment) ClientFormFragment.class.newInstance()).commit() ;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }





        return view;
    }

    private void refreshUi(){
        Toast.makeText(getActivity(), "refresh uui : " , Toast.LENGTH_SHORT).show();

        adapter = new ClientListAdapter( clientArrayList, this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position) {
        Client client = clientArrayList.get(position);

        Fragment fragment = new ClientDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("client", client);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}