package com.rifalcompany.danspro.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rifalcompany.danspro.R;
import com.rifalcompany.danspro.activity.DetailActivity;
import com.rifalcompany.danspro.api.jobModel;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {
    private List<jobModel> items;
    private String fulltime;
    private Context context;

    public JobAdapter(List<jobModel> items, String fulltime, Context context) {
        this.items = items;
        this.fulltime = fulltime;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        jobModel item = items.get(position);
        String fulltimeFromAdapt = fulltime.toLowerCase();
        String fulltimeFromApi = item.getType().toLowerCase();

        holder.itemView.setVisibility(View.VISIBLE);
        holder.tvTitle.setText(item.getTitle());
        holder.tvCompany.setText(String.valueOf(item.getCompany()));
        holder.tvLocation.setText(String.valueOf(item.getLocation()));

        if (!fulltimeFromAdapt.equals(fulltimeFromApi)){
            holder.itemView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("id_job", item.getId());

            Intent i = new Intent(context, DetailActivity.class);
            i.putExtras(bundle);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvCompany;
        public TextView tvLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCompany = itemView.findViewById(R.id.tv_company);
            tvLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}

