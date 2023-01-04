package com.job.softclick_mobile.ui.invoices;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.adapters.InvoiceListAdapter;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.databinding.FragmentEmployeeFormBinding;
import com.job.softclick_mobile.databinding.FragmentInvoiceFormBinding;
import com.job.softclick_mobile.models.Client;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.models.Project;
import com.job.softclick_mobile.repositories.clients.ClientRepository;
import com.job.softclick_mobile.ui.layout.FooterFragment;
import com.job.softclick_mobile.utils.LiveResponse;
import com.job.softclick_mobile.viewmodels.clients.ClientViewModel;
import com.job.softclick_mobile.viewmodels.clients.IClientViewModel;
import com.job.softclick_mobile.viewmodels.employees.EmployeeViewModel;
import com.job.softclick_mobile.viewmodels.employees.IEmployeeViewModel;
import com.job.softclick_mobile.viewmodels.invoices.IInvoiceViewModel;
import com.job.softclick_mobile.viewmodels.invoices.InvoiceViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.HttpException;
public class InvoiceFormFragment extends Fragment {
    private FragmentInvoiceFormBinding binding;
    private ActivityMenuBinding menuBinding;
    private ArrayList<Client> clientArrayList;
    private EditText date;
    private EditText total;
    private ImageView back;
    private TextView pagetitle;
    private TextView editbtn;
    private Invoice invoice;
    private IInvoiceViewModel invoiceViewModel;
    private AutoCompleteTextView Combo_project;
    private AutoCompleteTextView Combo_client;
    private ArrayList<Client> clientss;
    private IClientViewModel clientViewModel;
    private List<Project> projectss;
    private Hashtable<String,Integer> ClientHash;
    private List<String> clientName;
    private long selectedid;
    public InvoiceFormFragment() {
        // Required empty public constructor
    }
    public static InvoiceFormFragment newInstance(String param1, String param2) {
        InvoiceFormFragment fragment = new InvoiceFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            invoice = (Invoice) getArguments().getSerializable("invoice");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceFormBinding.inflate(inflater, container, false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();
        View view = binding.getRoot();
        invoiceViewModel = new ViewModelProvider(this).get(InvoiceViewModel.class);
        editbtn=binding.createBtn;
        pagetitle=binding.pageTitle;
        date = binding.date;
        total = binding.total;
        Combo_project = binding.projectCombo;
        Combo_client = binding.clientCombo;
        back = binding.imageView;
        binding.timedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),R.style.datetimepicker,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Set the selected date and time in a TextView

                                binding.timedate.setText(String.format("%d:%d", hourOfDay, minute));
                            }
                        },
                        hour, minute, false
                );
                timePickerDialog.show();
                timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),R.style.datetimepicker,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year+binding.timedate.getText());

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Invoice invoice=(Invoice) getArguments().getSerializable("invoice");
            editbtn.setText("Edit");
            pagetitle.setText("Invoice Edition");
            date.setText(invoice.getDate().toString());
            total.setText(invoice.getTotal());
//            Combo_project.setText(invoice.getProject().getNameProject().toString());
            Combo_client.setText(invoice.getClient().getNom().toString()+" "+invoice.getClient().getPrenom().toString());
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("invoice",invoice);
                    InvoiceDetailsFragment invoiceDetailsFragment = new InvoiceDetailsFragment();
                    invoiceDetailsFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, invoiceDetailsFragment).commit();
                }
            });
            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateInvoice((long) invoice.getId());
                }
            });


        } else {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InvoiceListFragment invoiceListFragment = new InvoiceListFragment();
                    FooterFragment footerFragment = new FooterFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, footerFragment).commit();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, invoiceListFragment).commit();
                }
            });
            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createInvoice();
                }
            });
        }
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.getAll().gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients)
            {
                clientArrayList=new ArrayList<>();
                clientArrayList.addAll(clients);
                HashTable();
            }
        });


        return binding.getRoot();

    }
    public void createInvoice(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);
        long id=ClientHash.get(Combo_client.getText().toString());
        Client selected=new Client();
        selected.setId(id);
       Invoice invoice = new Invoice(
               binding.date.getText().toString(),
               binding.total.getText().toString(),
               selected,
               null
        );

        System.out.println("Invoice ::: " + invoice.getTotal());


        LiveResponse createLiveResponse =  invoiceViewModel.create(invoice);
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
                binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }
    private void HashTable(){
        ClientHash=new Hashtable<String, Integer>();
        for (int i=0;i<clientArrayList.size();i++){
            ClientHash.put(clientArrayList.get(i).getNom().toString(),clientArrayList.get(i).getId().intValue());
        }
        Set<String> clientList = ClientHash.keySet();
        clientName=new ArrayList<>();
        clientName.addAll(clientList);
        for(int i=0;i<clientName.size();i++){
            System.out.println(clientName.get(i).toString());
        }
        ArrayAdapter<String> adapter_client = new ArrayAdapter<String>(getActivity(),
                R.layout.dropdown_item,clientName);
        Combo_client.setAdapter(adapter_client);
    }
    public void updateInvoice(Long key){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.formBody.setVisibility(View.GONE);
        long id=ClientHash.get(Combo_client.getText().toString());
        Client selected=new Client();
        selected.setId(id);
        Invoice invoice = new Invoice(
                binding.date.getText().toString(),
                binding.total.getText().toString(),
                selected,
                null
        );



        LiveResponse createLiveResponse =  invoiceViewModel.update(key, invoice);

        this.invoice = invoice;

        System.out.println("Invoice ::: " + invoice.getTotal());

        createLiveResponse.gettMutableLiveData().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                System.out.println("changed");
                if((Boolean) o == true ){
                    binding.progressBar.setVisibility(View.GONE);
                    back.callOnClick();
                    System.out.println("back called");
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
                binding.formBody.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                Log.d("ERR", error.getMessage());
            }
        });


    }
}