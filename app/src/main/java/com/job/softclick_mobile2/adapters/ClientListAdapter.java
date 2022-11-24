package com.job.softclick_mobile2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile2.R;
import com.job.softclick_mobile2.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile2.models.Client;

import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientListViewHolder> {
    private List<Client> clients;
    private final RecyclerViewHandler recyclerViewHandler;

    public ClientListAdapter(List<Client> clients, RecyclerViewHandler recyclerViewHandler) {
        this.clients = clients;
        this.recyclerViewHandler = recyclerViewHandler;
    }

    @NonNull
    @Override
    public ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.client_card,parent,false);
        return new ClientListViewHolder(view, recyclerViewHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientListViewHolder holder, int position)
    {
        holder.nom.setText(clients.get(position).getNom());
        holder.prenom.setText(clients.get(position).getPrenom());
        holder.nomEntreprise.setText(clients.get(position).getNomEntreprise());
        holder.ville.setText(clients.get(position).getVille());
        holder.pays.setText(clients.get(position).getPays());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    class ClientListViewHolder extends RecyclerView.ViewHolder
    {
        TextView nom, prenom, nomEntreprise, ville, pays;
        public ClientListViewHolder(@NonNull View itemView, RecyclerViewHandler rvh)
        {
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            nomEntreprise = itemView.findViewById(R.id.nomEntreprise);
            ville = itemView.findViewById(R.id.ville);
            pays = itemView.findViewById(R.id.pays);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rvh != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            rvh.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
