package com.job.softclick_mobile.ui.invoices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentInvoiceDetailsBinding;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.ui.clients.ClientDetailsFragment;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.ui.projectFragments.DetailProjectFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.invoices.IInvoiceViewModel;
import com.job.softclick_mobile.viewmodels.invoices.InvoiceViewModel;

import java.io.IOException;

import retrofit2.HttpException;

public class InvoiceDetailsFragment extends Fragment {
    private TextView date;
    private TextView total;
    private TextView client;
    private TextView project;
    private ImageView back;
    private ImageView more;
    private FragmentInvoiceDetailsBinding binding;
    private Invoice invoice;
    private IInvoiceViewModel invoiceViewModel;

    public InvoiceDetailsFragment() {
        // Required empty public constructor
    }
    public static InvoiceDetailsFragment newInstance(String param1, String param2) {
        InvoiceDetailsFragment fragment = new InvoiceDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            invoice=(Invoice) getArguments().getSerializable("invoice");
            System.out.println(invoice.toString());
        }
    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteInvoice();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        binding = FragmentInvoiceDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        date = binding.date;
        total = binding.total;
        client=binding.client;
        project=binding.project;
        project.setText(invoice.getProject().getNameProject().toString());
        String dataDate = invoice.getDate().toString();
        date.setText(dataDate);
        String dataTotal = invoice.getTotal();
        total.setText(dataTotal);
//        String projectname=invoice.getProject().getNameProject().toString();
     //   project.setText(projectname);
        String clientname=invoice.getClient().getNom()+" "+invoice.getClient().getPrenom();
        client.setText(clientname);
        back = binding.imageView;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();
        more = binding.moreOptions;
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), more);
                popupMenu.getMenuInflater().inflate(R.menu.details_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit:
                                Fragment fragment = new InvoiceFormFragment();

                                Bundle bundle = new Bundle();
                                bundle.putSerializable("invoice", invoice);
                                fragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.flContent, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;

                            case R.id.delete:
                                Toast.makeText(getActivity(), "Under Construction ", Toast.LENGTH_LONG).show();
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
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ClientDetailsFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("client", invoice.getClient());
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DetailProjectFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("project", invoice.getProject());
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceListFragment invoiceListFragment = new InvoiceListFragment();
                FooterFragment footerFragment = new FooterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, invoiceListFragment).commit();
            }
        });
        return view;
    }
    public void deleteInvoice(){
        binding.progressBar.setVisibility(View.VISIBLE);
        //binding.formBody.setVisibility(View.GONE);


        System.out.println("Invoice ::: " + this.invoice.getTotal());

        LiveResponse createLiveResponse =  invoiceViewModel.delete((long) this.invoice.getId());
        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    back.callOnClick();
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
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }
}

