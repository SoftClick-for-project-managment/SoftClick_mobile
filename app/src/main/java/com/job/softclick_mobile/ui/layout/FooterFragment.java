package com.job.softclick_mobile.ui.layout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.job.softclick_mobile.R;
import com.job.softclick_mobile.ui.clients.ClientListFragment;
import com.job.softclick_mobile.ui.employees.EmployeeListFragment;
import com.job.softclick_mobile.ui.expense.ExpensesListFragment;
import com.job.softclick_mobile.ui.invoices.InvoiceListFragment;
import com.job.softclick_mobile.ui.projectFragments.ListProjectsFragment;
import com.job.softclick_mobile.ui.team.TeamListFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FooterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FooterFragment extends Fragment {
    private BottomAppBar mBottomAppBar;
    private FrameLayout mBottomSheet;
    private BottomSheetBehavior mbBottomSheetBehavior;
    private Class fragmentClass;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FooterFragment() {
        // Required empty public constructor
    }
    public FooterFragment(Class fragmentClass) {
        // Required empty public constructor
        this.fragmentClass=fragmentClass;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FooterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FooterFragment newInstance(String param1, String param2) {
        FooterFragment fragment = new FooterFragment();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.footer_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(mbBottomSheetBehavior.getState() ==mbBottomSheetBehavior.STATE_HIDDEN ) {
                mbBottomSheetBehavior.setState(mbBottomSheetBehavior.STATE_EXPANDED);
            }else if(mbBottomSheetBehavior.getState() ==mbBottomSheetBehavior.STATE_EXPANDED){
                mbBottomSheetBehavior.setState(mbBottomSheetBehavior.STATE_HIDDEN);
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_footer, container, false);
        mBottomAppBar= view.findViewById(R.id.bottomAppBar);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if(fragmentClass == ListProjectsFragment.class){
            // do something with f

            fragmentManager.beginTransaction().replace(R.id.bottom_search_lContent,new ProjectSearchFragment()  ).commit();

         }else if(fragmentClass == EmployeeListFragment.class){
            fragmentManager.beginTransaction().replace(R.id.bottom_search_lContent, new EmployeSearchFragment()).commit();
        }else if(fragmentClass == ClientListFragment.class){
            fragmentManager.beginTransaction().replace(R.id.bottom_search_lContent, new ClientSearchFragment()).commit();
        }
        else if(fragmentClass == TeamListFragment.class){
            fragmentManager.beginTransaction().replace(R.id.bottom_search_lContent,new TeamSearchFragment()).commit();
        }
        else if(fragmentClass == InvoiceListFragment.class){
            fragmentManager.beginTransaction().replace(R.id.bottom_search_lContent,new InvoiceSearchFragment()).commit();
        }
        else if(fragmentClass == ExpensesListFragment.class){
            fragmentManager.beginTransaction().replace(R.id.bottom_search_lContent,new ExpenseSearchFragment()).commit();
        }
        mBottomSheet = view.findViewById(R.id.bottom_search_lContent);


        mbBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mbBottomSheetBehavior.setState(mbBottomSheetBehavior.STATE_HIDDEN);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mBottomAppBar);
        mBottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);

        mBottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setHasOptionsMenu(true);


        return view;
    }
}