package com.example.homebudget.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebudget.Model.Item;
import com.example.homebudget.databinding.LayoutItemBinding;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> items;
    private final Context context;

    public ItemAdapter(Context context, List<Item> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutItemBinding layoutItemBinding = LayoutItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(layoutItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.layoutItemBinding.tvItemName.setText(item.getName());
        holder.layoutItemBinding.tvItemInfo.setText(item.getInfo());
        holder.layoutItemBinding.tvCurrent.setText(String.valueOf(item.getCurrent()));
        holder.layoutItemBinding.tvPrevious.setText(String.valueOf(item.getPrevious()));
        holder.layoutItemBinding.tvChange.setText(String.valueOf(item.getCurrent() - item.getPrevious()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LayoutItemBinding layoutItemBinding;
        public ViewHolder(@NonNull LayoutItemBinding layoutItemBinding) {
            super(layoutItemBinding.getRoot());
            this.layoutItemBinding = layoutItemBinding;
        }
    }

    public void setValues(List<Item> items){
        this.items = items;
    }
}
