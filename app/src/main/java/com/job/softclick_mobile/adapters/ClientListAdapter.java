package com.job.softclick_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientListViewHolder> {
    List<Client> clients;

    public ClientListAdapter(List<Client> clients) {
        this.clients = clients;
    }

    @NonNull
    @Override
    public ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.client_card,parent,false);
        return new ClientListViewHolder(view);
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
        public ClientListViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            nomEntreprise = itemView.findViewById(R.id.nomEntreprise);
            ville = itemView.findViewById(R.id.ville);
            pays = itemView.findViewById(R.id.pays);
        }
    }
}
