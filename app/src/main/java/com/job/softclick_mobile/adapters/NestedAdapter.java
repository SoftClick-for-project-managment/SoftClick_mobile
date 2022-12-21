package com.job.softclick_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.job.softclick_mobile.R;
import com.job.softclick_mobile.ui.contracts.RecyclerViewHandler;
import com.job.softclick_mobile.models.Task;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder>{

    private List<Task> mList;
    private RecyclerViewHandler recyclerViewHandler;

    public NestedAdapter(List<Task> mList, RecyclerViewHandler recyclerViewHandler){
        this.mList = mList;
        this.recyclerViewHandler = recyclerViewHandler;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item , parent , false);
        return new NestedViewHolder(view, recyclerViewHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private TextView mTv;
        public NestedViewHolder(@NonNull View itemView, RecyclerViewHandler recyclerViewHandler) {
            super(itemView);
            mTv = itemView.findViewById(R.id.nestedItemTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewHandler != null){// chno Glti
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            recyclerViewHandler.onItemClick(mList, position);
                        }
                    }
                }
            });
        }
    }
}
