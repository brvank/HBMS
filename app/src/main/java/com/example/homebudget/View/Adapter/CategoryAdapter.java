package com.example.homebudget.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homebudget.Model.Category;
import com.example.homebudget.Util.Callbacks.CategoryCallback;
import com.example.homebudget.databinding.LayoutCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<Category> categories;
    private CategoryCallback categoryCallback;

    public CategoryAdapter(List<Category> categories, CategoryCallback categoryCallback){
        this.categories = categories;
        this.categoryCallback = categoryCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutCategoryBinding layoutCategoryBinding = LayoutCategoryBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(layoutCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        holder.layoutCategoryBinding.tvCategoryTitle.setText(categories.get(position).getName());
        holder.layoutCategoryBinding.tvCategoryInfo.setText(categories.get(position).getInfo());
        holder.layoutCategoryBinding.llParentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryCallback.callback(categories.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LayoutCategoryBinding layoutCategoryBinding;
        public ViewHolder(@NonNull LayoutCategoryBinding layoutCategoryBinding) {
            super(layoutCategoryBinding.getRoot());
            this.layoutCategoryBinding = layoutCategoryBinding;
        }
    }

    public void setValues(List<Category> categories){
        this.categories = categories;
    }
}
