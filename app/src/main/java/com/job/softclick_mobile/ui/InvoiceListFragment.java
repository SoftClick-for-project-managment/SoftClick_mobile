package com.job.softclick_mobile.ui;

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
import com.job.softclick_mobile.ui.contracts.InvoiceListInterface;
import com.job.softclick_mobile.models.Invoice;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceListFragment extends Fragment implements InvoiceListInterface {

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

        invoices = new ArrayList<>();
        invoices.add(new Invoice("12-12-2019", "10000"));
        invoices.add(new Invoice("12-23-2020", "500000"));
        invoices.add(new Invoice("1-12-2021", "100760"));
        invoices.add(new Invoice("11-12-2020", "1000.00"));
        invoices.add(new Invoice("11-1-2022", "50.00"));
        invoices.add(new Invoice("3-3-2019", "100.00"));
        invoices.add(new Invoice("1-8-2022", "8000.00"));
        invoices.add(new Invoice("15-11-2022", "300.00"));
        invoices.add(new Invoice("19-12-2021", "1000.00"));
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