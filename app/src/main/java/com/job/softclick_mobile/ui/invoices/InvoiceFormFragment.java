package com.job.softclick_mobile.ui.invoices;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.ActivityMenuBinding;
import com.job.softclick_mobile.databinding.FragmentInvoiceFormBinding;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.ui.layout.FooterFragment;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceFormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentInvoiceFormBinding binding;
    private ActivityMenuBinding menuBinding;
    private EditText date;
    private EditText total;
    private ImageView back;
    private TextView pagetitle;
    private TextView editbtn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InvoiceFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceFormFragment newInstance(String param1, String param2) {
        InvoiceFormFragment fragment = new InvoiceFormFragment();
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
        binding = FragmentInvoiceFormBinding.inflate(inflater, container, false);
        //set variables in Binding

        //View view = inflater.inflate(R.layout.fragment_employee_form, container, false);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fContentFooter, new Fragment()).commit();
        //return inflater.inflate(R.layout.fragment_employee_form, container, false);
        //return view;
        //
        editbtn=binding.createBtn;
        pagetitle=binding.pageTitle;
        date = binding.date;
        total = binding.total;
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        back = binding.imageView;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Invoice invoice=(Invoice) getArguments().getSerializable("invoice");
            editbtn.setText("Edit");
            pagetitle.setText("Invoice Edition");
            date.setText(invoice.getDate());
            total.setText(invoice.getTotal());
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
        }
        return binding.getRoot();

    }
}