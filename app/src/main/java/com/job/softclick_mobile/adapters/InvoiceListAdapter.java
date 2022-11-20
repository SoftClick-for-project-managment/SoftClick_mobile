package com.job.softclick_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Invoice;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;

import java.util.List;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.InvoiceListViewHolder> {
    List<Invoice> invoices;
    private final RecyclerViewHandler invoiceListInterface;
    public InvoiceListAdapter(List<Invoice> invoices, RecyclerViewHandler invoiceListInterface) {
        this.invoices = invoices;
        this.invoiceListInterface=invoiceListInterface;
    }
    @NonNull
    @Override
    public InvoiceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_card,parent,false);

        return new InvoiceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceListViewHolder holder, int position) {
        holder.date.setText(invoices.get(position).getDate());
        holder.total.setText(invoices.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
       return invoices.size();
    }
    class InvoiceListViewHolder extends RecyclerView.ViewHolder
    {
        TextView date,total;
        public InvoiceListViewHolder(@NonNull View itemView)
        {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            total = itemView.findViewById(R.id.total);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(InvoiceListAdapter.this.invoiceListInterface !=null){
                        int pos=getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            InvoiceListAdapter.this.invoiceListInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

    }
}
