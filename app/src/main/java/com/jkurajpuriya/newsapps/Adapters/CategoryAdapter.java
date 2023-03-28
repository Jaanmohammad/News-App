package com.jkurajpuriya.newsapps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jkurajpuriya.newsapps.Model.CategoryModel;
import com.jkurajpuriya.newsapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryModel> categoryModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoryModel> categoryModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModels = categoryModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel categoryModel =categoryModels.get(position);
        holder.categoryTextView.setText(categoryModel.getCategory());
        Picasso.get().load(categoryModel.getCategoryImageUrl()).into(holder.categoryImage);

        holder.itemView.setOnClickListener(v -> {
            categoryClickInterface.onCategoryClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTextView;
        ImageView categoryImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage=itemView.findViewById(R.id.categoryImage);
            categoryTextView=itemView.findViewById(R.id.categoryTextView);
        }
    }
}
