package com.example.homebudget.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebudget.Model.Plan;
import com.example.homebudget.Util.Callbacks.PlanCallback;
import com.example.homebudget.databinding.LayoutPlanBinding;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<Plan> plans;
    private PlanCallback planCallback;

    public PlanAdapter(List<Plan> plans, PlanCallback planCallback){
        this.plans = plans;
        this.planCallback = planCallback;
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
        final int pos = position;
        holder.layoutPlanBinding.tvPlanTitle.setText(plans.get(position).getName());
        holder.layoutPlanBinding.tvPlanInfo.setText(plans.get(position).getInfo());
        holder.layoutPlanBinding.llParentPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planCallback.callback(plans.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LayoutPlanBinding layoutPlanBinding;
        public ViewHolder(@NonNull LayoutPlanBinding layoutPlanBinding) {
            super(layoutPlanBinding.getRoot());
            this.layoutPlanBinding = layoutPlanBinding;
        }
    }

    public void setValues(List<Plan> plans){
        this.plans = plans;
    }
}
