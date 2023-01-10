package com.job.softclick_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Employee;
import com.job.softclick_mobile.models.Team;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Member_list_Adapter extends RecyclerView.Adapter<Member_list_Adapter.MemberViewHolder>{

    private Set<Employee> Members;
    private final RecyclerViewHandler recyclerViewHandler;


    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        public TextView namemember;

        public MemberViewHolder(@NonNull View itemView, RecyclerViewHandler recyclerViewHandler) {
            super(itemView);
            namemember= itemView.findViewById(R.id.membername);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewHandler != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            recyclerViewHandler.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public Member_list_Adapter(Set<Employee> Members, RecyclerViewHandler recyclerViewHandler) {
        this.Members = Members;
        this.recyclerViewHandler = recyclerViewHandler;
    }


    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        MemberViewHolder evh = new MemberViewHolder(v, recyclerViewHandler);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        List<Employee> listMembers = new ArrayList<>(Members);
            holder.namemember.setText(listMembers.get(position).getEmployeeFirstName()+listMembers.get(position).getEmployeeLastName());


    }

    @Override
    public int getItemCount() {
        return Members.size();
    }
}
