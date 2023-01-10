package com.job.softclick_mobile.ui.invoices;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.InvoiceListAdapter;
import com.job.softclick_mobile.models.Expense;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.viewmodels.ActivitySharedViewModel;
import com.job.softclick_mobile.viewmodels.invoices.IInvoiceViewModel;
import com.job.softclick_mobile.viewmodels.invoices.InvoiceViewModel;

import java.util.ArrayList;
import java.util.List;

public class InvoiceListFragment extends Fragment implements RecyclerViewHandler {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private List<Invoice> invoices;
    private InvoiceListAdapter adapter;
    private FloatingActionButton addButton;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Invoice> invoiceArrayList;
    private IInvoiceViewModel invoiceViewModel;
    private ProgressBar progressBar;
    private ActivitySharedViewModel activitySharedViewModel;

    public InvoiceListFragment() {
        // Required empty public constructor
    }

    public static InvoiceListFragment newInstance(String param1, String param2) {
        InvoiceListFragment fragment = new InvoiceListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activitySharedViewModel =  new ViewModelProvider(getActivity()).get(ActivitySharedViewModel.class);
        activitySharedViewModel.getSearchInvoice().observe(getViewLifecycleOwner(), new Observer<Invoice>() {
            @Override
            public void onChanged(Invoice invoice) {

                searchInvoice(invoice);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoice_list, container, false);
        recyclerView = view.findViewById(R.id.invoiceListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        invoiceViewModel.getAll().geteMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                progressBar.setVisibility(View.INVISIBLE);
                Throwable error = (Throwable) o;
                Log.d("ERR", error.getMessage());
            }
        });

        invoiceViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Invoice>>() {

            @Override
            public void onChanged(List<Invoice> invoices) {
                invoiceArrayList = new ArrayList<>();
                invoices.forEach(invoice -> {
                    invoiceArrayList.add(invoice);
                });

                progressBar.setVisibility(View.INVISIBLE);
                refreshUi();
            }
        });


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
    private void refreshUi(){
        adapter = new InvoiceListAdapter(invoiceArrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Invoice invoice = invoiceArrayList.get(position);
        Fragment fragment = new InvoiceDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("invoice", invoice);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void searchInvoice(Invoice invoice){
        invoiceViewModel.search(invoice).gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Invoice>>() {
            @Override
            public void onChanged(List<Invoice> invoices) {
                invoiceArrayList = new ArrayList<>();
                invoices.forEach(invoice -> {
                    invoiceArrayList.add(invoice);
                });
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                refreshUi();
                //mainRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}