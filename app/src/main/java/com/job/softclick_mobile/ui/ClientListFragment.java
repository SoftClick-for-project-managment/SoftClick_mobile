package com.job.softclick_mobile.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.ClientListAdapter;
import com.job.softclick_mobile.models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private List<Client> clients;

    public ClientListFragment() {
        // Required empty public constructor
    }

    public static ClientListFragment newInstance(String param1, String param2) {
        Log.d("CLIENT_LIST_FRAGMENT", "newInstance got called");
        ClientListFragment fragment = new ClientListFragment();
        Bundle args = new Bundle();
//        args.putString("ARG_PARAM1", param1);
//        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CLIENT_LIST_FRAGMENT", "onCreate got called");
        if (getArguments() != null) {
            mParam1 = getArguments().getString("ARG_PARAM1");
            mParam2 = getArguments().getString("ARG_PARAM2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CLIENT_LIST_FRAGMENT", "onCreateView got called");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);
        recyclerView = view.findViewById(R.id.clientListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clients =new ArrayList<>();
        clients.add(new Client("Mable","Murphy","jayda.legros@rau.com      ","+1 (754) 958-2911", "Zulauf, Tillman and Beer","Spinkamouth","KY"));
        clients.add(new Client("Millie","Stracke","sbuckridge@runte.com      ","+1-530-492-6944", "Morissette Ltd","Lubowitzmouth","LU"));
        clients.add(new Client("Michale","Bayer","sydnee.kutch@gutmann.com  ","(843) 605-7918", "Keebler, Satterfield and Bernier","Carterfurt     ","BY"));
        clients.add(new Client("Griffin","Spencer  ","feeney.wendell@reichel.com","+1-940-539-7397", "Wisozk-Bayer","Boyerberg","CM"));
        clients.add(new Client("Odessa","Langworth","vkunze@mccullough.com     ","214-455-0227     ", "Brakus-Ruecker","East Elissafort","BI"));
        clients.add(new Client("Dariana","Hahn     ","uvandervort@keebler.info  ","1-915-624-0388   ", "Donnelly Group","Deshaunside    ","IL"));
        clients.add(new Client("Lilliana","Treutel  ","baby13@boehm.net          ","+1.754.208.4853  ", "Moen, Powlowski and Orn","Lake Herman    ","GW"));
        clients.add(new Client("Waylon","Abshire  ","savanna06@reilly.net      ","+1-828-433-3907  ", "Kutch, Torphy and Cremin","Casimirmouth   ","BQ"));
        clients.add(new Client("Joany","Gerhold  ","pamela.boehm@ward.com     ","240.523.7261     ", "Quitzon PLC","Shanahanview   ","CA"));

        recyclerView.setAdapter(new ClientListAdapter(clients));

        return view;

    }
}