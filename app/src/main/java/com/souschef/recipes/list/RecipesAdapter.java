package com.souschef.recipes.list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.souschef.R;
import com.souschef.recipes.detail.RecipeDetail;
import com.souschef.recipes.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();

    private List<Recipe> recipiesList = new ArrayList<>();

    RecipesListActivity recipesListActivity;

    public RecipesAdapter(RecipesListActivity recipesListActivity) {
        this.recipesListActivity = recipesListActivity;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(recipesListActivity);
        View view = inflater.inflate(R.layout.activity_recipe_item, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        Log.d(TAG, "onBindViewHolder");
        recipeViewHolder.bind(recipiesList.get(i));
    }

    @Override
    public int getItemCount() {
        if (recipiesList == null) {
            return 1;
        }
        return recipiesList.size();
    }

    public void setRecipies(List<Recipe> recipes) {
        this.recipiesList = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Recipe recipie) {
            Log.d(TAG, "Bind new recipe");
            if (recipie != null) {
                TextView recipeName = itemView.findViewById(R.id.dishName);
                recipeName.setText(recipie.getDishName());
//                updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
//                priorityView = itemView.findViewById(R.id.priorityTextView);            }
            }
            itemView.setOnClickListener((v) -> {
                Intent goToDetailsIntent = new Intent(recipesListActivity, RecipeDetail.class);
                goToDetailsIntent.putExtra("recipeId", recipie.getId());
                recipesListActivity.startActivity(goToDetailsIntent);
            });
        }
    }
}
