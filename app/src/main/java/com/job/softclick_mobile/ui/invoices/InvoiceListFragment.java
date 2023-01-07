package com.job.softclick_mobile.ui.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.InvoiceListAdapter;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceListFragment extends Fragment implements RecyclerViewHandler {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private List<Invoice> invoices;
    private FloatingActionButton addButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InvoiceListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceListFragment newInstance(String param1, String param2) {
        InvoiceListFragment fragment = new InvoiceListFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoice_list, container, false);
        recyclerView = view.findViewById(R.id.invoiceListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Client client=new Client("Mable","Murphy","jayda.legros@rau.com      ","+1 (754) 958-2911", "Zulauf, Tillman and Beer","Spinkamouth","KY");
        Project project=new Project("Violance", "projet detection de violance based A I", 500000d);
        Project project2=new Project("gestion de dossier", "stocker les dossier legaliseés et les chercher par une simple scan intelligent", 600000d);
        invoices = new ArrayList<>();
        SimpleDateFormat formater = null;

        Date aujourdhui = new Date();
        formater = new SimpleDateFormat("dd-MM-yy");
        invoices.add(new Invoice(formater.format(aujourdhui) , "10000",client,project));
        invoices.add(new Invoice(formater.format(aujourdhui), "500000",client,project2));
        invoices.add(new Invoice("12-5-2019", "7000",client,project));
        invoices.add(new Invoice("12-12-2022", "500000",client,project2));
        invoices.add(new Invoice("12-3-2023" , "200",client,project));
        invoices.add(new Invoice("1-1-2018", "50",client,project2));
        recyclerView.setAdapter(new InvoiceListAdapter(invoices, this));
        addButton = this.getActivity().findViewById(R.id.addButton);
        if (addButton != null) {
            addButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, (Fragment) InvoiceFormFragment.class.newInstance()).commit();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return view;

    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("invoice", invoices.get(position));
        InvoiceDetailsFragment invoiceDetailsFragment = new InvoiceDetailsFragment();
        invoiceDetailsFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, invoiceDetailsFragment).commit();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}