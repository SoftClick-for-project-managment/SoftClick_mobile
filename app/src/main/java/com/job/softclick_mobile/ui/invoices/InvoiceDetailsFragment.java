package com.job.softclick_mobile.ui.invoices;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.databinding.FragmentInvoiceDetailsBinding;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.ui.layout.FooterFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvoiceDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvoiceDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView date;
    private TextView total;
    private ImageView back;
    private ImageView more;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentInvoiceDetailsBinding binding;

    public InvoiceDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InvoiceDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InvoiceDetailsFragment newInstance(String param1, String param2) {
        InvoiceDetailsFragment fragment = new InvoiceDetailsFragment();
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

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getContext())
// set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
//your deleting code
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
                                Bundle bundle = new Bundle();
                                Invoice invoice = new Invoice(date.getText().toString(), total.getText().toString());
                                bundle.putSerializable("invoice", invoice);
                                InvoiceFormFragment invoiceFormFragment = new InvoiceFormFragment();
                                invoiceFormFragment.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, invoiceFormFragment).commit();
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
        date = binding.date;
        total = binding.total;
        Invoice invoice = (Invoice) getArguments().getSerializable("invoice");
        String dataDate = invoice.getDate();
        date.setText(dataDate);
        String dataTotal = invoice.getTotal();
        total.setText(dataTotal);
        back = binding.imageView;
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
}