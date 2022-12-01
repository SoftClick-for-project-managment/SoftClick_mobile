package com.job.softclick_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.models.Member;

import java.util.ArrayList;

public class Member_list_Adapter extends RecyclerView.Adapter<Member_list_Adapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Member_image;
        TextView Member_name;
        public ViewHolder(View itemView){
            super(itemView);
            Member_image=itemView.findViewById(R.id.imagemember);
            Member_name=itemView.findViewById(R.id.membername);
        }
    }
    private Context context;
    ArrayList<Member> MembersArrayList;
    public Member_list_Adapter(Context ctx, ArrayList<Member> MembersArrayList){
        this.context=ctx;
        this.MembersArrayList=MembersArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.member_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Member members=MembersArrayList.get(position);
        holder.Member_name.setText(members.member_name);
        holder.Member_image.setImageResource(members.member_image);
    }

    @Override
    public int getItemCount() {
        return MembersArrayList.size();
    }
}



