package com.example.homebudget.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebudget.Model.Plan;
import com.example.homebudget.databinding.LayoutPlanBinding;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    public List<Plan> plans;

    public PlanAdapter(List<Plan> plans){
        this.plans = plans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutPlanBinding layoutPlanBinding = LayoutPlanBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(layoutPlanBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.layoutPlanBinding.tvPlanTitle.setText(plans.get(position).getName());
        holder.layoutPlanBinding.tvPlanInfo.setText(plans.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutPlanBinding layoutPlanBinding;
        public ViewHolder(@NonNull LayoutPlanBinding layoutPlanBinding) {
            super(layoutPlanBinding.getRoot());
            this.layoutPlanBinding = layoutPlanBinding;
        }
    }
}
